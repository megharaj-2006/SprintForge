package org.SprintForge.modules.workspace.workspace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceDefaultView;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceVisibility;

@Entity
@Table(name = "workspaces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Workspace extends SoftDeleteEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", unique = true)
    private String slug;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "icon")
    private String icon;

    @Column(name = "cover_image")
    private String coverImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    private WorkspaceVisibility visibility = WorkspaceVisibility.PRIVATE;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "default_role_id")
    private Long defaultRoleId;

    @Column(name = "default_task_status_id")
    private Long defaultTaskStatusId;

    @Column(name = "default_task_priority_id")
    private Long defaultTaskPriorityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "default_view")
    private WorkspaceDefaultView defaultView = WorkspaceDefaultView.LIST;

    @Column(name = "invite_code")
    private String inviteCode;

    @Column(name = "storage_used")
    private Long storageUsed = 0L;

    @Column(name = "storage_limit")
    private Long storageLimit;

    @Column(name = "max_members")
    private Integer maxMembers;

    @Column(name = "is_archived", nullable = false)
    private boolean isArchived = false;
}
