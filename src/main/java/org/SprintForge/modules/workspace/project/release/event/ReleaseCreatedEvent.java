package org.SprintForge.modules.workspace.project.release.event;

import java.time.LocalDateTime;

public record ReleaseCreatedEvent(
        Long releaseId,
        Long projectId,
        Long actorId,
        LocalDateTime timestamp
) {}
