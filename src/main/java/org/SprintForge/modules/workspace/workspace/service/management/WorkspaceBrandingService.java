package org.SprintForge.modules.workspace.workspace.service.management;

import org.SprintForge.modules.workspace.workspace.dto.request.WorkspaceBrandingRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceResponse;

public interface WorkspaceBrandingService {

    WorkspaceResponse updateBranding(Long id, WorkspaceBrandingRequest request, Long actorId);

    WorkspaceResponse updateLogo(Long id, String logoUrl, Long actorId);

    WorkspaceResponse updateIcon(Long id, String iconUrl, Long actorId);

    WorkspaceResponse updateBanner(Long id, String bannerUrl, Long actorId);

    WorkspaceResponse updateThemeColors(Long id, String primaryColor, String secondaryColor, Long actorId);

    WorkspaceResponse updateTheme(Long id, String theme, Long actorId);

    WorkspaceResponse updateCustomDomain(Long id, String customDomain, Long actorId);

    WorkspaceResponse updateFavicon(Long id, String faviconUrl, Long actorId);
}
