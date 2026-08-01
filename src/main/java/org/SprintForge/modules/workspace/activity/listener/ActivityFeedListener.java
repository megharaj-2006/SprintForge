package org.SprintForge.modules.workspace.activity.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.workspace.activity.service.ActivityFeedService;
import org.SprintForge.modules.workspace.sprint.event.SprintCompletedEvent;
import org.SprintForge.modules.workspace.sprint.event.SprintStartedEvent;
import org.SprintForge.modules.workspace.sprint.event.TaskMovedToSprintEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActivityFeedListener {

    private final ActivityFeedService activityFeedService;

    @EventListener
    public void handleSprintStarted(SprintStartedEvent event) {
        log.info("ActivityFeedListener recording SprintStartedEvent for sprint {}", event.getSprintId());
        activityFeedService.recordActivity(event.getActorId(), event.getProjectId(), null, "SPRINT_STARTED", "Started sprint #" + event.getSprintId(), null);
    }

    @EventListener
    public void handleSprintCompleted(SprintCompletedEvent event) {
        log.info("ActivityFeedListener recording SprintCompletedEvent for sprint {}", event.getSprintId());
        activityFeedService.recordActivity(event.getActorId(), event.getProjectId(), null, "SPRINT_COMPLETED", "Completed sprint #" + event.getSprintId() + " (" + event.getCompletedTasks() + " tasks completed)", null);
    }

    @EventListener
    public void handleTaskMoved(TaskMovedToSprintEvent event) {
        log.info("ActivityFeedListener recording TaskMovedToSprintEvent for sprint {}", event.getTargetSprintId());
        activityFeedService.recordActivity(event.getActorId(), null, null, "TASK_MOVED", "Moved " + event.getTaskIds().size() + " tasks into sprint #" + event.getTargetSprintId(), null);
    }
}
