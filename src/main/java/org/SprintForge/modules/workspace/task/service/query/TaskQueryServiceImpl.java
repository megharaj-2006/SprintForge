package org.SprintForge.modules.workspace.task.service.query;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.service.member.ProjectMemberService;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskStatisticsResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.mapper.TaskMapper;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskQueryServiceImpl implements TaskQueryService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberService projectMemberService;
    private final ProjectPermissionService projectPermissionService;
    private final TaskMapper taskMapper;

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTask(Long id, Long actorId) {
        Task task = taskRepository.findById(id)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));

        checkCanViewProject(task.getProject().getId(), actorId);

        return taskMapper.toResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasks(Long projectId, Long actorId) {
        getProjectOrThrow(projectId);
        checkCanViewProject(projectId, actorId);

        List<Task> tasks = taskRepository.findByProjectIdAndIsDeletedFalse(projectId);
        // Exclude archived tasks from standard tasks query
        return tasks.stream()
                .filter(t -> !Boolean.TRUE.equals(t.getArchived()))
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getBacklog(Long projectId, Long actorId) {
        getProjectOrThrow(projectId);
        checkCanViewProject(projectId, actorId);

        List<Task> backlogTasks = taskRepository.findByProjectIdAndSprintIsNullAndIsDeletedFalseAndArchivedFalse(projectId);
        return taskMapper.toResponseList(backlogTasks);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> searchTasks(Long projectId, String query, Long actorId) {
        getProjectOrThrow(projectId);
        checkCanViewProject(projectId, actorId);

        List<Task> foundTasks = taskRepository.searchTasks(projectId, query);
        return taskMapper.toResponseList(foundTasks);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getArchivedTasks(Long projectId, Long actorId) {
        getProjectOrThrow(projectId);
        checkCanViewProject(projectId, actorId);

        List<Task> archivedTasks = taskRepository.findByProjectIdAndArchivedTrueAndIsDeletedFalse(projectId);
        return taskMapper.toResponseList(archivedTasks);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskStatisticsResponse getTaskStatistics(Long projectId, Long actorId) {
        getProjectOrThrow(projectId);
        checkCanViewProject(projectId, actorId);

        List<Task> tasks = taskRepository.findByProjectIdAndIsDeletedFalse(projectId);

        long totalTasks = 0;
        long todoTasks = 0;
        long inProgressTasks = 0;
        long inReviewTasks = 0;
        long doneTasks = 0;
        long cancelledTasks = 0;
        double totalEstimatedHours = 0;
        double totalActualHours = 0;
        int totalStoryPoints = 0;

        for (Task task : tasks) {
            if (Boolean.TRUE.equals(task.getArchived())) {
                continue; // only calculate statistics for active tasks
            }
            totalTasks++;
            if (task.getStatus() != null) {
                switch (task.getStatus()) {
                    case TODO -> todoTasks++;
                    case IN_PROGRESS -> inProgressTasks++;
                    case IN_REVIEW -> inReviewTasks++;
                    case DONE -> doneTasks++;
                    case CANCELLED -> cancelledTasks++;
                }
            }
            if (task.getEstimatedHours() != null) {
                totalEstimatedHours += task.getEstimatedHours();
            }
            if (task.getActualHours() != null) {
                totalActualHours += task.getActualHours();
            }
            if (task.getStoryPoints() != null) {
                totalStoryPoints += task.getStoryPoints();
            }
        }

        return TaskStatisticsResponse.builder()
                .totalTasks(totalTasks)
                .todoTasks(todoTasks)
                .inProgressTasks(inProgressTasks)
                .inReviewTasks(inReviewTasks)
                .doneTasks(doneTasks)
                .cancelledTasks(cancelledTasks)
                .totalEstimatedHours(totalEstimatedHours)
                .totalActualHours(totalActualHours)
                .totalStoryPoints(totalStoryPoints)
                .build();
    }

    private Project getProjectOrThrow(Long projectId) {
        return projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
    }

    private void checkCanViewProject(Long projectId, Long actorId) {
        if (!projectMemberService.isProjectMember(projectId, actorId)) {
            throw new ForbiddenException("User must be a member of the project to view tasks.");
        }
        if (!projectPermissionService.hasPermission(projectId, actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view project tasks.");
        }
    }
}
