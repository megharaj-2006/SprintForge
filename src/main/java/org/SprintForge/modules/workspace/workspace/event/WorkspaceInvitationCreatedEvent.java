package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceInvitationCreatedEvent(
        Long invitationId,
        Long workspaceId,
        String email,
        Long invitedBy,
        LocalDateTime timestamp
) {}
