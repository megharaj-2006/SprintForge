package org.SprintForge.modules.workspace.workspace.security.webhook.entity;

import jakarta.persistence.*;
import lombok.*;
import org.SprintForge.common.entity.AuditableEntity;

@Entity(name = "WorkspaceSecurityWebhookDelivery")
@Table(name = "webhook_deliveries", indexes = {
    @Index(name = "idx_webhook_delivery_webhook", columnList = "webhook_id"),
    @Index(name = "idx_webhook_delivery_created", columnList = "created_at")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WebhookDelivery extends AuditableEntity {
    @Column(name = "webhook_id", nullable = false) private Long webhookId;
    @Column(name = "workspace_id", nullable = false) private Long workspaceId;
    @Column(name = "event_type", nullable = false) private String eventType;
    @Column(name = "payload", columnDefinition = "TEXT") private String payload;
    @Column(name = "response_status") private Integer responseStatus; // HTTP status code
    @Column(name = "response_body", columnDefinition = "TEXT") private String responseBody;
    @Column(name = "duration_ms") private Long durationMs;
    @Builder.Default
    @Column(name = "is_success") private Boolean isSuccess = false;
    @Builder.Default
    @Column(name = "attempt_number") private Integer attemptNumber = 1;
    @Column(name = "error_message", columnDefinition = "TEXT") private String errorMessage;
}
