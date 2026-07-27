package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record SubtaskCreatedEvent(
    Long taskId,
    Long parentTaskId,
    Long actorId,
    LocalDateTime timestamp
) {}
