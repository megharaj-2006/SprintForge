package org.SprintForge.modules.workspace.task.service.management;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectStatusType;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.service.member.ProjectMemberService;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.sprint.entity.Sprint;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;
import org.SprintForge.modules.workspace.sprint.repository.SprintRepository;
import org.SprintForge.modules.workspace.task.dto.request.CreateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.DuplicateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateTaskRequest;
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

@Service
@RequiredArgsConstructor
public class TaskLifecycleServiceImpl implements TaskLifecycleService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final SprintRepository sprintRepository;
    private final ProjectMemberService projectMemberService;
    private final ProjectPermissionService projectPermissionService;
    private final TaskMapper taskMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public TaskResponse createTask(CreateTaskRequest request, Long actorId) {
        // 1. Project must exist and not be deleted
        Project project = projectRepository.findById(request.getProjectId())
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + request.getProjectId()));

        // 2. Project must be active
        if (Boolean.TRUE.equals(project.getIsArchived()) || project.getStatus() == ProjectStatusType.ARCHIVED) {
            throw new BusinessRuleException("Cannot create a task in an archived project.");
        }

        // 3. User must be a Project Member
        if (!projectMemberService.isProjectMember(project.getId(), actorId)) {
            throw new ForbiddenException("User must be a member of the project to create tasks.");
        }

        // 4. User must have CREATE_TASK permission
        if (!projectPermissionService.hasPermission(project.getId(), actorId, "CREATE_TASK")) {
            throw new ForbiddenException("User does not have permission to create tasks in this project.");
        }

        // 5. If a Sprint is provided, it must belong to the same Project and not be deleted
        Sprint sprint = null;
        if (request.getSprintId() != null) {
            sprint = sprintRepository.findById(request.getSprintId())
                    .filter(s -> !s.isDeleted())
                    .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with ID: " + request.getSprintId()));
            if (!sprint.getProjectId().equals(project.getId())) {
                throw new BusinessRuleException("Sprint must belong to the same project.");
            }
        }

        // 6. Parent task validation
        Task parentTask = null;
        if (request.getParentTaskId() != null) {
            parentTask = taskRepository.findById(request.getParentTaskId())
                    .filter(t -> !t.isDeleted())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent task not found with ID: " + request.getParentTaskId()));
            if (!parentTask.getProject().getId().equals(project.getId())) {
                throw new BusinessRuleException("Parent task must belong to the same project.");
            }
        }

        // 7. Map to entity
        Task task = taskMapper.toEntity(request);
        task.setProject(project);
        task.setSprint(sprint);
        task.setParentTask(parentTask);

        // Set defaults if null
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.TODO);
        }
        if (task.getPriority() == null) {
            task.setPriority(TaskPriority.MEDIUM);
        }
        if (task.getType() == null) {
            task.setType(TaskType.TASK);
        }
        task.setArchived(false);

        // 8. Generate task identifier
        long seq = taskRepository.countByProjectId(project.getId()) + 1;
        String identifier = project.getProjectKey() + "-" + seq;
        while (taskRepository.existsByIdentifier(identifier)) {
            seq++;
            identifier = project.getProjectKey() + "-" + seq;
        }
        task.setIdentifier(identifier);

        Task saved = taskRepository.save(task);

        // 9. Publish event
        eventPublisher.publishEvent(new TaskCreatedEvent(saved.getId(), project.getId(), actorId, LocalDateTime.now()));

        return taskMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TaskResponse updateTask(Long id, UpdateTaskRequest request, Long actorId) {
        Task task = getTaskOrThrow(id);

        // 1. Archived tasks cannot be modified
        if (Boolean.TRUE.equals(task.getArchived())) {
            throw new BusinessRuleException("Archived tasks cannot be modified.");
        }

        // 2. User must have permission
        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "TASK_MANAGE")) {
            throw new ForbiddenException("User does not have permission to manage tasks.");
        }

        taskMapper.updateEntity(request, task);
        Task saved = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskUpdatedEvent(saved.getId(), actorId, LocalDateTime.now()));

        return taskMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteTask(Long id, Long actorId) {
        Task task = getTaskOrThrow(id);

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "TASK_MANAGE")) {
            throw new ForbiddenException("User does not have permission to delete tasks.");
        }

        task.markDeleted(actorId.toString());
        taskRepository.save(task);

        eventPublisher.publishEvent(new TaskDeletedEvent(id, actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public TaskResponse archiveTask(Long id, Long actorId) {
        Task task = getTaskOrThrow(id);

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "TASK_MANAGE")) {
            throw new ForbiddenException("User does not have permission to archive tasks.");
        }

        task.setArchived(true);
        Task saved = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskArchivedEvent(saved.getId(), actorId, LocalDateTime.now()));

        return taskMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TaskResponse restoreTask(Long id, Long actorId) {
        Task task = getTaskOrThrow(id);

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "TASK_MANAGE")) {
            throw new ForbiddenException("User does not have permission to restore tasks.");
        }

        task.setArchived(false);
        Task saved = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskRestoredEvent(saved.getId(), actorId, LocalDateTime.now()));

        return taskMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TaskResponse duplicateTask(Long id, DuplicateTaskRequest request, Long actorId) {
        Task source = getTaskOrThrow(id);

        if (!projectPermissionService.hasPermission(source.getProject().getId(), actorId, "CREATE_TASK")) {
            throw new ForbiddenException("User does not have permission to duplicate tasks.");
        }

        Task duplicate = new Task();
        duplicate.setProject(source.getProject());
        duplicate.setSprint(source.getSprint());
        duplicate.setParentTask(source.getParentTask());
        
        String newTitle = (request.getTitle() != null && !request.getTitle().isBlank()) 
                ? request.getTitle() 
                : "Copy of " + source.getTitle();
        duplicate.setTitle(newTitle);
        
        duplicate.setDescription(source.getDescription());
        duplicate.setStatus(source.getStatus());
        duplicate.setPriority(source.getPriority());
        duplicate.setType(source.getType());
        duplicate.setDueDate(source.getDueDate());
        duplicate.setEstimatedHours(source.getEstimatedHours());
        duplicate.setActualHours(null); // Actual hours start blank on duplication
        duplicate.setStoryPoints(source.getStoryPoints());
        duplicate.setArchived(false);

        // Generate identifier
        long seq = taskRepository.countByProjectId(source.getProject().getId()) + 1;
        String identifier = source.getProject().getProjectKey() + "-" + seq;
        while (taskRepository.existsByIdentifier(identifier)) {
            seq++;
            identifier = source.getProject().getProjectKey() + "-" + seq;
        }
        duplicate.setIdentifier(identifier);

        Task saved = taskRepository.save(duplicate);

        eventPublisher.publishEvent(new TaskDuplicatedEvent(source.getId(), saved.getId(), actorId, LocalDateTime.now()));

        return taskMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TaskResponse moveTaskToSprint(Long id, Long sprintId, Long actorId) {
        Task task = getTaskOrThrow(id);

        if (Boolean.TRUE.equals(task.getArchived())) {
            throw new BusinessRuleException("Archived tasks cannot be modified.");
        }

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "TASK_MANAGE")) {
            throw new ForbiddenException("User does not have permission to move tasks.");
        }

        Sprint sprint = sprintRepository.findById(sprintId)
                .filter(s -> !s.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with ID: " + sprintId));

        if (!sprint.getProjectId().equals(task.getProject().getId())) {
            throw new BusinessRuleException("Sprint must belong to the same project.");
        }

        if (sprint.getStatus() == SprintStatus.COMPLETED) {
            throw new BusinessRuleException("Cannot move tasks to a completed sprint.");
        }

        task.setSprint(sprint);
        Task saved = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskMovedEvent(saved.getId(), sprint.getId(), actorId, LocalDateTime.now()));

        return taskMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TaskResponse removeFromSprint(Long id, Long actorId) {
        Task task = getTaskOrThrow(id);

        if (Boolean.TRUE.equals(task.getArchived())) {
            throw new BusinessRuleException("Archived tasks cannot be modified.");
        }

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "TASK_MANAGE")) {
            throw new ForbiddenException("User does not have permission to remove tasks from sprint.");
        }

        task.setSprint(null);
        Task saved = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskMovedEvent(saved.getId(), null, actorId, LocalDateTime.now()));

        return taskMapper.toResponse(saved);
    }

    private Task getTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
    }
}
