package org.SprintForge.modules.workspace.project.event;

import java.time.LocalDateTime;

public record MilestoneCompletedEvent(
        Long milestoneId,
        Long projectId,
        String milestoneName,
        Long actorId,
        LocalDateTime completedAt,
        LocalDateTime timestamp
) {}
