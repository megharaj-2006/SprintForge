package org.SprintForge.modules.workspace.project.service.milestone;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.dto.request.MilestoneCreateRequest;
import org.SprintForge.modules.workspace.project.dto.request.MilestoneUpdateRequest;
import org.SprintForge.modules.workspace.project.dto.response.MilestoneProgressResponse;
import org.SprintForge.modules.workspace.project.dto.response.MilestoneResponse;
import org.SprintForge.modules.workspace.project.entity.Milestone;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.entity.enums.MilestoneStatus;
import org.SprintForge.modules.workspace.project.event.MilestoneCompletedEvent;
import org.SprintForge.modules.workspace.project.event.MilestoneCreatedEvent;
import org.SprintForge.modules.workspace.project.event.MilestoneDeletedEvent;
import org.SprintForge.modules.workspace.project.exception.MilestoneException;
import org.SprintForge.modules.workspace.project.mapper.MilestoneMapper;
import org.SprintForge.modules.workspace.project.repository.MilestoneRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final MilestoneMapper milestoneMapper;
    private final ApplicationEventPublisher eventPublisher;

    // -------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------

    @Override
    @Transactional
    public MilestoneResponse createMilestone(Long projectId, MilestoneCreateRequest request, Long actorId) {
        // 1. Project must exist and not be deleted
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        // 2. Project must not be archived
        if (project.getIsArchived()) {
            throw new BusinessRuleException("Cannot create a milestone in an archived project.");
        }

        // 3. Milestone name must be unique within the project
        if (milestoneRepository.existsByProjectIdAndNameAndIsDeletedFalse(projectId, request.getName())) {
            throw new MilestoneException("A milestone with the name '" + request.getName() + "' already exists in this project.");
        }

        // 4. Build and persist the milestone
        Milestone milestone = milestoneMapper.toEntity(request);
        milestone.setProjectId(projectId);

        if (milestone.getStatus() == null) {
            milestone.setStatus(MilestoneStatus.ACTIVE);
        }

        Milestone saved = milestoneRepository.save(milestone);

        // 5. Publish domain event
        eventPublisher.publishEvent(new MilestoneCreatedEvent(
                saved.getId(), projectId, saved.getName(), actorId, LocalDateTime.now()
        ));

        return milestoneMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public MilestoneResponse updateMilestone(Long milestoneId, MilestoneUpdateRequest request, Long actorId) {
        // 1. Milestone must exist and not be deleted
        Milestone milestone = loadActiveMilestone(milestoneId);

        // 2. Cannot update a completed or archived milestone's name
        if (milestone.getStatus() == MilestoneStatus.COMPLETED) {
            throw new MilestoneException("Cannot update a completed milestone.");
        }

        // 3. Apply partial update
        milestoneMapper.updateEntity(request, milestone);
        Milestone saved = milestoneRepository.save(milestone);

        return milestoneMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public MilestoneResponse archiveMilestone(Long milestoneId, Long actorId) {
        Milestone milestone = loadActiveMilestone(milestoneId);

        if (Boolean.TRUE.equals(milestone.getIsArchived())) {
            throw new MilestoneException("Milestone is already archived.");
        }

        milestone.archive();
        Milestone saved = milestoneRepository.save(milestone);

        return milestoneMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteMilestone(Long milestoneId, Long actorId) {
        Milestone milestone = loadActiveMilestone(milestoneId);

        if (milestone.getStatus() == MilestoneStatus.COMPLETED) {
            throw new MilestoneException("Completed milestones cannot be deleted. Archive them instead.");
        }

        // Unlink all tasks assigned to this milestone before deletion
        List<Task> assignedTasks = taskRepository.findByMilestoneIdAndIsDeletedFalse(milestoneId);
        assignedTasks.forEach(task -> task.setMilestoneId(null));
        taskRepository.saveAll(assignedTasks);

        milestone.markDeleted(String.valueOf(actorId));
        milestoneRepository.save(milestone);

        eventPublisher.publishEvent(new MilestoneDeletedEvent(
                milestoneId, milestone.getProjectId(), actorId, LocalDateTime.now()
        ));
    }

    @Override
    @Transactional
    public MilestoneResponse completeMilestone(Long milestoneId, Long actorId) {
        Milestone milestone = loadActiveMilestone(milestoneId);

        if (milestone.getStatus() == MilestoneStatus.COMPLETED) {
            throw new MilestoneException("Milestone is already completed.");
        }

        if (Boolean.TRUE.equals(milestone.getIsArchived())) {
            throw new MilestoneException("Cannot complete an archived milestone.");
        }

        milestone.complete();
        Milestone saved = milestoneRepository.save(milestone);

        eventPublisher.publishEvent(new MilestoneCompletedEvent(
                saved.getId(), saved.getProjectId(), saved.getName(),
                actorId, saved.getCompletedAt(), LocalDateTime.now()
        ));

        return milestoneMapper.toResponse(saved);
    }

    // -------------------------------------------------------------------------
    // Task Assignment
    // -------------------------------------------------------------------------

    @Override
    @Transactional
    public MilestoneResponse assignTask(Long milestoneId, Long taskId, Long actorId) {
        Milestone milestone = loadActiveMilestone(milestoneId);

        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        // Task must belong to the same project as the milestone
        if (!milestone.getProjectId().equals(task.getProject().getId())) {
            throw new MilestoneException("Task does not belong to the same project as this milestone.");
        }

        // Task must not already be assigned to another milestone
        if (task.getMilestoneId() != null && !task.getMilestoneId().equals(milestoneId)) {
            throw new MilestoneException("Task is already assigned to a different milestone. Remove it first.");
        }

        task.setMilestoneId(milestoneId);
        taskRepository.save(task);

        return milestoneMapper.toResponse(milestone);
    }

    @Override
    @Transactional
    public MilestoneResponse removeTask(Long milestoneId, Long taskId, Long actorId) {
        Milestone milestone = loadActiveMilestone(milestoneId);

        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        if (!milestoneId.equals(task.getMilestoneId())) {
            throw new MilestoneException("Task is not assigned to this milestone.");
        }

        task.setMilestoneId(null);
        taskRepository.save(task);

        return milestoneMapper.toResponse(milestone);
    }

    // -------------------------------------------------------------------------
    // Query
    // -------------------------------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public List<MilestoneResponse> getProjectMilestones(Long projectId, Long actorId) {
        projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        List<Milestone> milestones = milestoneRepository.findByProjectIdAndIsArchivedFalseAndIsDeletedFalse(projectId);
        return milestoneMapper.toResponseList(milestones);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MilestoneResponse> getOverdueMilestones(Long projectId, Long actorId) {
        projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        List<Milestone> overdue = milestoneRepository.findOverdueMilestones(projectId, LocalDate.now());
        return milestoneMapper.toResponseList(overdue);
    }

    // -------------------------------------------------------------------------
    // Progress
    // -------------------------------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public MilestoneProgressResponse calculateProgress(Long milestoneId, Long actorId) {
        Milestone milestone = loadActiveMilestone(milestoneId);

        long total = milestoneRepository.countTasksByMilestoneId(milestoneId);
        long completed = milestoneRepository.countCompletedTasksByMilestoneId(milestoneId);
        double percentage = total == 0 ? 0.0 : Math.round((completed * 100.0 / total) * 10.0) / 10.0;

        return MilestoneProgressResponse.builder()
                .milestoneId(milestoneId)
                .milestoneName(milestone.getName())
                .totalTasks(total)
                .completedTasks(completed)
                .progressPercentage(percentage)
                .build();
    }

    // -------------------------------------------------------------------------
    // Private Helpers
    // -------------------------------------------------------------------------

    private Milestone loadActiveMilestone(Long milestoneId) {
        return milestoneRepository.findById(milestoneId)
                .filter(m -> !m.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Milestone not found with ID: " + milestoneId));
    }
}
