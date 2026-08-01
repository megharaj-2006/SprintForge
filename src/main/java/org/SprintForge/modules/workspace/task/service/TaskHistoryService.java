package org.SprintForge.modules.workspace.task.service;

import org.SprintForge.modules.workspace.task.dto.response.TaskHistoryResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskHistorySummaryResponse;
import org.SprintForge.modules.workspace.task.entity.enums.TaskHistoryActionType;

import java.util.List;

public interface TaskHistoryService {
    void recordHistory(Long taskId, Long actorId, TaskHistoryActionType actionType, String fieldName, String oldValue, String newValue, String description);
    List<TaskHistoryResponse> getTaskHistory(Long taskId, Long actorId);
    List<TaskHistorySummaryResponse> getRecentActivity(Long taskId, Long actorId);
    void deleteHistory(Long taskId, Long actorId);
}
