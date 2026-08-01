package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record TaskWatcherAddedEvent(
    Long taskWatcherId,
    Long taskId,
    Long userId,
    Long actorId,
    LocalDateTime timestamp
) {}
