package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record ChecklistItemAddedEvent(
    Long checklistItemId,
    Long actorId,
    LocalDateTime timestamp
) {}
