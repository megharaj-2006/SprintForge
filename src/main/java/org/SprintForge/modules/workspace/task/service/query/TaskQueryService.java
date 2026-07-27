package org.SprintForge.modules.workspace.task.service.query;

import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskStatisticsResponse;

import java.util.List;

public interface TaskQueryService {

    TaskResponse getTask(Long id, Long actorId);

    List<TaskResponse> getTasks(Long projectId, Long actorId);

    List<TaskResponse> getBacklog(Long projectId, Long actorId);

    List<TaskResponse> searchTasks(Long projectId, String query, Long actorId);

    List<TaskResponse> getArchivedTasks(Long projectId, Long actorId);

    TaskStatisticsResponse getTaskStatistics(Long projectId, Long actorId);
}
