package org.SprintForge.modules.workspace.project.release.event;

import java.time.LocalDateTime;

public record ReleasePublishedEvent(
        Long releaseId,
        Long projectId,
        String version,
        Long actorId,
        LocalDateTime timestamp
) {}
