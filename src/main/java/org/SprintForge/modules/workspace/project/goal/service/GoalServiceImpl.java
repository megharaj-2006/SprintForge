package org.SprintForge.modules.workspace.project.goal.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.goal.dto.request.CreateGoalRequest;
import org.SprintForge.modules.workspace.project.goal.dto.request.UpdateGoalRequest;
import org.SprintForge.modules.workspace.project.goal.dto.response.GoalResponse;
import org.SprintForge.modules.workspace.project.goal.entity.Goal;
import org.SprintForge.modules.workspace.project.goal.entity.enums.GoalPriority;
import org.SprintForge.modules.workspace.project.goal.entity.enums.GoalStatus;
import org.SprintForge.modules.workspace.project.goal.repository.GoalRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public GoalResponse createGoal(Long projectId, CreateGoalRequest request, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        Goal goal = new Goal();
        goal.setProjectId(projectId);
        goal.setTitle(request.getTitle());
        goal.setDescription(request.getDescription());
        goal.setOwnerId(request.getOwnerId() != null ? request.getOwnerId() : actorId);
        goal.setPriority(request.getPriority() != null ? request.getPriority() : GoalPriority.MEDIUM);
        goal.setStatus(GoalStatus.DRAFT);
        goal.setStartDate(request.getStartDate());
        goal.setTargetDate(request.getTargetDate());
        goal.setIsArchived(false);

        Goal saved = goalRepository.save(goal);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public GoalResponse updateGoal(Long goalId, UpdateGoalRequest request, Long actorId) {
        Goal goal = goalRepository.findById(goalId)
                .filter(g -> !g.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with ID: " + goalId));

        if (request.getTitle() != null) goal.setTitle(request.getTitle());
        if (request.getDescription() != null) goal.setDescription(request.getDescription());
        if (request.getOwnerId() != null) goal.setOwnerId(request.getOwnerId());
        if (request.getPriority() != null) goal.setPriority(request.getPriority());
        if (request.getStatus() != null) {
            goal.setStatus(request.getStatus());
            if (request.getStatus() == GoalStatus.COMPLETED) {
                goal.setCompletedDate(LocalDateTime.now());
            }
        }
        if (request.getStartDate() != null) goal.setStartDate(request.getStartDate());
        if (request.getTargetDate() != null) goal.setTargetDate(request.getTargetDate());
        if (request.getWeight() != null) goal.setWeight(request.getWeight());
        if (request.getIsArchived() != null) goal.setIsArchived(request.getIsArchived());

        Goal saved = goalRepository.save(goal);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoalResponse> getGoals(Long projectId) {
        return goalRepository.findByProjectIdAndIsDeletedFalse(projectId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GoalResponse getGoal(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .filter(g -> !g.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with ID: " + goalId));
        return toResponse(goal);
    }

    @Override
    @Transactional
    public void deleteGoal(Long goalId, Long actorId) {
        Goal goal = goalRepository.findById(goalId)
                .filter(g -> !g.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with ID: " + goalId));

        goal.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        goalRepository.save(goal);
    }

    @Override
    @Transactional
    public GoalResponse archiveGoal(Long goalId, Long actorId) {
        Goal goal = goalRepository.findById(goalId)
                .filter(g -> !g.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with ID: " + goalId));

        goal.setIsArchived(true);
        goal.setStatus(GoalStatus.ARCHIVED);
        Goal saved = goalRepository.save(goal);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public GoalResponse cloneGoal(Long goalId, Long actorId) {
        Goal source = goalRepository.findById(goalId)
                .filter(g -> !g.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with ID: " + goalId));

        Goal cloned = new Goal();
        cloned.setProjectId(source.getProjectId());
        cloned.setTitle("Copy of " + source.getTitle());
        cloned.setDescription(source.getDescription());
        cloned.setOwnerId(actorId);
        cloned.setPriority(source.getPriority());
        cloned.setStatus(GoalStatus.DRAFT);
        cloned.setStartDate(source.getStartDate());
        cloned.setTargetDate(source.getTargetDate());
        cloned.setWeight(source.getWeight());
        cloned.setIsArchived(false);

        Goal saved = goalRepository.save(cloned);
        return toResponse(saved);
    }

    private GoalResponse toResponse(Goal goal) {
        Long creatorId = null;
        if (goal.getCreatedBy() != null) {
            try {
                creatorId = Long.parseLong(goal.getCreatedBy());
            } catch (NumberFormatException ignored) {}
        }

        return GoalResponse.builder()
                .id(goal.getId())
                .projectId(goal.getProjectId())
                .title(goal.getTitle())
                .description(goal.getDescription())
                .ownerId(goal.getOwnerId())
                .priority(goal.getPriority())
                .status(goal.getStatus())
                .startDate(goal.getStartDate())
                .targetDate(goal.getTargetDate())
                .completedDate(goal.getCompletedDate())
                .weight(goal.getWeight())
                .progressPercentage(0.0) // Derived dynamically via ProgressEngineService
                .isArchived(goal.getIsArchived())
                .totalObjectives(0)
                .createdBy(creatorId)
                .createdAt(goal.getCreatedAt())
                .updatedAt(goal.getUpdatedAt())
                .build();
    }
}
