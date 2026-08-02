package org.SprintForge.modules.workspace.project.governance.approval.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.governance.approval.entity.enums.ApprovalStatus;

import java.time.LocalDateTime;

@Entity(name = "GovernanceApproval")
@Table(name = "project_approvals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GovernanceApproval extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "entity_type", nullable = false)
    private String entityType; // e.g. RELEASE, DECISION, BUDGET, REQUIREMENT, CHANGE

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    @Column(name = "requested_by_id")
    private Long requestedById;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt = LocalDateTime.now();

    @Column(name = "approved_by_id")
    private Long approvedById;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;
}
