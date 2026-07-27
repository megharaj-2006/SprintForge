package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.task.dto.request.CreateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.response.LabelResponse;
import org.SprintForge.modules.workspace.task.entity.Label;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.event.LabelArchivedEvent;
import org.SprintForge.modules.workspace.task.event.LabelCreatedEvent;
import org.SprintForge.modules.workspace.task.event.LabelDeletedEvent;
import org.SprintForge.modules.workspace.task.event.LabelRestoredEvent;
import org.SprintForge.modules.workspace.task.event.LabelUpdatedEvent;
import org.SprintForge.modules.workspace.task.mapper.LabelMapper;
import org.SprintForge.modules.workspace.task.repository.LabelRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelManagementServiceImpl implements LabelManagementService {

    private final LabelRepository labelRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ProjectPermissionService projectPermissionService;
    private final LabelMapper labelMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public LabelResponse createLabel(Long projectId, CreateLabelRequest request, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.getIsArchived())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        if (!projectPermissionService.hasPermission(projectId, actorId, "MANAGE_LABELS")) {
            throw new ForbiddenException("User does not have permission to manage labels.");
        }

        if (labelRepository.existsByProjectIdAndNameAndIsDeletedFalse(projectId, request.getName())) {
            throw new ConflictException("Label name already exists in this project.");
        }

        Label label = labelMapper.toEntity(request);
        label.setProject(project);

        Label saved = labelRepository.save(label);

        eventPublisher.publishEvent(new LabelCreatedEvent(
                saved.getId(),
                projectId,
                saved.getName(),
                actorId,
                LocalDateTime.now()
        ));

        return labelMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public LabelResponse updateLabel(Long labelId, UpdateLabelRequest request, Long actorId) {
        Label label = getLabelOrThrow(labelId);

        if (!projectPermissionService.hasPermission(label.getProject().getId(), actorId, "MANAGE_LABELS")) {
            throw new ForbiddenException("User does not have permission to manage labels.");
        }

        if (Boolean.TRUE.equals(label.getArchived())) {
            throw new BusinessRuleException("Archived labels cannot be modified.");
        }

        if (request.getName() != null && !request.getName().equals(label.getName())) {
            if (labelRepository.existsByProjectIdAndNameAndIsDeletedFalse(label.getProject().getId(), request.getName())) {
                throw new ConflictException("Label name already exists in this project.");
            }
        }

        labelMapper.updateEntity(request, label);
        Label saved = labelRepository.save(label);

        eventPublisher.publishEvent(new LabelUpdatedEvent(saved.getId(), actorId, LocalDateTime.now()));

        return labelMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public LabelResponse archiveLabel(Long labelId, Long actorId) {
        Label label = getLabelOrThrow(labelId);

        if (!projectPermissionService.hasPermission(label.getProject().getId(), actorId, "MANAGE_LABELS")) {
            throw new ForbiddenException("User does not have permission to manage labels.");
        }

        label.setArchived(true);
        Label saved = labelRepository.save(label);

        eventPublisher.publishEvent(new LabelArchivedEvent(saved.getId(), actorId, LocalDateTime.now()));

        return labelMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public LabelResponse restoreLabel(Long labelId, Long actorId) {
        Label label = getLabelOrThrow(labelId);

        if (!projectPermissionService.hasPermission(label.getProject().getId(), actorId, "MANAGE_LABELS")) {
            throw new ForbiddenException("User does not have permission to manage labels.");
        }

        label.setArchived(false);
        Label saved = labelRepository.save(label);

        eventPublisher.publishEvent(new LabelRestoredEvent(saved.getId(), actorId, LocalDateTime.now()));

        return labelMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteLabel(Long labelId, Long actorId) {
        Label label = getLabelOrThrow(labelId);

        if (!projectPermissionService.hasPermission(label.getProject().getId(), actorId, "MANAGE_LABELS")) {
            throw new ForbiddenException("User does not have permission to manage labels.");
        }

        // Remove from all tasks to maintain integrity
        for (Task task : label.getTasks()) {
            task.getLabels().remove(label);
            taskRepository.save(task);
        }
        label.getTasks().clear();

        label.markDeleted(actorId.toString());
        labelRepository.save(label);

        eventPublisher.publishEvent(new LabelDeletedEvent(labelId, actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LabelResponse> getLabels(Long projectId, Long actorId) {
        if (!projectPermissionService.hasPermission(projectId, actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view project labels.");
        }
        List<Label> labels = labelRepository.findByProjectIdAndIsDeletedFalse(projectId);
        return labelMapper.toResponseList(labels);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LabelResponse> searchLabels(Long projectId, String query, Long actorId) {
        if (!projectPermissionService.hasPermission(projectId, actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view project labels.");
        }
        List<Label> labels = labelRepository.searchLabels(projectId, query);
        return labelMapper.toResponseList(labels);
    }

    private Label getLabelOrThrow(Long id) {
        return labelRepository.findById(id)
                .filter(l -> !l.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Label not found with ID: " + id));
    }
}
