package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceFavoritedEvent(
        Long workspaceId,
        Long userId,
        boolean favorited,
        LocalDateTime timestamp
) {}
