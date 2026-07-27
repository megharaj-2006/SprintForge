package org.SprintForge.modules.workspace.task.service.label;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.task.dto.request.CreateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.response.LabelResponse;
import org.SprintForge.modules.workspace.task.dto.response.LabelSummaryResponse;
import org.SprintForge.modules.workspace.task.entity.TaskLabel;
import org.SprintForge.modules.workspace.task.event.*;
import org.SprintForge.modules.workspace.task.mapper.LabelMapper;
import org.SprintForge.modules.workspace.task.repository.TaskLabelRepository;
import org.SprintForge.modules.workspace.task.repository.TaskLabelMappingRepository;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LabelManagementServiceImpl implements LabelManagementService {

    private final TaskLabelRepository labelRepository;
    private final TaskLabelMappingRepository mappingRepository;
    private final ProjectRepository projectRepository;
    private final ProjectPermissionService projectPermissionService;
    private final LabelMapper labelMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public LabelResponse createLabel(CreateLabelRequest request, Long actorId) {
        // Validate project exists
        Project project = projectRepository.findById(request.getProjectId())
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + request.getProjectId()));

        // Check MANAGE_LABELS permission
        if (!projectPermissionService.hasPermission(project.getId(), actorId, "MANAGE_LABELS")) {
            throw new ForbiddenException("User does not have MANAGE_LABELS permission on project " + project.getId());
        }

        // Check if label with same name already exists in this project
        if (labelRepository.existsByProjectAndName(project, request.getName())) {
            throw new IllegalArgumentException("Label with name '" + request.getName() + "' already exists in project " + project.getId());
        }

        // Create label entity
        TaskLabel label = labelMapper.toEntity(request);
        label.setProject(project);
        label.setCreatedBy(String.valueOf(actorId)); // assuming actorId is user ID stored as string
        label.setCreatedAt(LocalDateTime.now());

        // Save label
        TaskLabel savedLabel = labelRepository.save(label);

        // Publish event
        eventPublisher.publishEvent(new LabelCreatedEvent(
                savedLabel.getId(),
                savedLabel.getProject().getId(),
                actorId,
                LocalDateTime.now()
        ));

        // Return response
        return labelMapper.toLabelResponse(savedLabel);
    }

    @Override
    public LabelResponse updateLabel(Long labelId, UpdateLabelRequest request, Long actorId) {
        // Find label
        TaskLabel label = labelRepository.findById(labelId)
                .filter(l -> !l.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Label not found with ID: " + labelId));

        // Check if label is archived (cannot modify if archived)
        if (label.isArchived()) {
            throw new IllegalStateException("Archived label cannot be modified");
        }

        // Check MANAGE_LABELS permission on the project
        if (!projectPermissionService.hasPermission(label.getProject().getId(), actorId, "MANAGE_LABELS")) {
            throw new ForbiddenException("User does not have MANAGE_LABELS permission on project " + label.getProject().getId());
        }

        // Check if new name conflicts with another label in same project (excluding current)
        if (request.getName() != null && !request.getName().equals(label.getName())) {
            if (labelRepository.existsByProjectAndName(label.getProject(), request.getName())) {
                throw new IllegalArgumentException("Label with name '" + request.getName() + "' already exists in project " + label.getProject().getId());
            }
        }

        // Update fields
        labelMapper.updateEntityFromRequest(request, label);
        label.setUpdatedBy(String.valueOf(actorId));
        label.setUpdatedAt(LocalDateTime.now());

        // Save
        TaskLabel updatedLabel = labelRepository.save(label);

        // Publish event
        eventPublisher.publishEvent(new LabelUpdatedEvent(
                updatedLabel.getId(),
                updatedLabel.getProject().getId(),
                actorId,
                LocalDateTime.now()
        ));

        return labelMapper.toLabelResponse(updatedLabel);
    }

    @Override
    public void archiveLabel(Long labelId, Long actorId) {
        TaskLabel label = labelRepository.findById(labelId)
                .filter(l -> !l.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Label not found with ID: " + labelId));

        if (label.isArchived()) {
            // Already archived, nothing to do
            return;
        }

        // Check MANAGE_LABELS permission on the project
        if (!projectPermissionService.hasPermission(label.getProject().getId(), actorId, "MANAGE_LABELS")) {
            throw new ForbiddenException("User does not have MANAGE_LABELS permission on project " + label.getProject().getId());
        }

        label.setArchived(true);
        label.setUpdatedBy(String.valueOf(actorId));
        label.setUpdatedAt(LocalDateTime.now());
        labelRepository.save(label);

        eventPublisher.publishEvent(new LabelArchivedEvent(
                label.getId(),
                label.getProject().getId(),
                actorId,
                LocalDateTime.now()
        ));
    }

    @Override
    public void restoreLabel(Long labelId, Long actorId) {
        TaskLabel label = labelRepository.findById(labelId)
                .filter(l -> !l.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Label not found with ID: " + labelId));

        if (!label.isArchived()) {
            // Already not archived
            return;
        }

        // Check MANAGE_LABELS permission on the project
        if (!projectPermissionService.hasPermission(label.getProject().getId(), actorId, "MANAGE_LABELS")) {
            throw new ForbiddenException("User does not have MANAGE_LABELS permission on project " + label.getProject().getId());
        }

        label.setArchived(false);
        label.setUpdatedBy(String.valueOf(actorId));
        label.setUpdatedAt(LocalDateTime.now());
        labelRepository.save(label);

        eventPublisher.publishEvent(new LabelRestoredEvent(
                label.getId(),
                label.getProject().getId(),
                actorId,
                LocalDateTime.now()
        ));
    }

    @Override
    public void deleteLabel(Long labelId, Long actorId) {
        TaskLabel label = labelRepository.findById(labelId)
                .filter(l -> !l.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Label not found with ID: " + labelId));

        // Check MANAGE_LABELS permission on the project
        if (!projectPermissionService.hasPermission(label.getProject().getId(), actorId, "MANAGE_LABELS")) {
            throw new ForbiddenException("User does not have MANAGE_LABELS permission on project " + label.getProject().getId());
        }

        // Delete mappings for this label first
        mappingRepository.deleteByLabelId(labelId);

        // Delete label
        labelRepository.delete(label);

        eventPublisher.publishEvent(new LabelDeletedEvent(
                label.getId(),
                label.getProject().getId(),
                actorId,
                LocalDateTime.now()
        ));
    }

    @Override
    public LabelResponse getLabel(Long labelId) {
        TaskLabel label = labelRepository.findById(labelId)
                .filter(l -> !l.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Label not found with ID: " + labelId));
        return labelMapper.toLabelResponse(label);
    }

    @Override
    public List<LabelResponse> getLabelsByProject(Long projectId) {
        // Verify project exists
        projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        List<TaskLabel> labels = labelRepository.findByProjectId(projectId);
        return labels.stream()
                .map(labelMapper::toLabelResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LabelSummaryResponse> searchLabels(Long projectId, String keyword) {
        // Verify project exists
        projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        List<TaskLabel> labels;
        if (keyword == null || keyword.isBlank()) {
            labels = labelRepository.findByProjectId(projectId);
        } else {
            labels = labelRepository.searchLabels("%" + keyword + "%", projectId);
        }
        return labels.stream()
                .map(labelMapper::toLabelSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public long countTasksUsingLabel(Long labelId) {
        return labelRepository.countTasksUsingLabel(labelId);
    }
}