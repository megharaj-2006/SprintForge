package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceInvitationRejectedEvent(
        Long invitationId,
        Long workspaceId,
        String email,
        LocalDateTime timestamp
) {}
