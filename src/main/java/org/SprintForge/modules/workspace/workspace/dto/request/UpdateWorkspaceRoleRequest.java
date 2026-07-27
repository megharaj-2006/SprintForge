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
public class UpdateWorkspaceRoleRequest {

    @Size(min = 2, max = 50, message = "Role name must be between 2 and 50 characters")
    private String name;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    private String color;

    private Integer priority;

    private Boolean isDefaultRole;

    private String permissions;
}
