package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record LabelUpdatedEvent(
    Long labelId,
    Long actorId,
    LocalDateTime timestamp
) {}