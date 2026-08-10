package org.SprintForge.modules.workspace.workspace.security.webhook.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceWebhook;
import org.SprintForge.modules.workspace.workspace.security.webhook.dto.response.WebhookDeliveryResponse;
import org.SprintForge.modules.workspace.workspace.security.webhook.entity.WebhookDelivery;
import org.SprintForge.modules.workspace.workspace.security.webhook.repository.WebhookDeliveryRepository;
import org.SprintForge.modules.workspace.workspace.security.webhook.repository.WorkspaceWebhookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WebhookDeliveryServiceImpl implements WebhookDeliveryService {

    private final WebhookDeliveryRepository webhookDeliveryRepository;
    private final WorkspaceWebhookRepository workspaceWebhookRepository;

    @Override
    public WebhookDelivery recordDelivery(Long webhookId, Long workspaceId, String eventType, String payload, int httpStatus, String responseBody, long durationMs, boolean success, String errorMessage) {
        WebhookDelivery delivery = WebhookDelivery.builder()
                .webhookId(webhookId)
                .workspaceId(workspaceId)
                .eventType(eventType)
                .payload(payload)
                .responseStatus(httpStatus)
                .responseBody(responseBody)
                .durationMs(durationMs)
                .isSuccess(success)
                .errorMessage(errorMessage)
                .attemptNumber(1)
                .build();
        
        delivery = webhookDeliveryRepository.save(delivery);

        workspaceWebhookRepository.findById(webhookId).ifPresent(webhook -> {
            webhook.setLastDeliveryAt(LocalDateTime.now());
            webhook.setLastDeliveryStatus(success ? "SUCCESS" : "FAILURE");
            if (!success) {
                webhook.setFailureCount(webhook.getFailureCount() + 1);
            }
            workspaceWebhookRepository.save(webhook);
        });

        return delivery;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebhookDeliveryResponse> getDeliveries(Long webhookId, int page, int size) {
        return webhookDeliveryRepository.findByWebhookIdOrderByCreatedAtDesc(webhookId, PageRequest.of(page, size))
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebhookDeliveryResponse> getWorkspaceDeliveries(Long workspaceId, int page, int size) {
        return webhookDeliveryRepository.findByWorkspaceIdOrderByCreatedAtDesc(workspaceId, PageRequest.of(page, size))
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long countFailedDeliveries(Long webhookId) {
        return webhookDeliveryRepository.countByWebhookIdAndIsSuccessFalse(webhookId);
    }

    private WebhookDeliveryResponse mapToResponse(WebhookDelivery d) {
        return WebhookDeliveryResponse.builder()
                .id(d.getId())
                .webhookId(d.getWebhookId())
                .workspaceId(d.getWorkspaceId())
                .eventType(d.getEventType())
                .payload(d.getPayload())
                .responseStatus(d.getResponseStatus())
                .isSuccess(d.getIsSuccess())
                .attemptNumber(d.getAttemptNumber())
                .durationMs(d.getDurationMs())
                .errorMessage(d.getErrorMessage())
                .createdAt(d.getCreatedAt())
                .build();
    }
}
