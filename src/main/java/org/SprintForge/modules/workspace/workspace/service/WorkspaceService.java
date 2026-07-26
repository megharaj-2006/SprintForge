package org.SprintForge.modules.workspace.workspace.service;

import org.SprintForge.modules.workspace.workspace.dto.request.*;
import org.SprintForge.modules.workspace.workspace.dto.response.*;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceVisibility;

import java.util.List;

public interface WorkspaceService {

    WorkspaceResponse createWorkspace(WorkspaceCreateRequest request);

    WorkspaceResponse updateWorkspace(Long id, WorkspaceUpdateRequest request);

    void deleteWorkspace(Long id);

    WorkspaceResponse archiveWorkspace(Long id);

    WorkspaceResponse restoreWorkspace(Long id);

    WorkspaceResponse duplicateWorkspace(Long workspaceId);

    WorkspaceResponse cloneWorkspace(Long workspaceId, WorkspaceCloneRequest request);

    WorkspaceResponse transferOwnership(Long id, Long newOwnerId);

    void leaveWorkspace(Long id, Long userId);

    WorkspaceResponse getWorkspace(Long id);

    WorkspaceResponse getWorkspaceBySlug(String slug);

    WorkspaceDetailResponse getWorkspaceDetails(Long id);

    WorkspaceSummaryResponse getWorkspaceSummary(Long id);

    List<WorkspaceResponse> listUserWorkspaces(Long userId);

    List<WorkspaceResponse> listArchivedWorkspaces();

    List<WorkspaceResponse> searchWorkspaces(WorkspaceSearchRequest request);

    void favoriteWorkspace(Long id, Long userId);

    void unfavoriteWorkspace(Long id, Long userId);

    void pinWorkspace(Long id, Long userId);

    void unpinWorkspace(Long id, Long userId);

    List<WorkspaceResponse> recentWorkspaces(Long userId, int limit);

    String generateWorkspaceSlug(String name);

    boolean validateWorkspaceSlug(String slug);

    WorkspaceHealthReportResponse getWorkspaceHealth(Long id);

    void recalculateWorkspaceStatistics(Long id);
}