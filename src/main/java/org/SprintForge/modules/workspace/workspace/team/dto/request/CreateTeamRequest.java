package org.SprintForge.modules.workspace.workspace.team.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamRequest {
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    private String color;
    private String icon;
    
    @NotNull(message = "Workspace ID is required")
    private Long workspaceId;
    
    private Long departmentId;
    private Long leadMemberId;
}
