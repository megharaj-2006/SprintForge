package org.SprintForge.modules.workspace.project.governance.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GovernanceDashboardResponse {

    private Long projectId;
    private long openRisks;
    private long criticalRisks;
    private long pendingApprovals;
    private long rejectedApprovals;
    private long openDecisions;
    private long documentsCount;
    private long recentChangesCount;
    private double governanceScore; // 0.0 - 100.0
    private String complianceStatus; // HIGH, MODERATE, LOW
}
