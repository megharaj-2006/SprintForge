package org.SprintForge.modules.workspace.workspace.team.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {
    private Long id;
    private Long workspaceId;
    private Long departmentId;
    private String name;
    private String description;
    private String color;
    private String icon;
    private Long leadMemberId;
    private Long parentTeamId;
    private boolean isArchived;
    private Long memberCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
