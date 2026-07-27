package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceMemberSuspendedEvent(
        Long workspaceId,
        Long userId,
        Long actorId,
        LocalDateTime timestamp
) {}
