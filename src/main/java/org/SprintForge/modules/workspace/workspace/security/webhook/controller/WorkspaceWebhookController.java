package org.SprintForge.modules.workspace.workspace.security.webhook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.security.webhook.dto.request.CreateWebhookRequest;
import org.SprintForge.modules.workspace.workspace.security.webhook.dto.request.UpdateWebhookRequest;
import org.SprintForge.modules.workspace.workspace.security.webhook.dto.response.WebhookDeliveryResponse;
import org.SprintForge.modules.workspace.workspace.security.webhook.dto.response.WebhookResponse;
import org.SprintForge.modules.workspace.workspace.security.webhook.service.WebhookDeliveryService;
import org.SprintForge.modules.workspace.workspace.security.webhook.service.WebhookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workspaces/{workspaceId}/webhooks")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@Tag(name = "Webhook Management")
public class WorkspaceWebhookController {

    private final WebhookService webhookService;
    private final WebhookDeliveryService webhookDeliveryService;

    @PostMapping
    public ResponseEntity<WebhookResponse> registerWebhook(
            @PathVariable Long workspaceId,
            @Valid @RequestBody CreateWebhookRequest request,
            @RequestHeader("X-User-Id") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(webhookService.registerWebhook(workspaceId, request, actorId));
    }

    @GetMapping
    public List<WebhookResponse> listWebhooks(@PathVariable Long workspaceId) {
        return webhookService.listWebhooks(workspaceId);
    }

    @GetMapping("/{webhookId}")
    public WebhookResponse getWebhook(
            @PathVariable Long workspaceId,
            @PathVariable Long webhookId) {
        return webhookService.getWebhook(workspaceId, webhookId);
    }

    @PatchMapping("/{webhookId}")
    public WebhookResponse updateWebhook(
            @PathVariable Long workspaceId,
            @PathVariable Long webhookId,
            @Valid @RequestBody UpdateWebhookRequest request,
            @RequestHeader("X-User-Id") Long actorId) {
        return webhookService.updateWebhook(workspaceId, webhookId, request, actorId);
    }

    @DeleteMapping("/{webhookId}")
    public ResponseEntity<Void> deleteWebhook(
            @PathVariable Long workspaceId,
            @PathVariable Long webhookId,
            @RequestHeader("X-User-Id") Long actorId) {
        webhookService.deleteWebhook(workspaceId, webhookId, actorId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{webhookId}/enable")
    public WebhookResponse enableWebhook(
            @PathVariable Long workspaceId,
            @PathVariable Long webhookId,
            @RequestHeader("X-User-Id") Long actorId) {
        return webhookService.enableWebhook(workspaceId, webhookId, actorId);
    }

    @PostMapping("/{webhookId}/disable")
    public WebhookResponse disableWebhook(
            @PathVariable Long workspaceId,
            @PathVariable Long webhookId,
            @RequestHeader("X-User-Id") Long actorId) {
        return webhookService.disableWebhook(workspaceId, webhookId, actorId);
    }

    @GetMapping("/{webhookId}/deliveries")
    public List<WebhookDeliveryResponse> getDeliveries(
            @PathVariable Long workspaceId,
            @PathVariable Long webhookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return webhookDeliveryService.getDeliveries(webhookId, page, size);
    }
}
