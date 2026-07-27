package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record TaskDependencyCreatedEvent(
    Long dependencyId,
    Long predecessorTaskId,
    Long successorTaskId,
    Long actorId,
    LocalDateTime timestamp
) {}
