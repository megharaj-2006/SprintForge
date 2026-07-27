package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record TaskCreatedEvent(
    Long taskId,
    Long projectId,
    Long actorId,
    LocalDateTime timestamp
) {}
