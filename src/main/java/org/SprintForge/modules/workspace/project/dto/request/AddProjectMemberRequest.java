package org.SprintForge.modules.workspace.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProjectMemberRequest {

    @NotNull(message = "Workspace member ID is required")
    private Long workspaceMemberId;

    @NotBlank(message = "Project role name is required")
    private String roleName;
}
