package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record ChecklistDeletedEvent(
    Long checklistId,
    Long actorId,
    LocalDateTime timestamp
) {}
