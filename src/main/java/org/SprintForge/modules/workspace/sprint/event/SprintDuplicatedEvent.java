package org.SprintForge.modules.workspace.sprint.event;

import java.time.LocalDateTime;

public record SprintDuplicatedEvent(
        Long sourceSprintId,
        Long duplicatedSprintId,
        Long actorId,
        LocalDateTime timestamp
) {}
