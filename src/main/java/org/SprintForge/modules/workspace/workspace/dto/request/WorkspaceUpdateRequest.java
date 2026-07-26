package org.SprintForge.modules.workspace.workspace.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceDefaultView;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceVisibility;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceUpdateRequest {

    @Size(min = 2, max = 100, message = "Workspace name must be between 2 and 100 characters")
    private String name;

    @Size(max = 100, message = "Slug must not exceed 100 characters")
    private String slug;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    private String icon;

    private String coverImage;

    private WorkspaceVisibility visibility;

    private Long defaultRoleId;

    private Long defaultTaskStatusId;

    private Long defaultTaskPriorityId;

    private WorkspaceDefaultView defaultView;

    private Long storageLimit;

    private Integer maxMembers;
}
