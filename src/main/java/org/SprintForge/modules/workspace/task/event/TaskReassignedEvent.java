package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;
import java.util.List;

public record TaskReassignedEvent(
    Long taskId,
    List<Long> projectMemberIds,
    Long actorId,
    LocalDateTime timestamp
) {}
