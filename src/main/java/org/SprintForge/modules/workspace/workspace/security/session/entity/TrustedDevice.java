package org.SprintForge.modules.workspace.workspace.security.session.entity;

import jakarta.persistence.*;
import lombok.*;
import org.SprintForge.common.entity.AuditableEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "trusted_devices", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"workspace_id", "user_id", "device_fingerprint"})
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TrustedDevice extends AuditableEntity {
    @Column(name = "workspace_id", nullable = false) private Long workspaceId;
    @Column(name = "user_id", nullable = false) private Long userId;
    @Column(name = "device_fingerprint", nullable = false) private String deviceFingerprint;
    @Column(name = "device_name") private String deviceName;
    @Column(name = "device_type") private String deviceType;
    @Column(name = "trusted_at") private LocalDateTime trustedAt;
    @Column(name = "last_seen_at") private LocalDateTime lastSeenAt;
    @Builder.Default
    @Column(name = "is_active") private Boolean isActive = true;
}
