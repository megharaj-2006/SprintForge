package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceUpdatedEvent(
        Long workspaceId,
        Long updaterId,
        LocalDateTime timestamp
) {}
