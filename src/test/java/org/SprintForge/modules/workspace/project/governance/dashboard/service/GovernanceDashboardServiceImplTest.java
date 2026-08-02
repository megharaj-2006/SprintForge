package org.SprintForge.modules.workspace.project.governance.dashboard.service;

import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.governance.approval.entity.enums.ApprovalStatus;
import org.SprintForge.modules.workspace.project.governance.approval.repository.GovernanceApprovalRepository;
import org.SprintForge.modules.workspace.project.governance.change.repository.GovernanceChangeRepository;
import org.SprintForge.modules.workspace.project.governance.dashboard.dto.GovernanceDashboardResponse;
import org.SprintForge.modules.workspace.project.governance.decision.entity.enums.DecisionStatus;
import org.SprintForge.modules.workspace.project.governance.decision.repository.GovernanceDecisionRepository;
import org.SprintForge.modules.workspace.project.governance.document.repository.GovernanceDocumentRepository;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskStatus;
import org.SprintForge.modules.workspace.project.governance.risk.repository.GovernanceRiskRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GovernanceDashboardServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private GovernanceRiskRepository riskRepository;

    @Mock
    private GovernanceDecisionRepository decisionRepository;

    @Mock
    private GovernanceDocumentRepository documentRepository;

    @Mock
    private GovernanceApprovalRepository approvalRepository;

    @Mock
    private GovernanceChangeRepository changeRepository;

    @InjectMocks
    private GovernanceDashboardServiceImpl dashboardService;

    private Project testProject;

    @BeforeEach
    void setUp() {
        testProject = new Project();
        testProject.setId(1L);
    }

    @Test
    void getProjectGovernance_Success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(riskRepository.countByProjectIdAndStatusAndIsDeletedFalse(1L, RiskStatus.IDENTIFIED)).thenReturn(1L);
        when(riskRepository.countByProjectIdAndStatusAndIsDeletedFalse(1L, RiskStatus.UNDER_REVIEW)).thenReturn(0L);
        when(riskRepository.countByProjectIdAndStatusAndIsDeletedFalse(1L, RiskStatus.MITIGATING)).thenReturn(0L);
        when(riskRepository.countByProjectIdAndSeverityAndIsDeletedFalse(1L, "CRITICAL")).thenReturn(0L);

        when(approvalRepository.countByProjectIdAndStatusAndIsDeletedFalse(1L, ApprovalStatus.PENDING)).thenReturn(0L);
        when(approvalRepository.countByProjectIdAndStatusAndIsDeletedFalse(1L, ApprovalStatus.REJECTED)).thenReturn(0L);

        when(decisionRepository.countByProjectIdAndStatusAndIsDeletedFalse(1L, DecisionStatus.PROPOSED)).thenReturn(1L);
        when(decisionRepository.countByProjectIdAndStatusAndIsDeletedFalse(1L, DecisionStatus.DRAFT)).thenReturn(0L);

        when(documentRepository.countByProjectIdAndIsDeletedFalse(1L)).thenReturn(5L);
        when(changeRepository.countByProjectIdAndIsDeletedFalse(1L)).thenReturn(2L);

        GovernanceDashboardResponse response = dashboardService.getProjectGovernance(1L);

        assertNotNull(response);
        assertEquals(1L, response.getProjectId());
        assertEquals(1L, response.getOpenRisks());
        assertEquals(5L, response.getDocumentsCount());
        assertEquals("HIGH", response.getComplianceStatus());
        assertTrue(response.getGovernanceScore() >= 80.0);
    }
}
