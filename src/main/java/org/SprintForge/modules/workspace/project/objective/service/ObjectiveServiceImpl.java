package org.SprintForge.modules.workspace.project.objective.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.goal.entity.Goal;
import org.SprintForge.modules.workspace.project.goal.repository.GoalRepository;
import org.SprintForge.modules.workspace.project.objective.dto.request.CreateObjectiveRequest;
import org.SprintForge.modules.workspace.project.objective.dto.request.UpdateObjectiveRequest;
import org.SprintForge.modules.workspace.project.objective.dto.response.ObjectiveResponse;
import org.SprintForge.modules.workspace.project.objective.entity.Objective;
import org.SprintForge.modules.workspace.project.objective.entity.enums.ObjectiveStatus;
import org.SprintForge.modules.workspace.project.objective.repository.ObjectiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObjectiveServiceImpl implements ObjectiveService {

    private final ObjectiveRepository objectiveRepository;
    private final GoalRepository goalRepository;

    @Override
    @Transactional
    public ObjectiveResponse createObjective(Long goalId, CreateObjectiveRequest request, Long actorId) {
        Goal goal = goalRepository.findById(goalId)
                .filter(g -> !g.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with ID: " + goalId));

        Objective objective = new Objective();
        objective.setGoalId(goalId);
        objective.setTitle(request.getTitle());
        objective.setDescription(request.getDescription());
        objective.setOwnerId(request.getOwnerId() != null ? request.getOwnerId() : actorId);
        objective.setStatus(ObjectiveStatus.NOT_STARTED);
        objective.setWeight(request.getWeight() != null ? request.getWeight() : 1.0);
        objective.setStartDate(request.getStartDate());
        objective.setTargetDate(request.getTargetDate());

        Objective saved = objectiveRepository.save(objective);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public ObjectiveResponse updateObjective(Long objectiveId, UpdateObjectiveRequest request, Long actorId) {
        Objective objective = objectiveRepository.findById(objectiveId)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Objective not found with ID: " + objectiveId));

        if (request.getTitle() != null) objective.setTitle(request.getTitle());
        if (request.getDescription() != null) objective.setDescription(request.getDescription());
        if (request.getOwnerId() != null) objective.setOwnerId(request.getOwnerId());
        if (request.getStatus() != null) objective.setStatus(request.getStatus());
        if (request.getWeight() != null) objective.setWeight(request.getWeight());
        if (request.getStartDate() != null) objective.setStartDate(request.getStartDate());
        if (request.getTargetDate() != null) objective.setTargetDate(request.getTargetDate());

        Objective saved = objectiveRepository.save(objective);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ObjectiveResponse> getObjectives(Long goalId) {
        return objectiveRepository.findByGoalIdAndIsDeletedFalse(goalId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ObjectiveResponse getObjective(Long objectiveId) {
        Objective objective = objectiveRepository.findById(objectiveId)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Objective not found with ID: " + objectiveId));
        return toResponse(objective);
    }

    @Override
    @Transactional
    public void deleteObjective(Long objectiveId, Long actorId) {
        Objective objective = objectiveRepository.findById(objectiveId)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Objective not found with ID: " + objectiveId));

        objective.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        objectiveRepository.save(objective);
    }

    @Override
    @Transactional
    public ObjectiveResponse moveObjective(Long objectiveId, Long newGoalId, Long actorId) {
        Objective objective = objectiveRepository.findById(objectiveId)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Objective not found with ID: " + objectiveId));

        Goal newGoal = goalRepository.findById(newGoalId)
                .filter(g -> !g.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Target Goal not found with ID: " + newGoalId));

        objective.setGoalId(newGoalId);
        Objective saved = objectiveRepository.save(objective);
        return toResponse(saved);
    }

    private ObjectiveResponse toResponse(Objective objective) {
        Long creatorId = null;
        if (objective.getCreatedBy() != null) {
            try {
                creatorId = Long.parseLong(objective.getCreatedBy());
            } catch (NumberFormatException ignored) {}
        }

        return ObjectiveResponse.builder()
                .id(objective.getId())
                .goalId(objective.getGoalId())
                .title(objective.getTitle())
                .description(objective.getDescription())
                .ownerId(objective.getOwnerId())
                .status(objective.getStatus())
                .weight(objective.getWeight())
                .progressPercentage(0.0) // Derived dynamically via ProgressEngineService
                .startDate(objective.getStartDate())
                .targetDate(objective.getTargetDate())
                .totalKeyResults(0)
                .createdBy(creatorId)
                .createdAt(objective.getCreatedAt())
                .updatedAt(objective.getUpdatedAt())
                .build();
    }
}
