package org.SprintForge.modules.workspace.task.service.relation;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.task.dto.request.CreateSubtaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.SubtaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskHierarchyResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.event.SubtaskCreatedEvent;
import org.SprintForge.modules.workspace.task.event.SubtaskDetachedEvent;
import org.SprintForge.modules.workspace.task.event.SubtaskMovedEvent;
import org.SprintForge.modules.workspace.task.mapper.TaskMapper;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskHierarchyServiceImpl implements TaskHierarchyService {

    private final TaskRepository taskRepository;
    private final ProjectPermissionService projectPermissionService;
    private final TaskMapper taskMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public SubtaskResponse createSubtask(Long parentTaskId, CreateSubtaskRequest request, Long actorId) {
        Task parent = getTaskOrThrow(parentTaskId);
        if (parent.getArchived()) {
            throw new BusinessRuleException("Cannot add a subtask to an archived parent task.");
        }

        Project project = parent.getProject();
        if (!projectPermissionService.hasPermission(project.getId(), actorId, "CREATE_TASK")) {
            throw new ForbiddenException("User does not have permission to create tasks.");
        }

        Task child = taskMapper.toEntity(request);
        child.setProject(project);
        child.setParentTask(parent);
        child.setSprint(parent.getSprint()); // Inherit parent sprint if present

        // Generate unique task identifier key
        long count = taskRepository.countByProjectId(project.getId());
        String identifier = project.getProjectKey() + "-" + (count + 1);
        while (taskRepository.existsByIdentifierAndIsDeletedFalse(identifier)) {
            count++;
            identifier = project.getProjectKey() + "-" + (count + 1);
        }
        child.setIdentifier(identifier);

        Task saved = taskRepository.save(child);

        eventPublisher.publishEvent(new SubtaskCreatedEvent(saved.getId(), parentTaskId, actorId, LocalDateTime.now()));

        return taskMapper.toSubtaskResponse(saved);
    }

    @Override
    @Transactional
    public SubtaskResponse moveSubtask(Long taskId, Long parentTaskId, Long actorId) {
        Task child = getTaskOrThrow(taskId);
        Task newParent = getTaskOrThrow(parentTaskId);

        if (!child.getProject().getId().equals(newParent.getProject().getId())) {
            throw new BusinessRuleException("Parent and child tasks must belong to the same project.");
        }

        if (taskId.equals(parentTaskId)) {
            throw new BusinessRuleException("A task cannot be its own parent.");
        }

        if (!projectPermissionService.hasPermission(child.getProject().getId(), actorId, "UPDATE_TASK")) {
            throw new ForbiddenException("User does not have permission to update tasks.");
        }

        // Circular hierarchy check
        if (isAncestor(taskId, parentTaskId)) {
            throw new BusinessRuleException("Circular parent-child hierarchy detected.");
        }

        Long oldParentId = child.getParentTask() != null ? child.getParentTask().getId() : null;
        child.setParentTask(newParent);
        Task saved = taskRepository.save(child);

        eventPublisher.publishEvent(new SubtaskMovedEvent(taskId, oldParentId, parentTaskId, actorId, LocalDateTime.now()));

        return taskMapper.toSubtaskResponse(saved);
    }

    @Override
    @Transactional
    public void removeParent(Long taskId, Long actorId) {
        Task child = getTaskOrThrow(taskId);
        if (child.getParentTask() == null) {
            return;
        }

        if (!projectPermissionService.hasPermission(child.getProject().getId(), actorId, "UPDATE_TASK")) {
            throw new ForbiddenException("User does not have permission to update tasks.");
        }

        Long oldParentId = child.getParentTask().getId();
        child.setParentTask(null);
        taskRepository.save(child);

        eventPublisher.publishEvent(new SubtaskDetachedEvent(taskId, oldParentId, actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getParentTask(Long taskId, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view project tasks.");
        }
        if (task.getParentTask() == null) {
            return null;
        }
        return taskMapper.toResponse(task.getParentTask());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubtaskResponse> getSubtasks(Long taskId, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view project tasks.");
        }
        List<Task> subtasks = taskRepository.findByParentTaskIdAndIsDeletedFalse(taskId);
        return taskMapper.toSubtaskResponseList(subtasks);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getRootTasks(Long projectId, Long actorId) {
        if (!projectPermissionService.hasPermission(projectId, actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view project tasks.");
        }
        List<Task> roots = taskRepository.findByProjectIdAndParentTaskIsNullAndIsDeletedFalse(projectId);
        return taskMapper.toResponseList(roots);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskHierarchyResponse getTaskHierarchy(Long taskId, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view project tasks.");
        }
        return buildHierarchy(task);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasChildren(Long taskId) {
        return taskRepository.existsByParentTaskIdAndIsDeletedFalse(taskId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countSubtasks(Long taskId) {
        return taskRepository.countByParentTaskIdAndIsDeletedFalse(taskId);
    }

    private TaskHierarchyResponse buildHierarchy(Task task) {
        List<Task> children = taskRepository.findByParentTaskIdAndIsDeletedFalse(task.getId());
        List<TaskHierarchyResponse> childResponses = children.stream()
                .map(this::buildHierarchy)
                .toList();
        return TaskHierarchyResponse.builder()
                .task(taskMapper.toResponse(task))
                .children(childResponses)
                .build();
    }

    private boolean isAncestor(Long childTaskId, Long parentTaskId) {
        Task current = taskRepository.findById(parentTaskId).orElse(null);
        while (current != null) {
            if (current.getId().equals(childTaskId)) {
                return true;
            }
            current = current.getParentTask();
        }
        return false;
    }

    private Task getTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
    }
}
