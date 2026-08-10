package org.SprintForge.modules.workspace.workspace.team.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.workspace.team.entity.enums.TeamMemberRole;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignTeamMemberRequest {
    @NotNull(message = "Member ID is required")
    private Long memberId;
    
    @NotNull(message = "Role is required")
    private TeamMemberRole role;
    
    @Min(0)
    @Max(100)
    private Integer allocationPercentage;
}
