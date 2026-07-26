package org.SprintForge.modules.workspace.workspace.entity;

import jakarta.persistence.*;
import lombok.*;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "workspace_preferences", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"workspace_id", "user_id"})
}, indexes = {
        @Index(name = "idx_workspace_pref_composite", columnList = "workspace_id, user_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkspacePreference extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder.Default
    @Column(name = "theme", nullable = false)
    private String theme = "LIGHT"; // LIGHT, DARK, SYSTEM

    @Builder.Default
    @Column(name = "email_notifications", nullable = false)
    private boolean emailNotifications = true;

    @Builder.Default
    @Column(name = "push_notifications", nullable = false)
    private boolean pushNotifications = true;

    @Builder.Default
    @Column(name = "in_app_notifications", nullable = false)
    private boolean inAppNotifications = true;

    @Builder.Default
    @Column(name = "sidebar_collapsed", nullable = false)
    private boolean sidebarCollapsed = false;
}
