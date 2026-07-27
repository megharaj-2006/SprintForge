package org.SprintForge.modules.workspace.sprint.event;

import java.time.LocalDateTime;

public record SprintStartedEvent(
        Long sprintId,
        Long actorId,
        LocalDateTime timestamp
) {}
