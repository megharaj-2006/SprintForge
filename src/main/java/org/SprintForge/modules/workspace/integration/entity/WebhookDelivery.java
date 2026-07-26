package org.SprintForge.modules.workspace.integration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "webhook_deliveries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebhookDelivery extends SoftDeleteEntity {

    @Column(name = "webhook_id", nullable = false)
    private Long webhookId;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "request_body", columnDefinition = "TEXT")
    private String requestBody;

    @Column(name = "response_status")
    private Integer responseStatus;

    @Column(name = "response_body", columnDefinition = "TEXT")
    private String responseBody;

    @Column(name = "delivery_time_ms")
    private Long deliveryTimeMs;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
}

