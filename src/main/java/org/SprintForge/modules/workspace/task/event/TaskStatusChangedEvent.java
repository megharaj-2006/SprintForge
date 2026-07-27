package org.SprintForge.modules.workspace.task.event;

import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import java.time.LocalDateTime;

public record TaskStatusChangedEvent(
    Long taskId,
    TaskStatus oldStatus,
    TaskStatus newStatus,
    Long actorId,
    LocalDateTime timestamp
) {}
