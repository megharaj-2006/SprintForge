package org.SprintForge.modules.workspace.sprint.service.management;

import org.SprintForge.modules.workspace.sprint.dto.request.SprintCreateRequest;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintDuplicateRequest;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintUpdateRequest;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintResponse;

public interface SprintLifecycleService {

    SprintResponse createSprint(SprintCreateRequest request, Long actorId);

    SprintResponse updateSprint(Long id, SprintUpdateRequest request, Long actorId);

    SprintResponse startSprint(Long id, Long actorId);

    SprintResponse completeSprint(Long id, Long actorId);

    SprintResponse cancelSprint(Long id, Long actorId);

    SprintResponse archiveSprint(Long id, Long actorId);

    SprintResponse restoreSprint(Long id, Long actorId);

    void deleteSprint(Long id, Long actorId);

    SprintResponse duplicateSprint(Long id, SprintDuplicateRequest request, Long actorId);
}
