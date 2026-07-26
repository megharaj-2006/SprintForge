package org.SprintForge.modules.workspace.workspace.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceVisibility;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceSearchRequest {

    private String query;
    private WorkspaceVisibility visibility;
    private Boolean isArchived;
    private Long ownerId;
    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer size = 20;
    @Builder.Default
    private String sortBy = "createdAt";
    @Builder.Default
    private String sortDirection = "DESC";
}
