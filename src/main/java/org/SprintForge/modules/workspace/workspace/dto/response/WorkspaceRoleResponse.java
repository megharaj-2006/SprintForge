package org.SprintForge.modules.workspace.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceRoleResponse {

    private Long id;
    private Long workspaceId;
    private String name;
    private String description;
    private String color;
    private Integer priority;
    private Boolean isSystemRole;
    private Boolean isDefaultRole;
    private String permissions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
