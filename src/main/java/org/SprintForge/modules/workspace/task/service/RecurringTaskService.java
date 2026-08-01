package org.SprintForge.modules.workspace.task.service;

import org.SprintForge.modules.workspace.task.dto.request.*;
import org.SprintForge.modules.workspace.task.dto.response.OccurrencePreviewResponse;
import org.SprintForge.modules.workspace.task.dto.response.RecurringTaskResponse;

import java.time.LocalDateTime;

public interface RecurringTaskService {

    RecurringTaskResponse scheduleRecurringTask(Long parentTaskId, CreateRecurringTaskRequest request, Long actorId);

    RecurringTaskResponse updateRecurringTask(Long id, UpdateRecurringTaskRequest request, Long actorId);

    void cancelRecurringTask(Long id, Long actorId);

    RecurringTaskResponse pauseRecurringTask(Long id, PauseRecurringTaskRequest request, Long actorId);

    RecurringTaskResponse resumeRecurringTask(Long id, Long actorId);

    OccurrencePreviewResponse previewOccurrences(Long id, PreviewOccurrencesRequest request);

    RecurringTaskResponse getRecurringTaskByTaskId(Long taskId);

    RecurringTaskResponse getRecurringTaskById(Long id);

    void executeDueRecurringTasks();

    LocalDateTime calculateNextRun(org.SprintForge.modules.workspace.task.entity.RecurringTask recurringTask, LocalDateTime fromTime);
}
