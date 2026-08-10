package org.SprintForge.modules.workspace.workspace.security.session.entity;

import jakarta.persistence.*;
import lombok.*;
import org.SprintForge.common.entity.AuditableEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "workspace_sessions", indexes = {
    @Index(name = "idx_session_workspace_user", columnList = "workspace_id, user_id"),
    @Index(name = "idx_session_token", columnList = "session_token")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkspaceSession extends AuditableEntity {
    @Column(name = "workspace_id", nullable = false) private Long workspaceId;
    @Column(name = "user_id", nullable = false) private Long userId;
    @Column(name = "session_token", nullable = false, unique = true) private String sessionToken;
    @Column(name = "ip_address") private String ipAddress;
    @Column(name = "user_agent") private String userAgent;
    @Column(name = "device_type") private String deviceType;
    @Column(name = "location") private String location;
    @Builder.Default
    @Column(name = "is_active") private Boolean isActive = true;
    @Column(name = "last_activity_at") private LocalDateTime lastActivityAt;
    @Column(name = "expires_at") private LocalDateTime expiresAt;
}
