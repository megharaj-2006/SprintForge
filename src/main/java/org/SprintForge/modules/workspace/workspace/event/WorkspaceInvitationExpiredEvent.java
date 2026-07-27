package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceInvitationExpiredEvent(
        Long invitationId,
        Long workspaceId,
        String email,
        LocalDateTime timestamp
) {}
