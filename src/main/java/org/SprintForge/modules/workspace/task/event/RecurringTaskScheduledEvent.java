package org.SprintForge.modules.workspace.task.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecurringTaskScheduledEvent {
    private final Long recurringTaskId;
    private final Long parentTaskId;
    private final Long actorId;
}
