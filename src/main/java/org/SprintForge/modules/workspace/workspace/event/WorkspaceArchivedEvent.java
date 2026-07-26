package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;

public record WorkspaceArchivedEvent(
        Long workspaceId,
        Long archiverId,
        LocalDateTime timestamp
) {}
