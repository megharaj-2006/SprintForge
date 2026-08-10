package org.SprintForge.modules.workspace.workspace.entity;

import jakarta.persistence.*;
import lombok.*;
import org.SprintForge.common.entity.AuditableEntity;

@Entity
@Table(name = "workspace_audit_logs", indexes = {
    @Index(name = "idx_audit_workspace_created", columnList = "workspace_id, created_at")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkspaceAuditLog extends AuditableEntity {
    @Column(name = "workspace_id", nullable = false) private Long workspaceId;
    @Column(name = "actor_id") private Long actorId;
    @Column(name = "action") private String action;
    @Column(name = "entity_type") private String entityType;
    @Column(name = "entity_id") private Long entityId;
    @Column(name = "old_value", columnDefinition = "TEXT") private String oldValue;
    @Column(name = "new_value", columnDefinition = "TEXT") private String newValue;
    @Column(name = "ip_address") private String ipAddress;
    @Column(name = "user_agent") private String userAgent;
}
