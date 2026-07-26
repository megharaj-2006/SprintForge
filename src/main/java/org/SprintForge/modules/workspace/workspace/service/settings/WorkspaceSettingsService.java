package org.SprintForge.modules.workspace.workspace.service.settings;

import org.SprintForge.modules.workspace.workspace.dto.request.WorkspaceSettingsUpdateRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceSettingsResponse;

public interface WorkspaceSettingsService {

    WorkspaceSettingsResponse getSettings(Long workspaceId, Long actorId);

    WorkspaceSettingsResponse updateSettings(Long workspaceId, WorkspaceSettingsUpdateRequest request, Long actorId);
}
