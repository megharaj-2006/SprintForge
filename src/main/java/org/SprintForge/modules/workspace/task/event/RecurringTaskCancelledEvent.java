package org.SprintForge.modules.workspace.task.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecurringTaskCancelledEvent {
    private final Long recurringTaskId;
    private final Long actorId;
}
