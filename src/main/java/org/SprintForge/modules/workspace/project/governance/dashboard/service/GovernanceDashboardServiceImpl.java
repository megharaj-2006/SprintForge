package org.SprintForge.modules.workspace.project.governance.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.governance.approval.entity.enums.ApprovalStatus;
import org.SprintForge.modules.workspace.project.governance.approval.repository.GovernanceApprovalRepository;
import org.SprintForge.modules.workspace.project.governance.change.repository.GovernanceChangeRepository;
import org.SprintForge.modules.workspace.project.governance.dashboard.dto.GovernanceDashboardResponse;
import org.SprintForge.modules.workspace.project.governance.dashboard.dto.GovernanceSummaryResponse;
import org.SprintForge.modules.workspace.project.governance.decision.entity.enums.DecisionStatus;
import org.SprintForge.modules.workspace.project.governance.decision.repository.GovernanceDecisionRepository;
import org.SprintForge.modules.workspace.project.governance.document.repository.GovernanceDocumentRepository;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskStatus;
import org.SprintForge.modules.workspace.project.governance.risk.repository.GovernanceRiskRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GovernanceDashboardServiceImpl implements GovernanceDashboardService {

    private final ProjectRepository projectRepository;
    private final GovernanceRiskRepository riskRepository;
    private final GovernanceDecisionRepository decisionRepository;
    private final GovernanceDocumentRepository documentRepository;
    private final GovernanceApprovalRepository approvalRepository;
    private final GovernanceChangeRepository changeRepository;

    @Override
    @Transactional(readOnly = true)
    public GovernanceDashboardResponse getProjectGovernance(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        long openRisks = riskRepository.countByProjectIdAndStatusAndIsDeletedFalse(projectId, RiskStatus.IDENTIFIED)
                + riskRepository.countByProjectIdAndStatusAndIsDeletedFalse(projectId, RiskStatus.UNDER_REVIEW)
                + riskRepository.countByProjectIdAndStatusAndIsDeletedFalse(projectId, RiskStatus.MITIGATING);

        long criticalRisks = riskRepository.countByProjectIdAndSeverityAndIsDeletedFalse(projectId, "CRITICAL");

        long pendingApprovals = approvalRepository.countByProjectIdAndStatusAndIsDeletedFalse(projectId, ApprovalStatus.PENDING);
        long rejectedApprovals = approvalRepository.countByProjectIdAndStatusAndIsDeletedFalse(projectId, ApprovalStatus.REJECTED);

        long openDecisions = decisionRepository.countByProjectIdAndStatusAndIsDeletedFalse(projectId, DecisionStatus.PROPOSED)
                + decisionRepository.countByProjectIdAndStatusAndIsDeletedFalse(projectId, DecisionStatus.DRAFT);

        long docsCount = documentRepository.countByProjectIdAndIsDeletedFalse(projectId);
        long recentChanges = changeRepository.countByProjectIdAndIsDeletedFalse(projectId);

        double baseScore = 100.0;
        baseScore -= (criticalRisks * 15.0);
        baseScore -= (openRisks * 5.0);
        baseScore -= (pendingApprovals * 4.0);
        baseScore -= (rejectedApprovals * 8.0);
        if (docsCount > 0) baseScore += 5.0;

        double finalScore = Math.max(0.0, Math.min(100.0, baseScore));
        String complianceStatus = finalScore >= 80.0 ? "HIGH" : (finalScore >= 50.0 ? "MODERATE" : "LOW");

        return GovernanceDashboardResponse.builder()
                .projectId(projectId)
                .openRisks(openRisks)
                .criticalRisks(criticalRisks)
                .pendingApprovals(pendingApprovals)
                .rejectedApprovals(rejectedApprovals)
                .openDecisions(openDecisions)
                .documentsCount(docsCount)
                .recentChangesCount(recentChanges)
                .governanceScore(finalScore)
                .complianceStatus(complianceStatus)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public GovernanceSummaryResponse getProjectGovernanceSummary(Long projectId) {
        GovernanceDashboardResponse dash = getProjectGovernance(projectId);

        long totalRisks = riskRepository.findByProjectIdAndIsDeletedFalse(projectId).size();
        long totalDecisions = decisionRepository.findByProjectIdAndIsDeletedFalse(projectId).size();
        long totalApprovals = approvalRepository.findByProjectIdAndIsDeletedFalse(projectId).size();
        long totalDocs = documentRepository.findByProjectIdAndIsDeletedFalse(projectId).size();
        long totalChanges = changeRepository.findByProjectIdAndIsDeletedFalse(projectId).size();

        String health = dash.getGovernanceScore() >= 80.0
                ? "Governance is healthy and fully compliant."
                : (dash.getGovernanceScore() >= 50.0 ? "Governance requires attention on open risks or pending approvals." : "Governance status is critical due to unmitigated risks or rejected approvals.");

        return GovernanceSummaryResponse.builder()
                .projectId(projectId)
                .governanceScore(dash.getGovernanceScore())
                .healthSummary(health)
                .totalRisks(totalRisks)
                .totalDecisions(totalDecisions)
                .totalApprovals(totalApprovals)
                .totalDocuments(totalDocs)
                .totalChanges(totalChanges)
                .build();
    }
}
