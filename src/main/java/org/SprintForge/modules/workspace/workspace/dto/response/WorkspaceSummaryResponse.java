package org.SprintForge.modules.workspace.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceVisibility;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceSummaryResponse {

    private Long id;
    private String name;
    private String slug;
    private String icon;
    private WorkspaceVisibility visibility;
    private Long ownerId;
    private Integer memberCount;
    private boolean isArchived;
}
