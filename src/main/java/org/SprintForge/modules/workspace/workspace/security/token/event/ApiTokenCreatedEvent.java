package org.SprintForge.modules.workspace.workspace.security.token.event;

import java.time.LocalDateTime;

public record ApiTokenCreatedEvent(Long tokenId, Long workspaceId, Long createdById, LocalDateTime timestamp) {}
