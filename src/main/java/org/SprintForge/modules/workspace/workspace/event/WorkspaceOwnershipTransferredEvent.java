package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceOwnershipTransferredEvent(
        Long workspaceId,
        Long previousOwnerId,
        Long newOwnerId,
        LocalDateTime timestamp
) {}
