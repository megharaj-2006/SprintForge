package org.SprintForge.modules.workspace.analytics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "workspace_archives")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceArchive extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "archived_by")
    private Long archivedBy;

    @Column(name = "archived_at")
    private LocalDateTime archivedAt;

    @Column(name = "restore_deadline")
    private LocalDateTime restoreDeadline;
}

