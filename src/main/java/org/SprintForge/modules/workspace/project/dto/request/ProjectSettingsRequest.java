package org.SprintForge.modules.workspace.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSettingsRequest {
    private Boolean allowMultipleAssignees;
    private Boolean allowTimeTracking;
    private Boolean allowStoryPoints;
    private Boolean allowCustomFields;
    private Boolean allowRecurringTasks;
    private Boolean allowAutomation;
    private Boolean allowGuestAccess;
    private String defaultIssueType;
    private String defaultTaskStatus;
    private String defaultPriority;
    private String defaultView;
}
