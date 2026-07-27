package org.SprintForge.modules.workspace.sprint.service.query;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintDetailResponse;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintGoalResponse;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintResponse;
import org.SprintForge.modules.workspace.sprint.entity.Sprint;
import org.SprintForge.modules.workspace.sprint.entity.SprintGoal;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;
import org.SprintForge.modules.workspace.sprint.exception.SprintNotFoundException;
import org.SprintForge.modules.workspace.sprint.mapper.SprintMapper;
import org.SprintForge.modules.workspace.sprint.repository.SprintGoalRepository;
import org.SprintForge.modules.workspace.sprint.repository.SprintRepository;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SprintQueryServiceImpl implements SprintQueryService {

    private final SprintRepository sprintRepository;
    private final SprintGoalRepository sprintGoalRepository;
    private final ProjectRepository projectRepository;
    private final ProjectPermissionService projectPermissionService;
    private final SprintMapper sprintMapper;

    @Override
    @Transactional(readOnly = true)
    public SprintResponse getSprint(Long id, Long actorId) {
        Sprint sprint = getSprintOrThrow(id);
        checkCanViewProject(sprint.getProjectId(), actorId);
        return sprintMapper.toResponse(sprint);
    }

    @Override
    @Transactional(readOnly = true)
    public SprintDetailResponse getSprintDetail(Long id, Long actorId) {
        Sprint sprint = getSprintOrThrow(id);
        Project project = getProjectOrThrow(sprint.getProjectId());
        checkCanViewProject(sprint.getProjectId(), actorId);

        SprintDetailResponse detail = sprintMapper.toDetailResponse(sprint);
        detail.setProjectName(project.getName());

        double progress = 0.0;
        if (sprint.getTotalTaskCount() != null && sprint.getTotalTaskCount() > 0) {
            int completed = sprint.getCompletedTaskCount() != null ? sprint.getCompletedTaskCount() : 0;
            progress = (completed * 100.0) / sprint.getTotalTaskCount();
        }
        detail.setProgressPercentage(progress);

        List<SprintGoal> goals = sprintGoalRepository.findBySprintIdAndIsDeletedFalse(id);
        List<SprintGoalResponse> goalResponses = sprintMapper.toGoalResponseList(goals);
        detail.setGoals(goalResponses);

        return detail;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SprintResponse> getProjectSprints(Long projectId, Long actorId) {
        getProjectOrThrow(projectId);
        checkCanViewProject(projectId, actorId);
        List<Sprint> sprints = sprintRepository.findByProjectIdAndIsDeletedFalseOrderByOrderIndexAsc(projectId);
        return sprintMapper.toResponseList(sprints);
    }

    @Override
    @Transactional(readOnly = true)
    public SprintResponse getActiveSprint(Long projectId, Long actorId) {
        getProjectOrThrow(projectId);
        checkCanViewProject(projectId, actorId);
        Sprint sprint = sprintRepository.findByProjectIdAndStatusAndIsDeletedFalse(projectId, SprintStatus.ACTIVE)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No active sprint found for project ID: " + projectId));
        return sprintMapper.toResponse(sprint);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SprintResponse> getSprintsByStatus(Long projectId, SprintStatus status, Long actorId) {
        getProjectOrThrow(projectId);
        checkCanViewProject(projectId, actorId);
        List<Sprint> sprints = sprintRepository.findByProjectIdAndStatusAndIsDeletedFalse(projectId, status);
        return sprintMapper.toResponseList(sprints);
    }

    private Project getProjectOrThrow(Long projectId) {
        return projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
    }

    private Sprint getSprintOrThrow(Long sprintId) {
        return sprintRepository.findByIdAndIsDeletedFalse(sprintId)
                .orElseThrow(() -> new SprintNotFoundException(sprintId));
    }

    private void checkCanViewProject(Long projectId, Long actorId) {
        if (!projectPermissionService.canViewProject(projectId, actorId)) {
            throw new ForbiddenException("Access Denied: You do not have permission to view this project's sprints.");
        }
    }
}
