package org.SprintForge.modules.workspace.workspace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalTime;

@Entity
@Table(name = "workspace_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceSettings extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false, unique = true)
    private Long workspaceId;

    @Column(name = "timezone")
    private String timezone = "UTC";

    @Column(name = "language")
    private String language = "en";

    @Column(name = "date_format")
    private String dateFormat = "YYYY-MM-DD";

    @Column(name = "time_format")
    private String timeFormat = "24h";

    @Column(name = "week_start_day")
    private String weekStartDay = "MONDAY";

    @Column(name = "working_days", columnDefinition = "TEXT")
    private String workingDays;

    @Column(name = "work_start_time")
    private LocalTime workStartTime;

    @Column(name = "work_end_time")
    private LocalTime workEndTime;

    @Column(name = "allow_guest_users")
    private Boolean allowGuestUsers = true;

    @Column(name = "allow_public_projects")
    private Boolean allowPublicProjects = true;

    @Column(name = "allow_file_uploads")
    private Boolean allowFileUploads = true;

    @Column(name = "allow_time_tracking")
    private Boolean allowTimeTracking = true;

    @Column(name = "allow_automation")
    private Boolean allowAutomation = true;

    @Column(name = "allow_custom_fields")
    private Boolean allowCustomFields = true;

    @Column(name = "allow_multiple_assignees")
    private Boolean allowMultipleAssignees = true;

    @Column(name = "allow_external_invites")
    private Boolean allowExternalInvites = true;

    @Column(name = "allow_workspace_export")
    private Boolean allowWorkspaceExport = true;

    @Column(name = "allow_workspace_clone")
    private Boolean allowWorkspaceClone = true;

    @Column(name = "allow_project_templates")
    private Boolean allowProjectTemplates = true;

    @Column(name = "allow_recurring_tasks")
    private Boolean allowRecurringTasks = true;

    @Column(name = "allow_ai")
    private Boolean allowAI = true;

    @Column(name = "default_task_view")
    private String defaultTaskView;

    @Column(name = "default_project_view")
    private String defaultProjectView;

    @Column(name = "default_task_sorting")
    private String defaultTaskSorting;

    @Column(name = "default_task_grouping")
    private String defaultTaskGrouping;

    @Column(name = "logo")
    private String logo;

    @Column(name = "banner")
    private String banner;

    @Column(name = "primary_color")
    private String primaryColor;

    @Column(name = "secondary_color")
    private String secondaryColor;

    @Column(name = "theme")
    private String theme;

    @Column(name = "custom_domain")
    private String customDomain;

    @Column(name = "favicon")
    private String favicon;
}

