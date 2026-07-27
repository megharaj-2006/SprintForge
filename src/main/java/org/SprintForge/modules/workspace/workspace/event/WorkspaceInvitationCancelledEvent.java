package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceInvitationCancelledEvent(
        Long invitationId,
        Long workspaceId,
        String email,
        Long cancelledBy,
        LocalDateTime timestamp
) {}
