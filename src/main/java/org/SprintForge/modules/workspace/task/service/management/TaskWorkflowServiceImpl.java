package org.SprintForge.modules.workspace.task.service.management;

import org.SprintForge.modules.workspace.task.service.relation.TaskDependencyService;
import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.TaskPriority;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.entity.enums.TaskType;
import org.SprintForge.modules.workspace.task.event.*;
import org.SprintForge.modules.workspace.task.mapper.TaskMapper;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskWorkflowServiceImpl implements TaskWorkflowService {

    private final TaskRepository taskRepository;
    private final ProjectPermissionService projectPermissionService;
    private final TaskMapper taskMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final TaskDependencyService taskDependencyService;

    @Override
    @Transactional
    public TaskResponse changeStatus(Long id, TaskStatus status, Long actorId) {
        Task task = getTaskOrThrow(id);
        validateTaskMutable(task);

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "UPDATE_TASK")) {
            throw new ForbiddenException("User does not have permission to update tasks.");
        }

        TaskStatus oldStatus = task.getStatus();
        if (oldStatus == status) {
            return taskMapper.toResponse(task);
        }

        if (!validateTransition(oldStatus, status)) {
            throw new BusinessRuleException("Transition from " + oldStatus + " to " + status + " is not allowed.");
        }

        if (status == TaskStatus.IN_PROGRESS) {
            taskDependencyService.validateDependencies(id);
        }

        if (status == TaskStatus.DONE) {
            validateSubtasksCompleted(id);
        }

        task.setStatus(status);
        Task saved = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskStatusChangedEvent(saved.getId(), oldStatus, status, actorId, LocalDateTime.now()));

        return taskMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TaskResponse changePriority(Long id, TaskPriority priority, Long actorId) {
        Task task = getTaskOrThrow(id);
        validateTaskMutable(task);

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "TASK_MANAGE")) {
            throw new ForbiddenException("User does not have permission to manage tasks.");
        }

        TaskPriority oldPriority = task.getPriority();
        if (oldPriority == priority) {
            return taskMapper.toResponse(task);
        }

        task.setPriority(priority);
        Task saved = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskPriorityChangedEvent(saved.getId(), oldPriority, priority, actorId, LocalDateTime.now()));

        return taskMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TaskResponse changeType(Long id, TaskType type, Long actorId) {
        Task task = getTaskOrThrow(id);
        validateTaskMutable(task);

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "TASK_MANAGE")) {
            throw new ForbiddenException("User does not have permission to manage tasks.");
        }

        if (task.getType() == type) {
            return taskMapper.toResponse(task);
        }

        task.setType(type);
        Task saved = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskUpdatedEvent(saved.getId(), actorId, LocalDateTime.now()));

        return taskMapper.toResponse(saved);
    }

    @Override
    public boolean validateTransition(TaskStatus current, TaskStatus target) {
        if (current == target) {
            return true;
        }
        return switch (current) {
            case TODO -> target == TaskStatus.IN_PROGRESS || target == TaskStatus.CANCELLED;
            case IN_PROGRESS -> target == TaskStatus.IN_REVIEW || target == TaskStatus.TODO || target == TaskStatus.CANCELLED;
            case IN_REVIEW -> target == TaskStatus.DONE || target == TaskStatus.IN_PROGRESS || target == TaskStatus.CANCELLED;
            case DONE -> target == TaskStatus.IN_PROGRESS;
            case CANCELLED -> target == TaskStatus.TODO;
        };
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskStatus> getAllowedTransitions(Long id, Long actorId) {
        Task task = getTaskOrThrow(id);
        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view task workflow.");
        }

        List<TaskStatus> allowed = new ArrayList<>();
        for (TaskStatus status : TaskStatus.values()) {
            if (validateTransition(task.getStatus(), status)) {
                allowed.add(status);
            }
        }
        return allowed;
    }

    @Override
    @Transactional
    public TaskResponse startTask(Long id, Long actorId) {
        Task task = getTaskOrThrow(id);
        validateTaskMutable(task);

        if (task.getStatus() != TaskStatus.TODO) {
            throw new BusinessRuleException("Task can only be started from TODO status.");
        }

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "UPDATE_TASK")) {
            throw new ForbiddenException("User does not have permission to update tasks.");
        }

        taskDependencyService.validateDependencies(id);

        task.setStatus(TaskStatus.IN_PROGRESS);
        Task saved = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskStartedEvent(saved.getId(), actorId, LocalDateTime.now()));
        eventPublisher.publishEvent(new TaskStatusChangedEvent(saved.getId(), TaskStatus.TODO, TaskStatus.IN_PROGRESS, actorId, LocalDateTime.now()));

        return taskMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TaskResponse sendForReview(Long id, Long actorId) {
        Task task = getTaskOrThrow(id);
        validateTaskMutable(task);

        if (task.getStatus() != TaskStatus.IN_PROGRESS) {
            throw new BusinessRuleException("Task can only be sent for review from IN_PROGRESS status.");
        }

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "UPDATE_TASK")) {
            throw new ForbiddenException("User does not have permission to update tasks.");
        }

        task.setStatus(TaskStatus.IN_REVIEW);
        Task saved = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskReviewRequestedEvent(saved.getId(), actorId, LocalDateTime.now()));
        eventPublisher.publishEvent(new TaskStatusChangedEvent(saved.getId(), TaskStatus.IN_PROGRESS, TaskStatus.IN_REVIEW, actorId, LocalDateTime.now()));

        return taskMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TaskResponse completeTask(Long id, Long actorId) {
        Task task = getTaskOrThrow(id);
        validateTaskMutable(task);

        if (task.getStatus() != TaskStatus.IN_REVIEW) {
            throw new BusinessRuleException("Task can only be completed from IN_REVIEW status.");
        }

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "UPDATE_TASK")) {
            throw new ForbiddenException("User does not have permission to update tasks.");
        }

        validateSubtasksCompleted(id);

        task.setStatus(TaskStatus.DONE);
        Task saved = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskCompletedEvent(saved.getId(), actorId, LocalDateTime.now()));
        eventPublisher.publishEvent(new TaskStatusChangedEvent(saved.getId(), TaskStatus.IN_REVIEW, TaskStatus.DONE, actorId, LocalDateTime.now()));

        return taskMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TaskResponse cancelTask(Long id, Long actorId) {
        Task task = getTaskOrThrow(id);
        validateTaskMutable(task);

        TaskStatus oldStatus = task.getStatus();
        if (oldStatus == TaskStatus.CANCELLED) {
            throw new BusinessRuleException("Cancelled tasks cannot be cancelled again.");
        }

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "UPDATE_TASK")) {
            throw new ForbiddenException("User does not have permission to update tasks.");
        }

        task.setStatus(TaskStatus.CANCELLED);
        Task saved = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskCancelledEvent(saved.getId(), actorId, LocalDateTime.now()));
        eventPublisher.publishEvent(new TaskStatusChangedEvent(saved.getId(), oldStatus, TaskStatus.CANCELLED, actorId, LocalDateTime.now()));

        return taskMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TaskResponse reopenTask(Long id, TaskStatus targetStatus, Long actorId) {
        Task task = getTaskOrThrow(id);
        validateTaskMutable(task);

        TaskStatus oldStatus = task.getStatus();
        TaskStatus finalTarget = targetStatus;
        if (finalTarget == null) {
            if (oldStatus == TaskStatus.DONE) {
                finalTarget = TaskStatus.IN_PROGRESS;
            } else if (oldStatus == TaskStatus.CANCELLED) {
                finalTarget = TaskStatus.TODO;
            } else {
                throw new BusinessRuleException("Task can only be reopened from DONE or CANCELLED status.");
            }
        } else {
            if (oldStatus != TaskStatus.DONE && oldStatus != TaskStatus.CANCELLED) {
                throw new BusinessRuleException("Task can only be reopened from DONE or CANCELLED status.");
            }
            if (oldStatus == TaskStatus.DONE && finalTarget != TaskStatus.IN_PROGRESS) {
                throw new BusinessRuleException("DONE tasks can only be reopened to IN_PROGRESS status.");
            }
            if (oldStatus == TaskStatus.CANCELLED && finalTarget != TaskStatus.TODO) {
                throw new BusinessRuleException("CANCELLED tasks can only be reopened to TODO status.");
            }
        }

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "UPDATE_TASK")) {
            throw new ForbiddenException("User does not have permission to update tasks.");
        }

        if (finalTarget == TaskStatus.IN_PROGRESS) {
            taskDependencyService.validateDependencies(id);
        }

        task.setStatus(finalTarget);
        Task saved = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskReopenedEvent(saved.getId(), oldStatus, finalTarget, actorId, LocalDateTime.now()));
        eventPublisher.publishEvent(new TaskStatusChangedEvent(saved.getId(), oldStatus, finalTarget, actorId, LocalDateTime.now()));

        return taskMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByStatus(Long projectId, TaskStatus status, Long actorId) {
        if (!projectPermissionService.hasPermission(projectId, actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view project tasks.");
        }
        List<Task> tasks = taskRepository.findByProjectIdAndStatusAndIsDeletedFalse(projectId, status);
        return taskMapper.toResponseList(tasks);
    }

    @Override
    @Transactional(readOnly = true)
    public long countTasksByStatus(Long projectId, TaskStatus status, Long actorId) {
        if (!projectPermissionService.hasPermission(projectId, actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view project tasks.");
        }
        return taskRepository.countByProjectIdAndStatusAndIsDeletedFalse(projectId, status);
    }

    private Task getTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
    }

    private void validateTaskMutable(Task task) {
        if (Boolean.TRUE.equals(task.getArchived())) {
            throw new BusinessRuleException("Archived tasks cannot be modified.");
        }
    }

    private void validateSubtasksCompleted(Long taskId) {
        List<Task> subtasks = taskRepository.findByParentTaskIdAndIsDeletedFalse(taskId);
        for (Task sub : subtasks) {
            if (sub.getStatus() != TaskStatus.DONE) {
                throw new BusinessRuleException("Cannot complete parent task because some subtasks are not completed.");
            }
        }
    }
}
