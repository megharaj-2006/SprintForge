package org.SprintForge.modules.workspace.analytics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "workspace_backups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceBackup extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "backup_type")
    private String backupType;

    @Column(name = "storage_location")
    private String storageLocation;

    @Column(name = "size")
    private Long size;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}

