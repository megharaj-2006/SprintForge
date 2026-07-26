package org.SprintForge.modules.workspace.workspace.service.settings;

import org.SprintForge.modules.workspace.workspace.dto.request.WorkspacePreferenceRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspacePreferenceResponse;

public interface WorkspacePreferenceService {

    WorkspacePreferenceResponse getPreferences(Long workspaceId, Long userId, Long actorId);

    WorkspacePreferenceResponse updatePreferences(Long workspaceId, Long userId, WorkspacePreferenceRequest request, Long actorId);
}
