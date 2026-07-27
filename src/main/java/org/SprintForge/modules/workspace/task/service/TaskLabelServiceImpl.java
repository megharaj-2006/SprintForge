package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.task.dto.response.LabelResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.entity.Label;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.event.LabelAssignedEvent;
import org.SprintForge.modules.workspace.task.event.LabelRemovedEvent;
import org.SprintForge.modules.workspace.task.mapper.LabelMapper;
import org.SprintForge.modules.workspace.task.mapper.TaskMapper;
import org.SprintForge.modules.workspace.task.repository.LabelRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskLabelServiceImpl implements TaskLabelService {

    private final TaskRepository taskRepository;
    private final LabelRepository labelRepository;
    private final ProjectPermissionService projectPermissionService;
    private final LabelMapper labelMapper;
    private final TaskMapper taskMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void assignLabel(Long taskId, Long labelId, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        Label label = getLabelOrThrow(labelId);

        if (!task.getProject().getId().equals(label.getProject().getId())) {
            throw new BusinessRuleException("Task and Label must belong to the same project.");
        }

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "UPDATE_TASK")) {
            throw new ForbiddenException("User does not have permission to update tasks.");
        }

        if (Boolean.TRUE.equals(label.getArchived())) {
            throw new BusinessRuleException("Archived labels cannot be assigned to tasks.");
        }

        if (task.getLabels().stream().anyMatch(l -> l.getId().equals(labelId))) {
            throw new ConflictException("Label is already assigned to this task.");
        }

        task.getLabels().add(label);
        taskRepository.save(task);

        eventPublisher.publishEvent(new LabelAssignedEvent(taskId, labelId, actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public void assignLabels(Long taskId, List<Long> labelIds, Long actorId) {
        for (Long labelId : labelIds) {
            assignLabel(taskId, labelId, actorId);
        }
    }

    @Override
    @Transactional
    public void removeLabel(Long taskId, Long labelId, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        Label label = getLabelOrThrow(labelId);

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "UPDATE_TASK")) {
            throw new ForbiddenException("User does not have permission to update tasks.");
        }

        boolean removed = task.getLabels().removeIf(l -> l.getId().equals(labelId));
        if (!removed) {
            throw new BusinessRuleException("Label is not assigned to this task.");
        }
        taskRepository.save(task);

        eventPublisher.publishEvent(new LabelRemovedEvent(taskId, labelId, actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public void removeAllLabels(Long taskId, Long actorId) {
        Task task = getTaskOrThrow(taskId);

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "UPDATE_TASK")) {
            throw new ForbiddenException("User does not have permission to update tasks.");
        }

        List<Label> toRemove = new ArrayList<>(task.getLabels());
        task.getLabels().clear();
        taskRepository.save(task);

        for (Label label : toRemove) {
            eventPublisher.publishEvent(new LabelRemovedEvent(taskId, label.getId(), actorId, LocalDateTime.now()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<LabelResponse> getTaskLabels(Long taskId, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view project tasks.");
        }
        return labelMapper.toResponseList(new ArrayList<>(task.getLabels()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByLabel(Long labelId, Long actorId) {
        Label label = getLabelOrThrow(labelId);
        if (!projectPermissionService.hasPermission(label.getProject().getId(), actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view project tasks.");
        }
        List<Task> tasks = taskRepository.findTasksByLabelId(labelId);
        return taskMapper.toResponseList(tasks);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasLabel(Long taskId, Long labelId) {
        Task task = getTaskOrThrow(taskId);
        return task.getLabels().stream().anyMatch(l -> l.getId().equals(labelId));
    }

    @Override
    @Transactional(readOnly = true)
    public long countTasksUsingLabel(Long labelId) {
        return labelRepository.countTasksUsingLabel(labelId);
    }

    private Task getTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
    }

    private Label getLabelOrThrow(Long id) {
        return labelRepository.findById(id)
                .filter(l -> !l.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Label not found with ID: " + id));
    }
}
