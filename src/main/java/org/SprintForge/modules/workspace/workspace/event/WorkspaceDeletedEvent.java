package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceDeletedEvent(
        Long workspaceId,
        Long deleterId,
        boolean hardDelete,
        LocalDateTime timestamp
) {}
