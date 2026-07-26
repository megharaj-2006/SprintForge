package org.SprintForge.modules.workspace.workspace.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.auth.security.UserPrincipal;
import org.SprintForge.modules.workspace.workspace.dto.request.*;
import org.SprintForge.modules.workspace.workspace.dto.response.*;
import org.SprintForge.modules.workspace.workspace.entity.Workspace;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.exception.WorkspaceException;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRepository;
import org.SprintForge.modules.workspace.workspace.service.management.WorkspaceLifecycleService;
import org.SprintForge.modules.workspace.workspace.service.management.WorkspaceQueryService;
import org.SprintForge.modules.workspace.workspace.service.settings.WorkspacePreferenceService;
import org.SprintForge.modules.workspace.workspace.service.settings.WorkspaceSettingsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceLifecycleService workspaceLifecycleService;
    private final WorkspaceQueryService workspaceQueryService;
    private final WorkspaceSettingsService workspaceSettingsService;
    private final WorkspacePreferenceService workspacePreferenceService;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    @Override
    @Transactional
    public WorkspaceResponse createWorkspace(WorkspaceCreateRequest request) {
        return workspaceLifecycleService.createWorkspace(request, getCurrentUserId());
    }

    @Override
    @Transactional
    public WorkspaceResponse updateWorkspace(Long id, WorkspaceUpdateRequest request) {
        return workspaceLifecycleService.updateWorkspace(id, request, getCurrentUserId());
    }

    @Override
    @Transactional
    public void deleteWorkspace(Long id) {
        workspaceLifecycleService.deleteWorkspace(id, getCurrentUserId());
    }

    @Override
    @Transactional
    public WorkspaceResponse archiveWorkspace(Long id) {
        return workspaceLifecycleService.archiveWorkspace(id, getCurrentUserId());
    }

    @Override
    @Transactional
    public WorkspaceResponse restoreWorkspace(Long id) {
        return workspaceLifecycleService.restoreWorkspace(id, getCurrentUserId());
    }

    @Override
    @Transactional
    public WorkspaceResponse duplicateWorkspace(Long workspaceId) {
        return workspaceLifecycleService.duplicateWorkspace(workspaceId, getCurrentUserId());
    }

    @Override
    @Transactional
    public WorkspaceResponse cloneWorkspace(Long workspaceId, WorkspaceCloneRequest request) {
        return workspaceLifecycleService.cloneWorkspace(workspaceId, request, getCurrentUserId());
    }

    @Override
    @Transactional
    public WorkspaceResponse transferOwnership(Long id, Long newOwnerId) {
        return workspaceLifecycleService.transferOwnership(id, newOwnerId, getCurrentUserId());
    }

    @Override
    @Transactional
    public void leaveWorkspace(Long id, Long userId) {
        Long actorId = getCurrentUserId();
        if (actorId != null && !userId.equals(actorId)) {
            throw new WorkspaceException("Access Denied: You cannot make another user leave the workspace.");
        }

        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));

        if (workspace.getOwnerId().equals(userId)) {
            throw new WorkspaceException("Workspace Owner cannot leave the workspace. Please transfer ownership first.");
        }

        WorkspaceMember member = workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new WorkspaceException("User is not a member of this workspace."));

        member.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        workspaceMemberRepository.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkspaceResponse getWorkspace(Long id) {
        return workspaceQueryService.getWorkspace(id, getCurrentUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public WorkspaceResponse getWorkspaceBySlug(String slug) {
        return workspaceQueryService.getWorkspaceBySlug(slug, getCurrentUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public WorkspaceDetailResponse getWorkspaceDetails(Long id) {
        return workspaceQueryService.getWorkspaceDetails(id, getCurrentUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public WorkspaceSummaryResponse getWorkspaceSummary(Long id) {
        return workspaceQueryService.getWorkspaceSummary(id, getCurrentUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceResponse> listUserWorkspaces(Long userId) {
        return workspaceQueryService.listUserWorkspaces(userId, getCurrentUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceResponse> listArchivedWorkspaces() {
        return workspaceQueryService.listArchivedWorkspaces(getCurrentUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceResponse> searchWorkspaces(WorkspaceSearchRequest request) {
        return workspaceQueryService.searchWorkspaces(request, getCurrentUserId());
    }

    @Override
    @Transactional
    public void favoriteWorkspace(Long id, Long userId) {
        workspaceLifecycleService.favoriteWorkspace(id, userId, getCurrentUserId());
    }

    @Override
    @Transactional
    public void unfavoriteWorkspace(Long id, Long userId) {
        workspaceLifecycleService.unfavoriteWorkspace(id, userId, getCurrentUserId());
    }

    @Override
    @Transactional
    public void pinWorkspace(Long id, Long userId) {
        workspaceLifecycleService.pinWorkspace(id, userId, getCurrentUserId());
    }

    @Override
    @Transactional
    public void unpinWorkspace(Long id, Long userId) {
        workspaceLifecycleService.unpinWorkspace(id, userId, getCurrentUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceResponse> recentWorkspaces(Long userId, int limit) {
        return workspaceQueryService.recentWorkspaces(userId, limit, getCurrentUserId());
    }

    @Override
    public String generateWorkspaceSlug(String name) {
        return workspaceLifecycleService.generateWorkspaceSlug(name);
    }

    @Override
    public boolean validateWorkspaceSlug(String slug) {
        return workspaceLifecycleService.validateWorkspaceSlug(slug);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkspaceHealthReportResponse getWorkspaceHealth(Long id) {
        return workspaceLifecycleService.generateWorkspaceHealthReport(id);
    }

    @Override
    @Transactional
    public void recalculateWorkspaceStatistics(Long id) {
        workspaceQueryService.recalculateWorkspaceStatistics(id, getCurrentUserId());
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            return ((UserPrincipal) authentication.getPrincipal()).getUser().getId();
        }
        return null;
    }
}