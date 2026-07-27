package org.SprintForge.modules.workspace.task.service.label;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.task.dto.request.AssignLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.RemoveLabelRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskLabelResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.TaskLabel;
import org.SprintForge.modules.workspace.task.entity.TaskLabelMapping;
import org.SprintForge.modules.workspace.task.event.LabelAssignedEvent;
import org.SprintForge.modules.workspace.task.event.LabelRemovedEvent;
import org.SprintForge.modules.workspace.task.mapper.LabelMapper;
import org.SprintForge.modules.workspace.task.repository.TaskLabelMappingRepository;
import org.SprintForge.modules.workspace.task.repository.TaskLabelRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskLabelServiceImpl implements TaskLabelService {

    private final TaskLabelRepository labelRepository;
    private final TaskLabelMappingRepository mappingRepository;
    private final TaskRepository taskRepository;
    private final LabelMapper labelMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void assignLabel(AssignLabelRequest request, Long actorId) {
        Long taskId = request.getTaskId();
        Long labelId = request.getLabelId();

        // Validate task exists and not deleted
        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        // Validate label exists and not deleted
        TaskLabel label = labelRepository.findById(labelId)
                .filter(l -> !l.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Label not found with ID: " + labelId));

        // Check that task and label belong to the same project
        if (!task.getProject().getId().equals(label.getProject().getId())) {
            throw new IllegalArgumentException("Task and label must belong to the same project");
        }

        // Check if label is archived (cannot assign archived label)
        if (label.isArchived()) {
            throw new IllegalStateException("Cannot assign an archived label");
        }

        // Check if assignment already exists
        if (mappingRepository.existsByTaskIdAndLabelId(taskId, labelId)) {
            throw new IllegalStateException("Label already assigned to this task");
        }

        // Create mapping
        TaskLabelMapping mapping = new TaskLabelMapping();
        mapping.setTaskId(taskId);
        mapping.setLabelId(labelId);
        mapping.setCreatedBy(String.valueOf(actorId));
        mapping.setCreatedAt(LocalDateTime.now());
        mappingRepository.save(mapping);

        // Publish event
        eventPublisher.publishEvent(new LabelAssignedEvent(
                taskId,
                labelId,
                actorId,
                LocalDateTime.now()
        ));
    }


    @Override
    public void assignLabels(Long taskId, List<Long> labelIds, Long actorId) {
        // Validate task exists and not deleted
        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        // Process each label
        for (Long labelId : labelIds) {
            AssignLabelRequest request = new AssignLabelRequest();
            request.setTaskId(taskId);
            request.setLabelId(labelId);
            assignLabel(request, actorId); // reuse single assignment logic (includes validation)
        }
    }

    @Override
    public void removeLabel(RemoveLabelRequest request, Long actorId) {
        Long taskId = request.getTaskId();
        Long labelId = request.getLabelId();

        // Validate task exists
        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        // Validate label exists
        TaskLabel label = labelRepository.findById(labelId)
                .filter(l -> !l.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Label not found with ID: " + labelId));

        // Check that task and label belong to the same project (should be true if assignment exists)
        if (!task.getProject().getId().equals(label.getProject().getId())) {
            throw new IllegalArgumentException("Task and label must belong to the same project");
        }

        // Find and delete mapping
        Long mappingId = mappingRepository.findByTaskIdAndLabelId(taskId, labelId)
                .map(TaskLabelMapping::getId)
                .orElseThrow(() -> new IllegalStateException("Label is not assigned to this task"));

        mappingRepository.deleteById(mappingId);

        // Publish event
        eventPublisher.publishEvent(new LabelRemovedEvent(
                taskId,
                labelId,
                actorId,
                LocalDateTime.now()
        ));
    }

    @Override
    public void removeAllLabels(Long taskId, Long actorId) {
        // Validate task exists
        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        // Delete all mappings for this task
        mappingRepository.deleteByTaskId(taskId);

        // Note: We are not publishing individual events for each label removal to avoid too many events.
        // If needed, we could first fetch the mappings and publish events for each.
        // For simplicity, we can publish a custom event for bulk removal, but spec doesn't require.
        // We'll skip publishing events for bulk removal for now.
    }

    @Override
    public List<TaskLabelResponse> getTaskLabels(Long taskId) {
        // Validate task exists (optional, but we can check)
        taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        // Get label IDs for this task via mappings
        List<Long> labelIds = mappingRepository.findLabelIdsByTaskId(taskId);
        if (labelIds.isEmpty()) {
            return List.of();
        }

        // Get labels
        List<TaskLabel> labels = labelRepository.findAllById(labelIds);
        return labels.stream()
                .map(labelMapper::toTaskLabelResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getTaskIdsByLabel(Long labelId) {
        // Validate label exists
        labelRepository.findById(labelId)
                .filter(l -> !l.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Label not found with ID: " + labelId));

        // Get task IDs that have this label via mappings
        return mappingRepository.findTaskIdsByLabelId(labelId);
    }

    @Override
    public boolean hasLabel(Long taskId, Long labelId) {
        // Validate task and label exist (optional)
        taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));
        labelRepository.findById(labelId)
                .filter(l -> !l.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Label not found with ID: " + labelId));

        return mappingRepository.existsByTaskIdAndLabelId(taskId, labelId);
    }
}