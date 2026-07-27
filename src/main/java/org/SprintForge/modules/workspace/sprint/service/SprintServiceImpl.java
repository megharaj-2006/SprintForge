package org.SprintForge.modules.workspace.sprint.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintCreateRequest;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintDuplicateRequest;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintUpdateRequest;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintDetailResponse;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintResponse;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;
import org.SprintForge.modules.workspace.sprint.service.management.SprintLifecycleService;
import org.SprintForge.modules.workspace.sprint.service.query.SprintQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService {

    private final SprintLifecycleService sprintLifecycleService;
    private final SprintQueryService sprintQueryService;

    @Override
    public SprintResponse createSprint(SprintCreateRequest request, Long actorId) {
        return sprintLifecycleService.createSprint(request, actorId);
    }

    @Override
    public SprintResponse updateSprint(Long id, SprintUpdateRequest request, Long actorId) {
        return sprintLifecycleService.updateSprint(id, request, actorId);
    }

    @Override
    public SprintResponse startSprint(Long id, Long actorId) {
        return sprintLifecycleService.startSprint(id, actorId);
    }

    @Override
    public SprintResponse completeSprint(Long id, Long actorId) {
        return sprintLifecycleService.completeSprint(id, actorId);
    }

    @Override
    public SprintResponse cancelSprint(Long id, Long actorId) {
        return sprintLifecycleService.cancelSprint(id, actorId);
    }

    @Override
    public SprintResponse archiveSprint(Long id, Long actorId) {
        return sprintLifecycleService.archiveSprint(id, actorId);
    }

    @Override
    public SprintResponse restoreSprint(Long id, Long actorId) {
        return sprintLifecycleService.restoreSprint(id, actorId);
    }

    @Override
    public void deleteSprint(Long id, Long actorId) {
        sprintLifecycleService.deleteSprint(id, actorId);
    }

    @Override
    public SprintResponse duplicateSprint(Long id, SprintDuplicateRequest request, Long actorId) {
        return sprintLifecycleService.duplicateSprint(id, request, actorId);
    }

    @Override
    public SprintResponse getSprint(Long id, Long actorId) {
        return sprintQueryService.getSprint(id, actorId);
    }

    @Override
    public SprintDetailResponse getSprintDetail(Long id, Long actorId) {
        return sprintQueryService.getSprintDetail(id, actorId);
    }

    @Override
    public List<SprintResponse> getProjectSprints(Long projectId, Long actorId) {
        return sprintQueryService.getProjectSprints(projectId, actorId);
    }

    @Override
    public SprintResponse getActiveSprint(Long projectId, Long actorId) {
        return sprintQueryService.getActiveSprint(projectId, actorId);
    }

    @Override
    public List<SprintResponse> getSprintsByStatus(Long projectId, SprintStatus status, Long actorId) {
        return sprintQueryService.getSprintsByStatus(projectId, status, actorId);
    }
}