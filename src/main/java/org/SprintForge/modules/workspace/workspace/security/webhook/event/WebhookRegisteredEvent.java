package org.SprintForge.modules.workspace.workspace.security.webhook.event;

import java.time.LocalDateTime;

public record WebhookRegisteredEvent(Long webhookId, Long workspaceId, Long createdById, LocalDateTime timestamp) {}
