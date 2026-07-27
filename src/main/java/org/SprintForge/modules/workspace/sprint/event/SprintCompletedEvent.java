package org.SprintForge.modules.workspace.sprint.event;

import java.time.LocalDateTime;

public record SprintCompletedEvent(
        Long sprintId,
        Long actorId,
        LocalDateTime timestamp
) {}
