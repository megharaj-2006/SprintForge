package org.SprintForge.modules.workspace.project.progress.service;

import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.goal.entity.Goal;
import org.SprintForge.modules.workspace.project.goal.repository.GoalRepository;
import org.SprintForge.modules.workspace.project.keyresult.entity.KeyResult;
import org.SprintForge.modules.workspace.project.keyresult.repository.KeyResultRepository;
import org.SprintForge.modules.workspace.project.objective.entity.Objective;
import org.SprintForge.modules.workspace.project.objective.repository.ObjectiveRepository;
import org.SprintForge.modules.workspace.project.progress.dto.GoalProgressResponse;
import org.SprintForge.modules.workspace.project.progress.dto.ProjectProgressResponse;
import org.SprintForge.modules.workspace.project.release.repository.ReleaseRepository;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProgressEngineServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private ObjectiveRepository objectiveRepository;

    @Mock
    private KeyResultRepository keyResultRepository;

    @Mock
    private ReleaseRepository releaseRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private ProgressEngineServiceImpl progressEngineService;

    private Project testProject;
    private Goal testGoal;
    private Objective testObjective;
    private KeyResult testKeyResult;

    @BeforeEach
    void setUp() {
        testProject = new Project();
        testProject.setId(1L);
        testProject.setName("Strategic Project");

        testGoal = new Goal();
        testGoal.setId(10L);
        testGoal.setProjectId(1L);
        testGoal.setTitle("Goal 1");

        testObjective = new Objective();
        testObjective.setId(20L);
        testObjective.setGoalId(10L);
        testObjective.setTitle("Objective 1");

        testKeyResult = new KeyResult();
        testKeyResult.setId(30L);
        testKeyResult.setObjectiveId(20L);
        testKeyResult.setTargetValue(100.0);
        testKeyResult.setCurrentValue(50.0);
        testKeyResult.setWeight(1.0);
    }

    @Test
    void calculateObjectiveProgress_Success() {
        when(objectiveRepository.findById(20L)).thenReturn(Optional.of(testObjective));
        when(keyResultRepository.findByObjectiveIdAndIsDeletedFalse(20L)).thenReturn(List.of(testKeyResult));

        Double progress = progressEngineService.calculateObjectiveProgress(20L);
        assertEquals(50.0, progress);
    }

    @Test
    void calculateGoalProgress_Success() {
        when(goalRepository.findById(10L)).thenReturn(Optional.of(testGoal));
        when(objectiveRepository.findByGoalIdAndIsDeletedFalse(10L)).thenReturn(List.of(testObjective));
        when(objectiveRepository.findById(20L)).thenReturn(Optional.of(testObjective));
        when(keyResultRepository.findByObjectiveIdAndIsDeletedFalse(20L)).thenReturn(List.of(testKeyResult));

        GoalProgressResponse response = progressEngineService.calculateGoalProgress(10L);

        assertNotNull(response);
        assertEquals(50.0, response.getProgressPercentage());
        assertEquals(1, response.getTotalObjectives());
    }

    @Test
    void calculateProjectProgress_Success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(goalRepository.findByProjectIdAndIsDeletedFalse(1L)).thenReturn(List.of(testGoal));
        when(releaseRepository.findByProjectIdAndIsDeletedFalse(1L)).thenReturn(Collections.emptyList());

        Task doneTask = new Task();
        doneTask.setStatus(TaskStatus.DONE);
        Task todoTask = new Task();
        todoTask.setStatus(TaskStatus.TODO);

        when(taskRepository.findByProjectIdAndIsDeletedFalse(1L)).thenReturn(List.of(doneTask, todoTask));

        when(goalRepository.findById(10L)).thenReturn(Optional.of(testGoal));
        when(objectiveRepository.findByGoalIdAndIsDeletedFalse(10L)).thenReturn(List.of(testObjective));
        when(objectiveRepository.findById(20L)).thenReturn(Optional.of(testObjective));
        when(keyResultRepository.findByObjectiveIdAndIsDeletedFalse(20L)).thenReturn(List.of(testKeyResult));

        ProjectProgressResponse response = progressEngineService.calculateProjectProgress(1L);

        assertNotNull(response);
        assertEquals("Strategic Project", response.getProjectName());
        assertEquals(50.0, response.getGoalsProgressPercentage());
        assertEquals(50.0, response.getTasksProgressPercentage());
        assertTrue(response.getOverallProgressPercentage() > 0);
    }
}
