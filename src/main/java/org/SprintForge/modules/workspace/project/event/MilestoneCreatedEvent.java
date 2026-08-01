package org.SprintForge.modules.workspace.project.event;

import java.time.LocalDateTime;

public record MilestoneCreatedEvent(
        Long milestoneId,
        Long projectId,
        String milestoneName,
        Long actorId,
        LocalDateTime timestamp
) {}
