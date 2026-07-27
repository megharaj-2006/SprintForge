package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceMemberReactivatedEvent(
        Long workspaceId,
        Long userId,
        Long actorId,
        LocalDateTime timestamp
) {}
