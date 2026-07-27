package org.SprintForge.modules.workspace.sprint.service;

import org.SprintForge.modules.workspace.sprint.dto.request.SprintCreateRequest;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintDuplicateRequest;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintUpdateRequest;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintDetailResponse;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintResponse;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;

import java.util.List;

public interface SprintService {

    // Lifecycle Service
    SprintResponse createSprint(SprintCreateRequest request, Long actorId);

    SprintResponse updateSprint(Long id, SprintUpdateRequest request, Long actorId);

    SprintResponse startSprint(Long id, Long actorId);

    SprintResponse completeSprint(Long id, Long actorId);

    SprintResponse cancelSprint(Long id, Long actorId);

    SprintResponse archiveSprint(Long id, Long actorId);

    SprintResponse restoreSprint(Long id, Long actorId);

    void deleteSprint(Long id, Long actorId);

    SprintResponse duplicateSprint(Long id, SprintDuplicateRequest request, Long actorId);

    // Query Service
    SprintResponse getSprint(Long id, Long actorId);

    SprintDetailResponse getSprintDetail(Long id, Long actorId);

    List<SprintResponse> getProjectSprints(Long projectId, Long actorId);

    SprintResponse getActiveSprint(Long projectId, Long actorId);

    List<SprintResponse> getSprintsByStatus(Long projectId, SprintStatus status, Long actorId);
}