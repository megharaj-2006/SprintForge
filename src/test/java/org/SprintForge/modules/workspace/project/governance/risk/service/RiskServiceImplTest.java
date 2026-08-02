package org.SprintForge.modules.workspace.project.governance.risk.service;

import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.governance.risk.dto.request.CreateRiskRequest;
import org.SprintForge.modules.workspace.project.governance.risk.dto.response.RiskResponse;
import org.SprintForge.modules.workspace.project.governance.risk.entity.GovernanceRisk;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskCategory;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskImpact;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskProbability;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskStatus;
import org.SprintForge.modules.workspace.project.governance.risk.repository.GovernanceRiskRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RiskServiceImplTest {

    @Mock
    private GovernanceRiskRepository riskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private RiskServiceImpl riskService;

    private Project testProject;
    private GovernanceRisk testRisk;

    @BeforeEach
    void setUp() {
        testProject = new Project();
        testProject.setId(1L);

        testRisk = new GovernanceRisk();
        testRisk.setId(10L);
        testRisk.setProjectId(1L);
        testRisk.setTitle("Security Vulnerability Risk");
        testRisk.setCategory(RiskCategory.SECURITY);
        testRisk.setProbability(RiskProbability.HIGH);
        testRisk.setImpact(RiskImpact.CRITICAL);
        testRisk.setStatus(RiskStatus.IDENTIFIED);
        testRisk.setIdentifiedDate(LocalDate.now());
        testRisk.calculateRiskScore();
    }

    @Test
    void createRisk_Success() {
        CreateRiskRequest request = CreateRiskRequest.builder()
                .title("Security Vulnerability Risk")
                .category(RiskCategory.SECURITY)
                .probability(RiskProbability.HIGH)
                .impact(RiskImpact.CRITICAL)
                .build();

        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(riskRepository.save(any(GovernanceRisk.class))).thenReturn(testRisk);

        RiskResponse response = riskService.createRisk(1L, request, 100L);

        assertNotNull(response);
        assertEquals("Security Vulnerability Risk", response.getTitle());
        assertEquals(RiskCategory.SECURITY, response.getCategory());
        assertEquals("CRITICAL", response.getSeverity());
    }

    @Test
    void getRisks_Success() {
        when(riskRepository.findByProjectIdAndIsDeletedFalse(1L)).thenReturn(Collections.singletonList(testRisk));

        List<RiskResponse> responses = riskService.getRisks(1L);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Security Vulnerability Risk", responses.get(0).getTitle());
    }

    @Test
    void createMitigationTask_Success() {
        TaskResponse dummyTask = TaskResponse.builder().id(50L).title("[Mitigation] Security Vulnerability Risk").build();

        when(riskRepository.findById(10L)).thenReturn(Optional.of(testRisk));
        when(taskService.createTask(any(), eq(100L))).thenReturn(dummyTask);

        TaskResponse response = riskService.createMitigationTask(10L, 100L);

        assertNotNull(response);
        assertEquals(50L, response.getId());
        assertEquals(RiskStatus.MITIGATING, testRisk.getStatus());
    }
}
