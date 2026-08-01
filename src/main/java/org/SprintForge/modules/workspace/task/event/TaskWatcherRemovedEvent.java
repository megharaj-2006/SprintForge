package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record TaskWatcherRemovedEvent(
    Long taskWatcherId,
    Long taskId,
    Long userId,
    Long actorId,
    LocalDateTime timestamp
) {}
