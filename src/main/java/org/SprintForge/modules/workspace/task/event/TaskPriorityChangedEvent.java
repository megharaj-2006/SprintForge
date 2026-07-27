package org.SprintForge.modules.workspace.task.event;

import org.SprintForge.modules.workspace.task.entity.enums.TaskPriority;
import java.time.LocalDateTime;

public record TaskPriorityChangedEvent(
    Long taskId,
    TaskPriority oldPriority,
    TaskPriority newPriority,
    Long actorId,
    LocalDateTime timestamp
) {}
