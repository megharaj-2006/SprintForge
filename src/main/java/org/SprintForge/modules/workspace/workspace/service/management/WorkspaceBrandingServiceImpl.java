package org.SprintForge.modules.workspace.workspace.service.management;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.dto.request.WorkspaceBrandingRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceResponse;
import org.SprintForge.modules.workspace.workspace.entity.Workspace;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceRole;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceSettings;
import org.SprintForge.modules.workspace.workspace.exception.WorkspaceException;
import org.SprintForge.modules.workspace.workspace.mapper.WorkspaceMapper;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRoleRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceSettingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkspaceBrandingServiceImpl implements WorkspaceBrandingService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceSettingsRepository workspaceSettingsRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceRoleRepository workspaceRoleRepository;
    private final WorkspaceMapper workspaceMapper;

    @Override
    @Transactional
    public WorkspaceResponse updateBranding(Long id, WorkspaceBrandingRequest request, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));
        WorkspaceSettings settings = workspaceSettingsRepository.findByWorkspaceIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new WorkspaceException("Workspace settings not found."));

        if (request.getIcon() != null) workspace.setIcon(request.getIcon());
        if (request.getBanner() != null) {
            workspace.setCoverImage(request.getBanner());
            settings.setBanner(request.getBanner());
        }
        if (request.getLogo() != null) settings.setLogo(request.getLogo());
        if (request.getPrimaryColor() != null) settings.setPrimaryColor(request.getPrimaryColor());
        if (request.getSecondaryColor() != null) settings.setSecondaryColor(request.getSecondaryColor());
        if (request.getTheme() != null) settings.setTheme(request.getTheme());
        if (request.getCustomDomain() != null) settings.setCustomDomain(request.getCustomDomain());
        if (request.getFavicon() != null) settings.setFavicon(request.getFavicon());

        workspaceSettingsRepository.save(settings);
        Workspace saved = workspaceRepository.save(workspace);
        return workspaceMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public WorkspaceResponse updateLogo(Long id, String logoUrl, Long actorId) {
        return updateBranding(id, WorkspaceBrandingRequest.builder().logo(logoUrl).build(), actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse updateIcon(Long id, String iconUrl, Long actorId) {
        return updateBranding(id, WorkspaceBrandingRequest.builder().icon(iconUrl).build(), actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse updateBanner(Long id, String bannerUrl, Long actorId) {
        return updateBranding(id, WorkspaceBrandingRequest.builder().banner(bannerUrl).build(), actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse updateThemeColors(Long id, String primaryColor, String secondaryColor, Long actorId) {
        return updateBranding(id, WorkspaceBrandingRequest.builder().primaryColor(primaryColor).secondaryColor(secondaryColor).build(), actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse updateTheme(Long id, String theme, Long actorId) {
        return updateBranding(id, WorkspaceBrandingRequest.builder().theme(theme).build(), actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse updateCustomDomain(Long id, String customDomain, Long actorId) {
        return updateBranding(id, WorkspaceBrandingRequest.builder().customDomain(customDomain).build(), actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse updateFavicon(Long id, String faviconUrl, Long actorId) {
        return updateBranding(id, WorkspaceBrandingRequest.builder().favicon(faviconUrl).build(), actorId);
    }

    private void checkIsOwnerOrAdmin(Long workspaceId, Long actorId) {
        if (actorId == null) return;
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));
        if (workspace.getOwnerId().equals(actorId)) return;

        WorkspaceMember member = workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, actorId)
                .orElseThrow(() -> new WorkspaceException("Access Denied: Actor is not a member of the workspace."));

        WorkspaceRole role = workspaceRoleRepository.findById(member.getRoleId())
                .orElseThrow(() -> new WorkspaceException("Role not found."));

        if (!"ADMIN".equalsIgnoreCase(role.getName())) {
            throw new WorkspaceException("Access Denied: Only Owner or Admin can perform this operation.");
        }
    }
}
