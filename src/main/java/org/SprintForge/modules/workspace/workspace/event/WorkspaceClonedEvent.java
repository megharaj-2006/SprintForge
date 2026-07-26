package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceClonedEvent(
        Long sourceWorkspaceId,
        Long targetWorkspaceId,
        Long performerId,
        LocalDateTime timestamp
) {}
