package org.SprintForge.modules.workspace.project.event;

import java.time.LocalDateTime;

public record ProjectDeletedEvent(
        Long projectId,
        Long actorId,
        LocalDateTime timestamp
) {}
