package org.SprintForge.modules.workspace.sprint.service.management;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintCreateRequest;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintDuplicateRequest;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintUpdateRequest;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintResponse;
import org.SprintForge.modules.workspace.sprint.entity.Sprint;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;
import org.SprintForge.modules.workspace.sprint.event.*;
import org.SprintForge.modules.workspace.sprint.exception.SprintException;
import org.SprintForge.modules.workspace.sprint.exception.SprintNotFoundException;
import org.SprintForge.modules.workspace.sprint.mapper.SprintMapper;
import org.SprintForge.modules.workspace.sprint.repository.SprintRepository;
import org.SprintForge.modules.workspace.sprint.validation.SprintValidator;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SprintLifecycleServiceImpl implements SprintLifecycleService {

    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;
    private final ProjectPermissionService projectPermissionService;
    private final SprintMapper sprintMapper;
    private final SprintValidator sprintValidator;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public SprintResponse createSprint(SprintCreateRequest request, Long actorId) {
        Project project = getProjectOrThrow(request.getProjectId());
        checkCanManageSprint(project.getId(), actorId);

        sprintValidator.validateDates(request.getStartDate(), request.getEndDate());

        Sprint sprint = sprintMapper.toEntity(request);
        sprint.setStatus(SprintStatus.PLANNED);
        
        long count = sprintRepository.countByProjectIdAndIsDeletedFalse(project.getId());
        sprint.setOrderIndex((int) (count + 1));

        Sprint saved = sprintRepository.save(sprint);

        eventPublisher.publishEvent(new SprintCreatedEvent(saved.getId(), saved.getProjectId(), actorId, LocalDateTime.now()));

        return sprintMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public SprintResponse updateSprint(Long id, SprintUpdateRequest request, Long actorId) {
        Sprint sprint = getSprintOrThrow(id);
        checkCanManageSprint(sprint.getProjectId(), actorId);
        checkSprintIsMutable(sprint);

        sprintValidator.validateDates(request.getStartDate(), request.getEndDate());

        sprintMapper.updateEntity(request, sprint);
        Sprint saved = sprintRepository.save(sprint);

        eventPublisher.publishEvent(new SprintUpdatedEvent(saved.getId(), actorId, LocalDateTime.now()));

        return sprintMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public SprintResponse startSprint(Long id, Long actorId) {
        Sprint sprint = getSprintOrThrow(id);
        checkCanManageSprint(sprint.getProjectId(), actorId);

        if (sprint.getStatus() != SprintStatus.PLANNED) {
            throw new SprintException("Only PLANNED sprints can be started.");
        }

        sprintValidator.validateActiveSprintCheck(sprint.getProjectId(), id);

        sprint.setStatus(SprintStatus.ACTIVE);
        Sprint saved = sprintRepository.save(sprint);

        eventPublisher.publishEvent(new SprintStartedEvent(saved.getId(), actorId, LocalDateTime.now()));

        return sprintMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public SprintResponse completeSprint(Long id, Long actorId) {
        Sprint sprint = getSprintOrThrow(id);
        checkCanManageSprint(sprint.getProjectId(), actorId);

        if (sprint.getStatus() != SprintStatus.ACTIVE) {
            throw new SprintException("Only ACTIVE sprints can be completed.");
        }

        sprint.setStatus(SprintStatus.COMPLETED);
        sprint.setCompletedAt(LocalDateTime.now());
        Sprint saved = sprintRepository.save(sprint);

        eventPublisher.publishEvent(new SprintCompletedEvent(saved.getId(), actorId, LocalDateTime.now()));

        return sprintMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public SprintResponse cancelSprint(Long id, Long actorId) {
        Sprint sprint = getSprintOrThrow(id);
        checkCanManageSprint(sprint.getProjectId(), actorId);

        if (sprint.getStatus() != SprintStatus.PLANNED && sprint.getStatus() != SprintStatus.ACTIVE) {
            throw new SprintException("Only PLANNED or ACTIVE sprints can be cancelled.");
        }

        sprint.setStatus(SprintStatus.CANCELLED);
        sprint.setCancelledAt(LocalDateTime.now());
        Sprint saved = sprintRepository.save(sprint);

        eventPublisher.publishEvent(new SprintCancelledEvent(saved.getId(), actorId, LocalDateTime.now()));

        return sprintMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public SprintResponse archiveSprint(Long id, Long actorId) {
        Sprint sprint = getSprintOrThrow(id);
        checkCanManageSprint(sprint.getProjectId(), actorId);

        if (sprint.getStatus() != SprintStatus.COMPLETED && sprint.getStatus() != SprintStatus.CANCELLED) {
            throw new SprintException("Only COMPLETED or CANCELLED sprints can be archived.");
        }

        sprint.setStatus(SprintStatus.ARCHIVED);
        sprint.setArchivedAt(LocalDateTime.now());
        Sprint saved = sprintRepository.save(sprint);

        eventPublisher.publishEvent(new SprintArchivedEvent(saved.getId(), actorId, LocalDateTime.now()));

        return sprintMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public SprintResponse restoreSprint(Long id, Long actorId) {
        Sprint sprint = getSprintOrThrow(id);
        checkCanManageSprint(sprint.getProjectId(), actorId);

        if (sprint.getStatus() != SprintStatus.ARCHIVED) {
            throw new SprintException("Only ARCHIVED sprints can be restored.");
        }

        sprint.setStatus(SprintStatus.PLANNED);
        sprint.setArchivedAt(null);
        Sprint saved = sprintRepository.save(sprint);

        eventPublisher.publishEvent(new SprintRestoredEvent(saved.getId(), actorId, LocalDateTime.now()));

        return sprintMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteSprint(Long id, Long actorId) {
        Sprint sprint = getSprintOrThrow(id);
        checkCanManageSprint(sprint.getProjectId(), actorId);

        if (sprint.getStatus() != SprintStatus.PLANNED && sprint.getStatus() != SprintStatus.CANCELLED) {
            throw new SprintException("Only PLANNED or CANCELLED sprints can be deleted.");
        }

        sprint.markDeleted(actorId.toString());
        sprintRepository.save(sprint);

        eventPublisher.publishEvent(new SprintDeletedEvent(id, actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public SprintResponse duplicateSprint(Long id, SprintDuplicateRequest request, Long actorId) {
        Sprint source = getSprintOrThrow(id);
        checkCanManageSprint(source.getProjectId(), actorId);

        Sprint duplicate = new Sprint();
        duplicate.setProjectId(source.getProjectId());
        duplicate.setName(request.getName());
        if (request.isCopyGoal()) {
            duplicate.setGoal(source.getGoal());
        }
        duplicate.setCapacity(source.getCapacity());
        duplicate.setPlannedStoryPoints(source.getPlannedStoryPoints());
        duplicate.setStartDate(request.getStartDate() != null ? request.getStartDate() : source.getStartDate());
        if (duplicate.getStartDate() != null && source.getEndDate() != null && source.getStartDate() != null) {
            long days = java.time.temporal.ChronoUnit.DAYS.between(source.getStartDate(), source.getEndDate());
            duplicate.setEndDate(duplicate.getStartDate().plusDays(days));
        } else {
            duplicate.setEndDate(source.getEndDate());
        }
        duplicate.setStatus(SprintStatus.PLANNED);

        long count = sprintRepository.countByProjectIdAndIsDeletedFalse(source.getProjectId());
        duplicate.setOrderIndex((int) (count + 1));

        Sprint saved = sprintRepository.save(duplicate);

        eventPublisher.publishEvent(new SprintDuplicatedEvent(source.getId(), saved.getId(), actorId, LocalDateTime.now()));

        return sprintMapper.toResponse(saved);
    }

    private Project getProjectOrThrow(Long projectId) {
        return projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
    }

    private Sprint getSprintOrThrow(Long sprintId) {
        return sprintRepository.findByIdAndIsDeletedFalse(sprintId)
                .orElseThrow(() -> new SprintNotFoundException(sprintId));
    }

    private void checkCanManageSprint(Long projectId, Long actorId) {
        if (!projectPermissionService.hasPermission(projectId, actorId, "SPRINT_CREATE") &&
                !projectPermissionService.hasPermission(projectId, actorId, "TASK_MANAGE")) {
            throw new ForbiddenException("Access Denied: You do not have permission to manage sprints for this project.");
        }
    }

    private void checkSprintIsMutable(Sprint sprint) {
        if (sprint.getStatus() == SprintStatus.COMPLETED ||
                sprint.getStatus() == SprintStatus.CANCELLED ||
                sprint.getStatus() == SprintStatus.ARCHIVED) {
            throw new SprintException("Completed, cancelled, or archived sprints cannot be modified.");
        }
    }
}
