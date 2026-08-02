package org.SprintForge.modules.workspace.project.governance.change.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.governance.change.entity.enums.ChangeStatus;
import org.SprintForge.modules.workspace.project.governance.change.entity.enums.ChangeType;

import java.time.LocalDateTime;

@Entity(name = "GovernanceChange")
@Table(name = "project_changes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GovernanceChange extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_type", nullable = false)
    private ChangeType changeType = ChangeType.SCOPE;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "impact", columnDefinition = "TEXT")
    private String impact;

    @Column(name = "requested_by_id")
    private Long requestedById;

    @Column(name = "approved_by_id")
    private Long approvedById;

    @Column(name = "implemented_by_id")
    private Long implementedById;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ChangeStatus status = ChangeStatus.REQUESTED;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}
