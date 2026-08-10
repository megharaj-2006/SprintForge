package org.SprintForge.modules.workspace.workspace.team.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamSummaryResponse {
    private Long id;
    private String name;
    private String color;
    private String icon;
    private Long memberCount;
    private boolean isArchived;
}
