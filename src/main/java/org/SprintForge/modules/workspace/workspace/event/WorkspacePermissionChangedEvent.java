package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspacePermissionChangedEvent(
        Long workspaceId,
        Long roleId,
        Long actorId,
        LocalDateTime timestamp
) {}
