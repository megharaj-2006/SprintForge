package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceRestoredEvent(
        Long workspaceId,
        Long restorerId,
        LocalDateTime timestamp
) {}
