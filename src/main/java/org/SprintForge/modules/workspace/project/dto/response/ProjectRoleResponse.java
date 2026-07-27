package org.SprintForge.modules.workspace.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRoleResponse {

    private Long id;
    private Long projectId;
    private String name;
    private String description;
    private String color;
    private String permissions;
}
