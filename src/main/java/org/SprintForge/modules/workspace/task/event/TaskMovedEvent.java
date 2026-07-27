package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record TaskMovedEvent(
    Long taskId,
    Long sprintId,
    Long actorId,
    LocalDateTime timestamp
) {}
