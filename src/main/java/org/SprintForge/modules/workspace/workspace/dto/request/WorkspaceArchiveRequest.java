package org.SprintForge.modules.workspace.workspace.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceArchiveRequest {

    @Size(max = 500, message = "Archive reason must not exceed 500 characters")
    private String reason;

    @Builder.Default
    private boolean archiveAssociatedProjects = false;
}
