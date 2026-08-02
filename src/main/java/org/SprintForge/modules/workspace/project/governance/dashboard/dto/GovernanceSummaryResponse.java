package org.SprintForge.modules.workspace.project.governance.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GovernanceSummaryResponse {

    private Long projectId;
    private double governanceScore;
    private String healthSummary;
    private long totalRisks;
    private long totalDecisions;
    private long totalApprovals;
    private long totalDocuments;
    private long totalChanges;
}
