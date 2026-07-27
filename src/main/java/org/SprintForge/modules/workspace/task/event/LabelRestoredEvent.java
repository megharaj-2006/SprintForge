package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record LabelRestoredEvent(
    Long labelId,
    Long actorId,
    LocalDateTime timestamp
) {}