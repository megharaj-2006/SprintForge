package org.SprintForge.modules.workspace.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectStatusType;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectVisibility;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSearchRequest {

    private Long workspaceId;
    private String query;
    private ProjectVisibility visibility;
    private ProjectStatusType status;
    private Long ownerId;
    private Boolean isArchived;

    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer size = 20;
    @Builder.Default
    private String sortBy = "createdAt";
    @Builder.Default
    private String sortDirection = "DESC";
}
