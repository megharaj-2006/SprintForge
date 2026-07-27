package org.SprintForge.modules.workspace.sprint.event;

import java.time.LocalDateTime;

public record SprintUpdatedEvent(
        Long sprintId,
        Long actorId,
        LocalDateTime timestamp
) {}
