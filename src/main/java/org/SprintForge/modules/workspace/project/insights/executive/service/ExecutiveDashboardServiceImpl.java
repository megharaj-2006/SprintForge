package org.SprintForge.modules.workspace.project.insights.executive.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.approval.entity.enums.ApprovalStatus;
import org.SprintForge.modules.workspace.project.governance.approval.repository.GovernanceApprovalRepository;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskStatus;
import org.SprintForge.modules.workspace.project.governance.risk.repository.GovernanceRiskRepository;
import org.SprintForge.modules.workspace.project.insights.executive.dto.ExecutiveDashboardResponse;
import org.SprintForge.modules.workspace.project.insights.portfolio.dto.response.PortfolioResponse;
import org.SprintForge.modules.workspace.project.insights.portfolio.service.PortfolioService;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExecutiveDashboardServiceImpl implements ExecutiveDashboardService {

    private final ProjectRepository projectRepository;
    private final PortfolioService portfolioService;
    private final GovernanceRiskRepository riskRepository;
    private final GovernanceApprovalRepository approvalRepository;

    @Override
    @Transactional(readOnly = true)
    public ExecutiveDashboardResponse getExecutiveDashboard(Long workspaceId) {
        long totalProjects = projectRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId).size();
        List<PortfolioResponse> portfolios = portfolioService.getPortfolios(workspaceId);

        double totalCompletion = 0.0;
        if (!portfolios.isEmpty()) {
            for (PortfolioResponse p : portfolios) {
                totalCompletion += p.getOverallProgressPercentage();
            }
        }
        double avgCompletion = !portfolios.isEmpty() ? totalCompletion / portfolios.size() : 0.0;

        long pendingApprovals = approvalRepository.findByStatusAndIsDeletedFalse(ApprovalStatus.PENDING).size();
        long openRisks = riskRepository.countByProjectIdAndStatusAndIsDeletedFalse(workspaceId, RiskStatus.IDENTIFIED);

        String health = avgCompletion >= 70.0 ? "HEALTHY" : (avgCompletion >= 40.0 ? "AT_RISK" : "CRITICAL");

        return ExecutiveDashboardResponse.builder()
                .workspaceId(workspaceId)
                .totalProjects(totalProjects)
                .activeProjects(totalProjects)
                .portfolioCompletionPercentage(avgCompletion)
                .overallHealthStatus(health)
                .totalOpenRisks(openRisks)
                .totalPendingApprovals(pendingApprovals)
                .totalVelocity(145.0)
                .portfolios(portfolios)
                .build();
    }
}
