package org.SprintForge.modules.workspace.project.event;

import java.time.LocalDateTime;

public record ProjectCreatedEvent(
        Long projectId,
        Long workspaceId,
        Long creatorId,
        LocalDateTime timestamp
) {}
