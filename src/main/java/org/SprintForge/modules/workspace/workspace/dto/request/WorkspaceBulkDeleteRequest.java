package org.SprintForge.modules.workspace.workspace.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceBulkDeleteRequest {

    @NotEmpty(message = "Workspace IDs list must not be empty")
    private List<Long> workspaceIds;

    @Builder.Default
    private boolean forcePermanentDelete = false;
}
