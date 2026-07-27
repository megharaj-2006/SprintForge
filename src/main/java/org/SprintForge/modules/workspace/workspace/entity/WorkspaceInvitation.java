package org.SprintForge.modules.workspace.workspace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceInvitationStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "workspace_invitations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceInvitation extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "invitee_email", nullable = false)
    private String email;

    @Column(name = "invited_user_id")
    private Long invitedUserId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "inviter_id", nullable = false)
    private Long invitedBy;

    @Column(name = "token", unique = true)
    private String inviteToken;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WorkspaceInvitationStatus status = WorkspaceInvitationStatus.PENDING;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;
}
