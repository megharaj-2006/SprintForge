package org.SprintForge.modules.workspace.workspace.preference.service;

import org.SprintForge.modules.workspace.workspace.preference.dto.request.WorkspaceGlobalPreferenceRequest;
import org.SprintForge.modules.workspace.workspace.preference.dto.response.WorkspaceGlobalPreferenceResponse;

public interface WorkspacePreferenceAdminService {
    WorkspaceGlobalPreferenceResponse getWorkspacePreferences(Long workspaceId);
    WorkspaceGlobalPreferenceResponse updateWorkspacePreferences(Long workspaceId, WorkspaceGlobalPreferenceRequest request, Long actorId);
    void resetWorkspacePreferences(Long workspaceId, Long actorId);
}
