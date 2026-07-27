package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record TaskRestoredEvent(
    Long taskId,
    Long actorId,
    LocalDateTime timestamp
) {}
