package org.SprintForge.modules.workspace.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceStatisticsResponse {

    private Long workspaceId;
    private Long totalProjects;
    private Long activeProjects;
    private Long totalMembers;
    private Long activeMembers;
    private Long totalSprints;
    private Long activeSprints;
    private Long totalTasks;
    private Long completedTasks;
    private Long storageUsed;
    private Long storageLimit;
    private Double storagePercentageUsed;
}
