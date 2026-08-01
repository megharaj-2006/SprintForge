package org.SprintForge.modules.workspace.timelog.event;

import java.time.LocalDateTime;

public record TimeTrackingStoppedEvent(
    Long timeEntryId,
    Long taskId,
    Long userId,
    Long durationMinutes,
    LocalDateTime timestamp
) {}
