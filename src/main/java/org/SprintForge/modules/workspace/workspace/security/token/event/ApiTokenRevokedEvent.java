package org.SprintForge.modules.workspace.workspace.security.token.event;

import java.time.LocalDateTime;

public record ApiTokenRevokedEvent(Long tokenId, Long workspaceId, Long revokedById, LocalDateTime timestamp) {}
