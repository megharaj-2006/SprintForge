package org.SprintForge.modules.workspace.workspace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceSplitRequest {

    @NotBlank(message = "New workspace name is required")
    @Size(min = 2, max = 100, message = "Workspace name must be between 2 and 100 characters")
    private String newWorkspaceName;

    @Size(max = 100, message = "Slug must not exceed 100 characters")
    private String newWorkspaceSlug;

    @NotEmpty(message = "At least one project ID must be moved")
    private List<Long> projectIdsToMove;
}
