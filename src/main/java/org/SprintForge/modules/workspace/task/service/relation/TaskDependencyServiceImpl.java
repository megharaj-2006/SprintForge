package org.SprintForge.modules.workspace.task.service.relation;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.task.dto.request.CreateTaskDependencyRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskDependencyResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.TaskDependency;
import org.SprintForge.modules.workspace.task.entity.enums.TaskDependencyType;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.event.TaskDependencyCreatedEvent;
import org.SprintForge.modules.workspace.task.event.TaskDependencyRemovedEvent;
import org.SprintForge.modules.workspace.task.mapper.TaskDependencyMapper;
import org.SprintForge.modules.workspace.task.mapper.TaskMapper;
import org.SprintForge.modules.workspace.task.repository.TaskDependencyRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskDependencyServiceImpl implements TaskDependencyService {

    private final TaskDependencyRepository taskDependencyRepository;
    private final TaskRepository taskRepository;
    private final ProjectPermissionService projectPermissionService;
    private final TaskDependencyMapper taskDependencyMapper;
    private final TaskMapper taskMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public TaskDependencyResponse addDependency(CreateTaskDependencyRequest request, Long actorId) {
        Task predecessor = getTaskOrThrow(request.getPredecessorTaskId());
        Task successor = getTaskOrThrow(request.getSuccessorTaskId());

        if (!predecessor.getProject().getId().equals(successor.getProject().getId())) {
            throw new BusinessRuleException("Tasks must belong to the same project.");
        }

        if (request.getPredecessorTaskId().equals(request.getSuccessorTaskId())) {
            throw new BusinessRuleException("A task cannot depend on itself.");
        }

        if (!projectPermissionService.hasPermission(predecessor.getProject().getId(), actorId, "UPDATE_TASK")) {
            throw new ForbiddenException("User does not have permission to manage dependencies.");
        }

        if (taskDependencyRepository.existsByPredecessorTaskIdAndSuccessorTaskIdAndIsDeletedFalse(
                request.getPredecessorTaskId(), request.getSuccessorTaskId())) {
            throw new ConflictException("Dependency already exists.");
        }

        // Circular Dependency Prevention using DFS path lookup (detects if successor blocks predecessor)
        if (hasPath(request.getSuccessorTaskId(), request.getPredecessorTaskId(), new HashSet<>())) {
            throw new BusinessRuleException("Circular dependency detected.");
        }

        TaskDependency dependency = new TaskDependency();
        dependency.setPredecessorTask(predecessor);
        dependency.setSuccessorTask(successor);
        dependency.setType(request.getType());

        TaskDependency saved = taskDependencyRepository.save(dependency);

        eventPublisher.publishEvent(new TaskDependencyCreatedEvent(
                saved.getId(),
                request.getPredecessorTaskId(),
                request.getSuccessorTaskId(),
                actorId,
                LocalDateTime.now()
        ));

        return taskDependencyMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void removeDependency(Long dependencyId, Long actorId) {
        TaskDependency dependency = taskDependencyRepository.findById(dependencyId)
                .filter(d -> !d.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Dependency not found with ID: " + dependencyId));

        if (!projectPermissionService.hasPermission(dependency.getPredecessorTask().getProject().getId(), actorId, "UPDATE_TASK")) {
            throw new ForbiddenException("User does not have permission to manage dependencies.");
        }

        dependency.markDeleted(actorId.toString());
        taskDependencyRepository.save(dependency);

        eventPublisher.publishEvent(new TaskDependencyRemovedEvent(
                dependency.getId(),
                dependency.getPredecessorTask().getId(),
                dependency.getSuccessorTask().getId(),
                actorId,
                LocalDateTime.now()
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDependencyResponse> getDependencies(Long taskId, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view project tasks.");
        }

        List<TaskDependency> all = new ArrayList<>();
        all.addAll(taskDependencyRepository.findBySuccessorTaskIdAndIsDeletedFalse(taskId));
        all.addAll(taskDependencyRepository.findByPredecessorTaskIdAndIsDeletedFalse(taskId));

        return taskDependencyMapper.toResponseList(all);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getBlockingTasks(Long taskId, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view project tasks.");
        }
        return taskMapper.toResponseList(taskDependencyRepository.findBlockingTasks(taskId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getDependentTasks(Long taskId, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view project tasks.");
        }
        return taskMapper.toResponseList(taskDependencyRepository.findDependentTasks(taskId));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasDependencies(Long taskId) {
        return taskDependencyRepository.countDependencies(taskId) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public void validateDependencies(Long taskId) {
        if (!canStartTask(taskId)) {
            throw new BusinessRuleException("Cannot start task due to unfinished predecessor tasks (FINISH_TO_START).");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canStartTask(Long taskId) {
        List<TaskDependency> predecessors = taskDependencyRepository.findBySuccessorTaskIdAndIsDeletedFalse(taskId);
        for (TaskDependency dep : predecessors) {
            if (dep.getType() == TaskDependencyType.FINISH_TO_START) {
                if (dep.getPredecessorTask().getStatus() != TaskStatus.DONE) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasBlockingDependencies(Long taskId) {
        List<TaskDependency> predecessors = taskDependencyRepository.findBySuccessorTaskIdAndIsDeletedFalse(taskId);
        for (TaskDependency dep : predecessors) {
            if (dep.getType() == TaskDependencyType.FINISH_TO_START && dep.getPredecessorTask().getStatus() != TaskStatus.DONE) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDependencyResponse> getDependencyGraph(Long taskId, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view task dependency graph.");
        }
        List<TaskDependency> deps = taskDependencyRepository.findByProjectIdAndIsDeletedFalse(task.getProject().getId());
        return taskDependencyMapper.toResponseList(deps);
    }

    @Override
    @Transactional(readOnly = true)
    public long countDependencies(Long taskId) {
        return taskDependencyRepository.countDependencies(taskId);
    }

    private boolean hasPath(Long current, Long target, Set<Long> visited) {
        if (current.equals(target)) {
            return true;
        }
        if (visited.contains(current)) {
            return false;
        }
        visited.add(current);

        List<TaskDependency> successors = taskDependencyRepository.findByPredecessorTaskIdAndIsDeletedFalse(current);
        for (TaskDependency dep : successors) {
            if (hasPath(dep.getSuccessorTask().getId(), target, visited)) {
                return true;
            }
        }
        return false;
    }

    private Task getTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
    }
}
