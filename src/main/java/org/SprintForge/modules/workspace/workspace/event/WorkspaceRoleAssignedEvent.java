package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceRoleAssignedEvent(
        Long workspaceId,
        Long userId,
        Long roleId,
        Long actorId,
        LocalDateTime timestamp
) {}
