package org.SprintForge.modules.workspace.workspace.entity;

import jakarta.persistence.*;
import lombok.*;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "workspace_api_tokens", indexes = {
    @Index(name = "idx_api_token_workspace", columnList = "workspace_id"),
    @Index(name = "idx_api_token_hash", columnList = "token_hash")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkspaceApiToken extends SoftDeleteEntity {
    @Column(name = "workspace_id", nullable = false) private Long workspaceId;
    @Column(name = "name", nullable = false) private String name;
    @Column(name = "token_hash", nullable = false) private String tokenHash;
    @Column(name = "permissions", columnDefinition = "TEXT") private String permissions; // JSON array
    @Column(name = "created_by_id") private Long createdById;
    @Column(name = "expires_at") private LocalDateTime expiresAt;
    @Column(name = "last_used_at") private LocalDateTime lastUsedAt;
    @Builder.Default
    @Column(name = "is_revoked") private Boolean isRevoked = false;
}
