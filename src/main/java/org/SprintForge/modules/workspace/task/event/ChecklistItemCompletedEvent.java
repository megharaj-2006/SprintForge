package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record ChecklistItemCompletedEvent(
    Long checklistItemId,
    Boolean completed,
    Long actorId,
    LocalDateTime timestamp
) {}
