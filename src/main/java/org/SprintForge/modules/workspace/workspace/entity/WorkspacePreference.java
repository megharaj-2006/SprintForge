package org.SprintForge.modules.workspace.workspace.entity;

import jakarta.persistence.*;
import lombok.*;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "workspace_preferences", indexes = {
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

    @Column(name = "user_id", nullable = true)
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

    // Workspace-level preferences
    @Builder.Default
    @Column(name = "timezone")
    private String timezone = "UTC";

    @Builder.Default
    @Column(name = "language")
    private String language = "en";

    @Builder.Default
    @Column(name = "date_format")
    private String dateFormat = "YYYY-MM-DD";

    @Builder.Default
    @Column(name = "time_format")
    private String timeFormat = "24H";

    @Builder.Default
    @Column(name = "week_starts_on")
    private String weekStartsOn = "MONDAY";

    @Builder.Default
    @Column(name = "working_days", columnDefinition = "TEXT")
    private String workingDays = "MON,TUE,WED,THU,FRI";

    @Builder.Default
    @Column(name = "business_hours_start")
    private String businessHoursStart = "09:00";

    @Builder.Default
    @Column(name = "business_hours_end")
    private String businessHoursEnd = "17:00";
}
