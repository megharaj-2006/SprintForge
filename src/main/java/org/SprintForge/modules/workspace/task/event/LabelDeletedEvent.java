package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record LabelDeletedEvent(
    Long labelId,
    Long actorId,
    LocalDateTime timestamp
) {}