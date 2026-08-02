package org.SprintForge.modules.workspace.project.service;

import org.SprintForge.modules.workspace.project.dto.response.ProjectDashboardSummaryResponse;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectMemberStatus;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectStatusType;
import org.SprintForge.modules.workspace.project.repository.MilestoneRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectMemberRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRiskRepository;
import org.SprintForge.modules.workspace.project.service.dashboard.ProjectDashboardServiceImpl;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectDashboardServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Mock
    private MilestoneRepository milestoneRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRiskRepository riskRepository;

    @InjectMocks
    private ProjectDashboardServiceImpl dashboardService;

    private Project testProject;

    @BeforeEach
    void setUp() {
        testProject = new Project();
        testProject.setWorkspaceId(1L);
        testProject.setName("SprintForge Core");
        testProject.setProjectKey("SFC");
        testProject.setStatus(ProjectStatusType.ACTIVE);
    }

    @Test
    void getDashboardSummary_Success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));

        Task task1 = new Task();
        task1.setStatus(TaskStatus.DONE);
        Task task2 = new Task();
        task2.setStatus(TaskStatus.IN_PROGRESS);

        when(taskRepository.findByProjectIdAndIsDeletedFalse(1L)).thenReturn(List.of(task1, task2));
        when(projectMemberRepository.countByProjectIdAndStatusAndIsDeletedFalse(1L, ProjectMemberStatus.ACTIVE)).thenReturn(5L);
        when(riskRepository.countByProjectIdAndIsDeletedFalse(1L)).thenReturn(1L);
        when(milestoneRepository.findByProjectIdAndIsDeletedFalse(1L)).thenReturn(Collections.emptyList());

        ProjectDashboardSummaryResponse response = dashboardService.getDashboardSummary(1L, 10L);

        assertNotNull(response);
        assertEquals("SprintForge Core", response.getProjectName());
        assertEquals(2, response.getTotalTasks());
        assertEquals(1, response.getCompletedTasks());
        assertEquals(1, response.getOpenTasks());
        assertEquals(50.0, response.getCompletionPercentage());
        assertEquals(5, response.getTeamSize());
        assertEquals("GOOD", response.getHealthStatus());
    }
}
