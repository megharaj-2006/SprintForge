package org.SprintForge.modules.workspace.sprint.service.query;

import org.SprintForge.modules.workspace.sprint.dto.response.SprintDetailResponse;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintResponse;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;

import java.util.List;

public interface SprintQueryService {

    SprintResponse getSprint(Long id, Long actorId);

    SprintDetailResponse getSprintDetail(Long id, Long actorId);

    List<SprintResponse> getProjectSprints(Long projectId, Long actorId);

    SprintResponse getActiveSprint(Long projectId, Long actorId);

    List<SprintResponse> getSprintsByStatus(Long projectId, SprintStatus status, Long actorId);
}
