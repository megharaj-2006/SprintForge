package org.SprintForge.modules.workspace.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceHealthReportResponse {

    private Long workspaceId;
    private int healthScore;
    private String status;
    private int redundantProjectsCount;
    private int unusedProjectsCount;
    private int abandonedProjectsCount;
    private int inactiveMembersCount;
    private List<String> recommendations;
    private String growthPrediction;
    private String storagePrediction;
    private Double estimatedMaintenanceCost;
}
