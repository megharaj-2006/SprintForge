package org.SprintForge.modules.workspace.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSettingsResponse {
    private Long id;
    private Long projectId;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
