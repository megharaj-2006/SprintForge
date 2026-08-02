package org.SprintForge.modules.workspace.project.goal.service;

import org.SprintForge.modules.workspace.project.goal.dto.request.CreateGoalRequest;
import org.SprintForge.modules.workspace.project.goal.dto.request.UpdateGoalRequest;
import org.SprintForge.modules.workspace.project.goal.dto.response.GoalResponse;

import java.util.List;

public interface GoalService {
    GoalResponse createGoal(Long projectId, CreateGoalRequest request, Long actorId);
    GoalResponse updateGoal(Long goalId, UpdateGoalRequest request, Long actorId);
    List<GoalResponse> getGoals(Long projectId);
    GoalResponse getGoal(Long goalId);
    void deleteGoal(Long goalId, Long actorId);
    GoalResponse archiveGoal(Long goalId, Long actorId);
    GoalResponse cloneGoal(Long goalId, Long actorId);
}
