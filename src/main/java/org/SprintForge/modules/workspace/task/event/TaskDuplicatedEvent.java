package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record TaskDuplicatedEvent(
    Long sourceTaskId,
    Long duplicatedTaskId,
    Long actorId,
    LocalDateTime timestamp
) {}
