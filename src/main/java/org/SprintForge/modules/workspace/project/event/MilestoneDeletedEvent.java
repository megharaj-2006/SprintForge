package org.SprintForge.modules.workspace.project.event;

import java.time.LocalDateTime;

public record MilestoneDeletedEvent(
        Long milestoneId,
        Long projectId,
        Long actorId,
        LocalDateTime timestamp
) {}
