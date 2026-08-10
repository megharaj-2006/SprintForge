package org.SprintForge.modules.workspace.workspace.team.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTeamRequest {
    private String name;
    private String description;
    private String color;
    private String icon;
    private Long leadMemberId;
    private Long departmentId;
}
