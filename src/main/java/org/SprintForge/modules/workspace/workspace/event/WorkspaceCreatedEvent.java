package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceCreatedEvent(
        Long workspaceId,
        Long creatorId,
        LocalDateTime timestamp
) {}
