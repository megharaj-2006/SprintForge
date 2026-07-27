package org.SprintForge.modules.workspace.project.event;

import java.time.LocalDateTime;

public record ProjectDuplicatedEvent(
        Long sourceProjectId,
        Long targetProjectId,
        Long actorId,
        LocalDateTime timestamp
) {}
