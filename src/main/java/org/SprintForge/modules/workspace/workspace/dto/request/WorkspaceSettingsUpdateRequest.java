package org.SprintForge.modules.workspace.workspace.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceSettingsUpdateRequest {

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
}
