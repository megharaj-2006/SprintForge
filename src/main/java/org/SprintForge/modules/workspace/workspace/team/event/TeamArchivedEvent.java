package org.SprintForge.modules.workspace.workspace.team.event;

import java.time.LocalDateTime;

public record TeamArchivedEvent(
    Long teamId, 
    Long workspaceId, 
    Long archivedById, 
    LocalDateTime timestamp
) {}
