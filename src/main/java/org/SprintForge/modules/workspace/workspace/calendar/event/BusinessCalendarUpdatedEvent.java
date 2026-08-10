package org.SprintForge.modules.workspace.workspace.calendar.event;

import java.time.LocalDateTime;

public record BusinessCalendarUpdatedEvent(Long workspaceId, String changeType, Long actorId, LocalDateTime timestamp) {}
