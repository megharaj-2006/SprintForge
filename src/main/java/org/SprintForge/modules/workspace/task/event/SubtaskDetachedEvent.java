package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record SubtaskDetachedEvent(
    Long taskId,
    Long oldParentTaskId,
    Long actorId,
    LocalDateTime timestamp
) {}
