package org.SprintForge.modules.workspace.project.event;

import java.time.LocalDateTime;

public record ProjectRestoredEvent(
        Long projectId,
        Long actorId,
        LocalDateTime timestamp
) {}
