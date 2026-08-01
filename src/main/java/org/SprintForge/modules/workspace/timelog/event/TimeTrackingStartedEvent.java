package org.SprintForge.modules.workspace.timelog.event;

import java.time.LocalDateTime;

public record TimeTrackingStartedEvent(
    Long timeEntryId,
    Long taskId,
    Long userId,
    LocalDateTime timestamp
) {}
