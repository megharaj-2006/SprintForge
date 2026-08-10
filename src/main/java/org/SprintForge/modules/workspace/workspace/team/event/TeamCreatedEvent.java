package org.SprintForge.modules.workspace.workspace.team.event;

import java.time.LocalDateTime;

public record TeamCreatedEvent(
    Long teamId, 
    Long workspaceId, 
    Long createdById, 
    LocalDateTime timestamp
) {}
