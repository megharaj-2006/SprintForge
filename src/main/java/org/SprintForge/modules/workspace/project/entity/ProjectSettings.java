package org.SprintForge.modules.workspace.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "project_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSettings extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false, unique = true)
    private Long projectId;

    @Column(name = "allow_multiple_assignees")
    private Boolean allowMultipleAssignees = true;

    @Column(name = "allow_time_tracking")
    private Boolean allowTimeTracking = true;

    @Column(name = "allow_story_points")
    private Boolean allowStoryPoints = true;

    @Column(name = "allow_custom_fields")
    private Boolean allowCustomFields = true;

    @Column(name = "allow_recurring_tasks")
    private Boolean allowRecurringTasks = true;

    @Column(name = "allow_automation")
    private Boolean allowAutomation = true;

    @Column(name = "allow_guest_access")
    private Boolean allowGuestAccess = true;

    @Column(name = "default_issue_type")
    private String defaultIssueType;

    @Column(name = "default_task_status")
    private String defaultTaskStatus;

    @Column(name = "default_priority")
    private String defaultPriority;

    @Column(name = "default_view")
    private String defaultView;

    @Column(name = "default_sprint_length_days")
    private Integer defaultSprintLengthDays = 14;

    @Column(name = "working_days")
    private String workingDays = "MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY";

    @Column(name = "business_hours")
    private String businessHours = "09:00-17:00";

    @Column(name = "timezone")
    private String timezone = "UTC";

    @Column(name = "story_point_scale")
    private String storyPointScale = "FIBONACCI";

    @Column(name = "estimate_unit")
    private String estimateUnit = "STORY_POINTS";

    @Column(name = "notification_defaults")
    private Boolean notificationDefaults = true;

    @Column(name = "visibility_defaults")
    private String visibilityDefaults = "WORKSPACE";
}

