package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record SubtaskMovedEvent(
    Long taskId,
    Long oldParentTaskId,
    Long newParentTaskId,
    Long actorId,
    LocalDateTime timestamp
) {}
