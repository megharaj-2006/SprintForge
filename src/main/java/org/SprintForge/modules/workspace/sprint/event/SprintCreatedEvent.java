package org.SprintForge.modules.workspace.sprint.event;

import java.time.LocalDateTime;

public record SprintCreatedEvent(
        Long sprintId,
        Long projectId,
        Long actorId,
        LocalDateTime timestamp
) {}
