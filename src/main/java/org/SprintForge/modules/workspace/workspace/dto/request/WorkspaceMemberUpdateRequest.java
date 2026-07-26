package org.SprintForge.modules.workspace.workspace.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMemberUpdateRequest {

    private Long roleId;

    @Size(max = 100, message = "Job title must not exceed 100 characters")
    private String jobTitle;

    @Size(max = 100, message = "Department must not exceed 100 characters")
    private String department;

    private WorkspaceMemberStatus status;

    private Boolean isFavoriteWorkspace;

    private Boolean isStarred;

    private Long notificationPreferenceId;
}
