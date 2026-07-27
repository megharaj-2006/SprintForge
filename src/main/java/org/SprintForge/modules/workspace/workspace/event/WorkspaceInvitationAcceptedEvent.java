package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceInvitationAcceptedEvent(
        Long invitationId,
        Long workspaceId,
        String email,
        Long userId,
        LocalDateTime timestamp
) {}
