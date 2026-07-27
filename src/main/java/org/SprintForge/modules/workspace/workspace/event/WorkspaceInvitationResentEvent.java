package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceInvitationResentEvent(
        Long invitationId,
        Long workspaceId,
        String email,
        Long resentBy,
        LocalDateTime timestamp
) {}
