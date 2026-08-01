package org.SprintForge.modules.workspace.notification.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.workspace.notification.service.NotificationService;
import org.SprintForge.modules.workspace.sprint.event.SprintCompletedEvent;
import org.SprintForge.modules.workspace.sprint.event.SprintStartedEvent;
import org.SprintForge.modules.workspace.sprint.event.TaskMovedToSprintEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService notificationService;

    @EventListener
    public void handleSprintStarted(SprintStartedEvent event) {
        log.info("NotificationListener handling SprintStartedEvent for sprint {}", event.getSprintId());
        notificationService.createNotification(event.getActorId(), event.getActorId(), "SPRINT_STARTED", "Sprint Started", "Sprint #" + event.getSprintId() + " has officially started.", null, "/sprints/" + event.getSprintId());
    }

    @EventListener
    public void handleSprintCompleted(SprintCompletedEvent event) {
        log.info("NotificationListener handling SprintCompletedEvent for sprint {}", event.getSprintId());
        notificationService.createNotification(event.getActorId(), event.getActorId(), "SPRINT_COMPLETED", "Sprint Completed", "Sprint #" + event.getSprintId() + " was completed.", null, "/sprints/" + event.getSprintId());
    }

    @EventListener
    public void handleTaskMovedToSprint(TaskMovedToSprintEvent event) {
        log.info("NotificationListener handling TaskMovedToSprintEvent for sprint {}", event.getTargetSprintId());
        notificationService.createNotification(event.getActorId(), event.getActorId(), "TASK_MOVED", "Tasks Moved to Sprint", event.getTaskIds().size() + " tasks were moved to sprint.", null, "/sprints/" + event.getTargetSprintId());
    }
}
