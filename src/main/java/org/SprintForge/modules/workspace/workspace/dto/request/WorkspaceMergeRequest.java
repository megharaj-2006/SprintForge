package org.SprintForge.modules.workspace.workspace.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMergeRequest {

    @NotNull(message = "Target workspace ID is required")
    private Long targetWorkspaceId;

    private boolean deleteSource;
}
