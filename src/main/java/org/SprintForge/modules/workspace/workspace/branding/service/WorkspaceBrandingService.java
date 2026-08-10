package org.SprintForge.modules.workspace.workspace.branding.service;

import org.SprintForge.modules.workspace.workspace.branding.dto.request.WorkspaceBrandingUpdateRequest;
import org.SprintForge.modules.workspace.workspace.branding.dto.response.WorkspaceBrandingResponse;

public interface WorkspaceBrandingService {
    WorkspaceBrandingResponse getBranding(Long workspaceId);
    WorkspaceBrandingResponse updateBranding(Long workspaceId, WorkspaceBrandingUpdateRequest request, Long actorId);
    void resetBranding(Long workspaceId, Long actorId);
}
