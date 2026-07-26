package org.SprintForge.modules.workspace.workspace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceSubscriptionPlan;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceSubscriptionStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "workspace_subscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceSubscription extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false, unique = true)
    private Long workspaceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "plan")
    private WorkspaceSubscriptionPlan plan = WorkspaceSubscriptionPlan.FREE;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WorkspaceSubscriptionStatus status = WorkspaceSubscriptionStatus.ACTIVE;

    @Column(name = "max_projects")
    private Integer maxProjects;

    @Column(name = "max_members")
    private Integer maxMembers;

    @Column(name = "max_storage")
    private Long maxStorage;

    @Column(name = "max_automations")
    private Integer maxAutomations;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}

