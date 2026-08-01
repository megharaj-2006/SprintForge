package org.SprintForge.modules.workspace.task.service;

import org.SprintForge.modules.workspace.task.dto.request.AddWatcherRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskWatcherResponse;

import java.util.List;

public interface TaskWatcherService {

    TaskWatcherResponse addWatcher(Long taskId, AddWatcherRequest request, Long actorId);

    void removeWatcher(Long taskId, Long userId, Long actorId);

    TaskWatcherResponse toggleWatcher(Long taskId, Long actorId);

    List<TaskWatcherResponse> getTaskWatchers(Long taskId, Long actorId);

    boolean isWatching(Long taskId, Long userId);

    List<TaskResponse> getWatchingTasks(Long userId, Long actorId);
}
