package org.SprintForge.modules.workspace.task.event;

import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import java.time.LocalDateTime;

public record TaskReopenedEvent(
    Long taskId,
    TaskStatus fromStatus,
    TaskStatus toStatus,
    Long actorId,
    LocalDateTime timestamp
) {}
