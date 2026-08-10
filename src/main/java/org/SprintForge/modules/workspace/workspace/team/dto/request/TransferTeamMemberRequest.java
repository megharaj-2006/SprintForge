package org.SprintForge.modules.workspace.workspace.team.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferTeamMemberRequest {
    @NotNull(message = "Member ID is required")
    private Long memberId;
    
    @NotNull(message = "Target team ID is required")
    private Long targetTeamId;
}
