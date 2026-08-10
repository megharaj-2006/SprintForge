package org.SprintForge.modules.workspace.workspace.entity;

import jakarta.persistence.*;
import lombok.*;
import org.SprintForge.common.entity.SoftDeleteEntity;
import java.time.LocalDateTime;

@Entity
@Table(name = "workspace_webhooks", indexes = {
    @Index(name = "idx_webhook_workspace", columnList = "workspace_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkspaceWebhook extends SoftDeleteEntity {
    @Column(name = "workspace_id", nullable = false) private Long workspaceId;
    @Column(name = "name", nullable = false) private String name;
    @Column(name = "url", nullable = false) private String url;
    @Column(name = "secret") private String secret;
    @Column(name = "events", columnDefinition = "TEXT") private String events; // JSON array of event names
    @Builder.Default
    @Column(name = "is_active") private Boolean isActive = true;
    @Builder.Default
    @Column(name = "failure_count") private Integer failureCount = 0;
    @Column(name = "last_delivery_at") private LocalDateTime lastDeliveryAt;
    @Column(name = "last_delivery_status") private String lastDeliveryStatus; // SUCCESS/FAILURE
    @Column(name = "created_by_id") private Long createdById;
}
