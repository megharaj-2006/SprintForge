package org.SprintForge.modules.workspace.project.objective.service;

import org.SprintForge.modules.workspace.project.objective.dto.request.CreateObjectiveRequest;
import org.SprintForge.modules.workspace.project.objective.dto.request.UpdateObjectiveRequest;
import org.SprintForge.modules.workspace.project.objective.dto.response.ObjectiveResponse;

import java.util.List;

public interface ObjectiveService {
    ObjectiveResponse createObjective(Long goalId, CreateObjectiveRequest request, Long actorId);
    ObjectiveResponse updateObjective(Long objectiveId, UpdateObjectiveRequest request, Long actorId);
    List<ObjectiveResponse> getObjectives(Long goalId);
    ObjectiveResponse getObjective(Long objectiveId);
    void deleteObjective(Long objectiveId, Long actorId);
    ObjectiveResponse moveObjective(Long objectiveId, Long newGoalId, Long actorId);
}
