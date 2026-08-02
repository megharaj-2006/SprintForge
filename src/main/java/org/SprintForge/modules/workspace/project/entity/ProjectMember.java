package org.SprintForge.modules.workspace.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectMemberStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "workspace_member_id", nullable = false)
    private Long workspaceMemberId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Column(name = "added_by")
    private Long addedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProjectMemberStatus status = ProjectMemberStatus.ACTIVE;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "favorite")
    private Boolean favorite = false;

    @Column(name = "notifications_enabled")
    private Boolean notificationsEnabled = true;

    @Column(name = "allocation_percentage")
    private Double allocationPercentage = 100.0;

    @Column(name = "last_accessed")
    private LocalDateTime lastAccessed;
}
