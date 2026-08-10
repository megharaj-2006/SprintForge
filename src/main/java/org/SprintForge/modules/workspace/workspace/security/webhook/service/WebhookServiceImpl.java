package org.SprintForge.modules.workspace.workspace.security.webhook.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceWebhook;
import org.SprintForge.modules.workspace.workspace.security.webhook.dto.request.CreateWebhookRequest;
import org.SprintForge.modules.workspace.workspace.security.webhook.dto.request.UpdateWebhookRequest;
import org.SprintForge.modules.workspace.workspace.security.webhook.dto.response.WebhookResponse;
import org.SprintForge.modules.workspace.workspace.security.webhook.event.WebhookRegisteredEvent;
import org.SprintForge.modules.workspace.workspace.security.webhook.repository.WorkspaceWebhookRepository;
import org.SprintForge.modules.workspace.workspace.validation.WebhookValidator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WebhookServiceImpl implements WebhookService {

    private final WorkspaceWebhookRepository workspaceWebhookRepository;
    private final WebhookValidator webhookValidator;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public WebhookResponse registerWebhook(Long workspaceId, CreateWebhookRequest request, Long actorId) {
        webhookValidator.validateUrl(request.getUrl());
        webhookValidator.validateEvents(request.getEvents());

        WorkspaceWebhook webhook = WorkspaceWebhook.builder()
                .workspaceId(workspaceId)
                .name(request.getName())
                .url(request.getUrl())
                .secret(request.getSecret())
                .events(String.join(",", request.getEvents()))
                .isActive(true)
                .failureCount(0)
                .createdById(actorId)
                .build();
        webhook = workspaceWebhookRepository.save(webhook);

        eventPublisher.publishEvent(new WebhookRegisteredEvent(webhook.getId(), workspaceId, actorId, LocalDateTime.now()));

        return mapToResponse(webhook);
    }

    @Override
    public WebhookResponse updateWebhook(Long workspaceId, Long webhookId, UpdateWebhookRequest request, Long actorId) {
        WorkspaceWebhook webhook = findActiveWebhook(workspaceId, webhookId);

        if (request.getUrl() != null) {
            webhookValidator.validateUrl(request.getUrl());
            webhook.setUrl(request.getUrl());
        }
        if (request.getName() != null) {
            webhook.setName(request.getName());
        }
        if (request.getEvents() != null) {
            webhookValidator.validateEvents(request.getEvents());
            webhook.setEvents(String.join(",", request.getEvents()));
        }
        if (request.getIsActive() != null) {
            webhook.setIsActive(request.getIsActive());
        }

        webhook = workspaceWebhookRepository.save(webhook);
        return mapToResponse(webhook);
    }

    @Override
    public void deleteWebhook(Long workspaceId, Long webhookId, Long actorId) {
        WorkspaceWebhook webhook = findActiveWebhook(workspaceId, webhookId);
        webhook.markDeleted(actorId.toString());
        workspaceWebhookRepository.save(webhook);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebhookResponse> listWebhooks(Long workspaceId) {
        return workspaceWebhookRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WebhookResponse getWebhook(Long workspaceId, Long webhookId) {
        return mapToResponse(findActiveWebhook(workspaceId, webhookId));
    }

    @Override
    public WebhookResponse enableWebhook(Long workspaceId, Long webhookId, Long actorId) {
        WorkspaceWebhook webhook = findActiveWebhook(workspaceId, webhookId);
        webhook.setIsActive(true);
        return mapToResponse(workspaceWebhookRepository.save(webhook));
    }

    @Override
    public WebhookResponse disableWebhook(Long workspaceId, Long webhookId, Long actorId) {
        WorkspaceWebhook webhook = findActiveWebhook(workspaceId, webhookId);
        webhook.setIsActive(false);
        return mapToResponse(workspaceWebhookRepository.save(webhook));
    }

    // ---- helpers ----

    private WorkspaceWebhook findActiveWebhook(Long workspaceId, Long webhookId) {
        WorkspaceWebhook webhook = workspaceWebhookRepository.findById(webhookId)
                .orElseThrow(() -> new ResourceNotFoundException("Webhook not found"));
        if (!webhook.getWorkspaceId().equals(workspaceId) || webhook.isDeleted()) {
            throw new ResourceNotFoundException("Webhook not found in workspace");
        }
        return webhook;
    }

    private WebhookResponse mapToResponse(WorkspaceWebhook webhook) {
        List<String> eventList = webhook.getEvents() != null && !webhook.getEvents().isEmpty()
                ? Arrays.asList(webhook.getEvents().split(","))
                : List.of();

        return WebhookResponse.builder()
                .id(webhook.getId())
                .workspaceId(webhook.getWorkspaceId())
                .name(webhook.getName())
                .url(webhook.getUrl())
                .events(eventList)
                .isActive(webhook.getIsActive())
                .failureCount(webhook.getFailureCount())
                .lastDeliveryAt(webhook.getLastDeliveryAt())
                .lastDeliveryStatus(webhook.getLastDeliveryStatus())
                .createdAt(webhook.getCreatedAt())
                .updatedAt(webhook.getUpdatedAt())
                .build();
    }
}
