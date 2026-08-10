package org.SprintForge.modules.workspace.workspace.security.webhook.service;

import org.SprintForge.modules.workspace.workspace.security.webhook.dto.response.WebhookDeliveryResponse;
import org.SprintForge.modules.workspace.workspace.security.webhook.entity.WebhookDelivery;

import java.util.List;

public interface WebhookDeliveryService {
    WebhookDelivery recordDelivery(Long webhookId, Long workspaceId, String eventType, String payload, int httpStatus, String responseBody, long durationMs, boolean success, String errorMessage);
    List<WebhookDeliveryResponse> getDeliveries(Long webhookId, int page, int size);
    List<WebhookDeliveryResponse> getWorkspaceDeliveries(Long workspaceId, int page, int size);
    long countFailedDeliveries(Long webhookId);
}
