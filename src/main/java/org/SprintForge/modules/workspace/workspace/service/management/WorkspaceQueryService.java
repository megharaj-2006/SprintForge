package org.SprintForge.modules.workspace.workspace.service.management;

import org.SprintForge.modules.workspace.workspace.dto.request.WorkspaceSearchRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.*;

import java.util.List;

public interface WorkspaceQueryService {

    WorkspaceResponse getWorkspace(Long id, Long actorId);

    WorkspaceResponse getWorkspaceBySlug(String slug, Long actorId);

    WorkspaceDetailResponse getWorkspaceDetails(Long id, Long actorId);

    WorkspaceSummaryResponse getWorkspaceSummary(Long id, Long actorId);

    List<WorkspaceResponse> listUserWorkspaces(Long userId, Long actorId);

    List<WorkspaceResponse> listArchivedWorkspaces(Long actorId);

    List<WorkspaceResponse> searchWorkspaces(WorkspaceSearchRequest request, Long actorId);

    List<WorkspaceResponse> recentWorkspaces(Long userId, int limit, Long actorId);

    WorkspaceStatisticsResponse getWorkspaceStatistics(Long id, Long actorId);

    void recalculateWorkspaceStatistics(Long id, Long actorId);
}
