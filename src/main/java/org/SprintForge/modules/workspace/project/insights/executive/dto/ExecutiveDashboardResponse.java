package org.SprintForge.modules.workspace.project.insights.executive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.insights.portfolio.dto.response.PortfolioResponse;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutiveDashboardResponse {

    private Long workspaceId;
    private long totalProjects;
    private long activeProjects;
    private double portfolioCompletionPercentage;
    private String overallHealthStatus; // HEALTHY, AT_RISK, CRITICAL
    private long totalOpenRisks;
    private long totalPendingApprovals;
    private double totalVelocity;
    private List<PortfolioResponse> portfolios;
}
