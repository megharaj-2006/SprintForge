package org.SprintForge.modules.workspace.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceSettingsResponse {

    private Long id;
    private Long workspaceId;
    private String timezone;
    private String language;
    private String dateFormat;
    private String timeFormat;
    private String weekStartDay;
    private String workingDays;
    private LocalTime workStartTime;
    private LocalTime workEndTime;
    private Boolean allowGuestUsers;
    private Boolean allowPublicProjects;
    private Boolean allowFileUploads;
    private Boolean allowTimeTracking;
    private Boolean allowAutomation;
    private Boolean allowCustomFields;
    private Boolean allowMultipleAssignees;
    private Boolean allowExternalInvites;
    private Boolean allowWorkspaceExport;
    private Boolean allowWorkspaceClone;
    private Boolean allowProjectTemplates;
    private Boolean allowRecurringTasks;
    private Boolean allowAI;
    private String defaultTaskView;
    private String defaultProjectView;
    private String defaultTaskSorting;
    private String defaultTaskGrouping;
    private String logo;
    private String banner;
    private String primaryColor;
    private String secondaryColor;
    private String theme;
    private String customDomain;
    private String favicon;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
