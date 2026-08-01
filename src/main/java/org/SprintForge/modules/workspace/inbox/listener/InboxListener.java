package org.SprintForge.modules.workspace.inbox.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.workspace.inbox.service.InboxService;
import org.SprintForge.modules.workspace.sprint.event.SprintCompletedEvent;
import org.SprintForge.modules.workspace.sprint.event.SprintStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InboxListener {

    private final InboxService inboxService;

    @EventListener
    public void handleSprintStarted(SprintStartedEvent event) {
        log.info("InboxListener handling SprintStartedEvent for sprint {}", event.getSprintId());
        inboxService.createInboxItem(event.getActorId(), null, "SPRINT_UPDATE", "Sprint #" + event.getSprintId() + " Started", "The sprint has been started and is now active.");
    }

    @EventListener
    public void handleSprintCompleted(SprintCompletedEvent event) {
        log.info("InboxListener handling SprintCompletedEvent for sprint {}", event.getSprintId());
        inboxService.createInboxItem(event.getActorId(), null, "SPRINT_UPDATE", "Sprint #" + event.getSprintId() + " Completed", "Sprint completed with " + event.getCompletedTasks() + " completed tasks.");
    }
}
