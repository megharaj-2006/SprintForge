package org.SprintForge.modules.workspace.project.event;

import java.time.LocalDateTime;

public record ProjectOwnershipTransferredEvent(
        Long projectId,
        Long previousOwnerId,
        Long newOwnerId,
        Long actorId,
        LocalDateTime timestamp
) {}
