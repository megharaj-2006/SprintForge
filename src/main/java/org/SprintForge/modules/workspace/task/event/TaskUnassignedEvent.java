package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record TaskUnassignedEvent(
    Long taskId,
    Long projectMemberId,
    Long actorId,
    LocalDateTime timestamp
) {}
