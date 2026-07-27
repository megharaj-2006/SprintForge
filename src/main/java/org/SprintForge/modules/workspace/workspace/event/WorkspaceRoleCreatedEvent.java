package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceRoleCreatedEvent(
        Long workspaceId,
        Long roleId,
        String roleName,
        Long actorId,
        LocalDateTime timestamp
) {}
