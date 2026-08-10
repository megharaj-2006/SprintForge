package org.SprintForge.modules.workspace.workspace.security.webhook.service;

import org.SprintForge.modules.workspace.workspace.security.webhook.dto.request.CreateWebhookRequest;
import org.SprintForge.modules.workspace.workspace.security.webhook.dto.request.UpdateWebhookRequest;
import org.SprintForge.modules.workspace.workspace.security.webhook.dto.response.WebhookResponse;

import java.util.List;

public interface WebhookService {
    WebhookResponse registerWebhook(Long workspaceId, CreateWebhookRequest request, Long actorId);
    WebhookResponse updateWebhook(Long workspaceId, Long webhookId, UpdateWebhookRequest request, Long actorId);
    void deleteWebhook(Long workspaceId, Long webhookId, Long actorId);
    List<WebhookResponse> listWebhooks(Long workspaceId);
    WebhookResponse getWebhook(Long workspaceId, Long webhookId);
    WebhookResponse enableWebhook(Long workspaceId, Long webhookId, Long actorId);
    WebhookResponse disableWebhook(Long workspaceId, Long webhookId, Long actorId);
}
