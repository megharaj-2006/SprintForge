package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record ChecklistCreatedEvent(
    Long checklistId,
    Long actorId,
    LocalDateTime timestamp
) {}
