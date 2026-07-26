package org.SprintForge.modules.workspace.workspace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "workspace_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMember extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "department")
    private String department;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WorkspaceMemberStatus status = WorkspaceMemberStatus.ACTIVE;

    @Column(name = "joined_via_invite")
    private Boolean joinedViaInvite = false;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Column(name = "last_seen_at")
    private LocalDateTime lastSeenAt;

    @Column(name = "is_favorite_workspace")
    private Boolean isFavoriteWorkspace = false;

    @Column(name = "is_starred")
    private Boolean isStarred = false;

    @Column(name = "notification_preference_id")
    private Long notificationPreferenceId;
}
