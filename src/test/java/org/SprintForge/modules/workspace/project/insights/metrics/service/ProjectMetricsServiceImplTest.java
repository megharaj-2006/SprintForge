package org.SprintForge.modules.workspace.project.insights.metrics.service;

import org.SprintForge.modules.workspace.project.entity.Project;

import org.SprintForge.modules.workspace.project.governance.approval.repository.GovernanceApprovalRepository;
import org.SprintForge.modules.workspace.project.governance.decision.repository.GovernanceDecisionRepository;
import org.SprintForge.modules.workspace.project.governance.document.repository.GovernanceDocumentRepository;
import org.SprintForge.modules.workspace.project.governance.risk.repository.GovernanceRiskRepository;
import org.SprintForge.modules.workspace.project.insights.metrics.dto.ProjectMetricsResponse;
import org.SprintForge.modules.workspace.project.repository.ProjectMemberRepository;
import org.SprintForge.modules.workspace.project.progress.dto.ProjectProgressResponse;
import org.SprintForge.modules.workspace.project.progress.service.ProgressEngineService;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectMetricsServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Mock
    private GovernanceRiskRepository riskRepository;

    @Mock
    private GovernanceDecisionRepository decisionRepository;

    @Mock
    private GovernanceApprovalRepository approvalRepository;

    @Mock
    private GovernanceDocumentRepository documentRepository;

    @Mock
    private ProgressEngineService progressEngineService;

    @InjectMocks
    private ProjectMetricsServiceImpl projectMetricsService;

    private Project testProject;

    @BeforeEach
    void setUp() {
        testProject = new Project();
        testProject.setId(1L);
    }

    @Test
    void getProjectMetrics_Success() {
        Task t1 = new Task();
        t1.setId(101L);
        t1.setStatus(TaskStatus.DONE);
        t1.setStoryPoints(5);

        Task t2 = new Task();
        t2.setId(102L);
        t2.setStatus(TaskStatus.IN_PROGRESS);
        t2.setStoryPoints(3);

        ProjectProgressResponse progress = ProjectProgressResponse.builder()
                .projectId(1L)
                .overallProgressPercentage(50.0)
                .goalsProgressPercentage(50.0)
                .releasesProgressPercentage(50.0)
                .build();

        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(taskRepository.findByProjectIdAndIsDeletedFalse(1L)).thenReturn(Arrays.asList(t1, t2));
        when(projectMemberRepository.findByProjectIdAndIsDeletedFalse(1L)).thenReturn(Collections.emptyList());
        when(progressEngineService.calculateProjectProgress(1L)).thenReturn(progress);
        when(riskRepository.findByProjectIdAndIsDeletedFalse(1L)).thenReturn(Collections.emptyList());
        when(decisionRepository.countByProjectIdAndStatusAndIsDeletedFalse(1L, null)).thenReturn(0L);
        when(approvalRepository.findByProjectIdAndIsDeletedFalse(1L)).thenReturn(Collections.emptyList());
        when(documentRepository.countByProjectIdAndIsDeletedFalse(1L)).thenReturn(0L);

        ProjectMetricsResponse metrics = projectMetricsService.getProjectMetrics(1L);

        assertNotNull(metrics);
        assertEquals(1L, metrics.getProjectId());
        assertEquals(2, metrics.getTotalTasks());
        assertEquals(1, metrics.getCompletedTasks());
        assertEquals(1, metrics.getOpenTasks());
        assertEquals(8, metrics.getTotalStoryPoints());
        assertEquals(50.0, metrics.getCompletionPercentage());
    }
}
