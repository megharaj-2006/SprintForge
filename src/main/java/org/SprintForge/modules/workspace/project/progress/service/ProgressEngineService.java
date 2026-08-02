package org.SprintForge.modules.workspace.project.progress.service;

import org.SprintForge.modules.workspace.project.progress.dto.GoalProgressResponse;
import org.SprintForge.modules.workspace.project.progress.dto.ProjectProgressResponse;

public interface ProgressEngineService {
    ProjectProgressResponse calculateProjectProgress(Long projectId);
    GoalProgressResponse calculateGoalProgress(Long goalId);
    Double calculateObjectiveProgress(Long objectiveId);
}
