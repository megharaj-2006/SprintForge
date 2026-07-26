package org.SprintForge.modules.workspace.workspace.service.management;

import org.SprintForge.modules.workspace.workspace.dto.request.*;
import org.SprintForge.modules.workspace.workspace.dto.response.*;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceVisibility;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface WorkspaceLifecycleService {

    // Category A - Creation
    WorkspaceResponse createWorkspace(WorkspaceCreateRequest request, Long actorId);
    WorkspaceResponse createWorkspaceFromTemplate(Long templateId, WorkspaceCreateRequest request, Long actorId);
    WorkspaceResponse createWorkspaceFromImport(Object importRequest, Long actorId);
    WorkspaceResponse createWorkspaceFromBackup(Long backupId, WorkspaceCreateRequest request, Long actorId);
    WorkspaceResponse quickCreateWorkspace(String name, Long actorId);
    WorkspaceResponse cloneWorkspace(Long workspaceId, WorkspaceCloneRequest request, Long actorId);
    WorkspaceResponse duplicateWorkspace(Long workspaceId, Long actorId);
    WorkspaceResponse forkWorkspace(Long workspaceId, Long actorId);
    WorkspaceResponse copyWorkspaceStructure(Long workspaceId, Long actorId);
    WorkspaceResponse copyWorkspaceWithoutData(Long workspaceId, Long actorId);
    WorkspaceResponse copyWorkspaceWithMembers(Long workspaceId, Long actorId);
    WorkspaceResponse copyWorkspaceWithPermissions(Long workspaceId, Long actorId);
    WorkspaceResponse copyWorkspaceWithAutomation(Long workspaceId, Long actorId);
    WorkspaceResponse copyWorkspaceWithIntegrations(Long workspaceId, Long actorId);

    // Category B - Modification
    WorkspaceResponse updateWorkspace(Long id, WorkspaceUpdateRequest request, Long actorId);
    WorkspaceResponse renameWorkspace(Long id, String newName, Long actorId);
    WorkspaceResponse changeWorkspaceSlug(Long id, String newSlug, Long actorId);
    WorkspaceResponse changeWorkspaceDescription(Long id, String description, Long actorId);
    WorkspaceResponse changeWorkspaceCategory(Long id, String category, Long actorId);
    WorkspaceResponse changeWorkspaceVisibility(Long id, WorkspaceVisibility visibility, Long actorId);
    WorkspaceResponse changeWorkspaceType(Long id, String type, Long actorId);
    WorkspaceResponse changeWorkspaceOwnerName(Long id, String ownerName, Long actorId);
    WorkspaceResponse regenerateWorkspaceSlug(Long id, Long actorId);
    WorkspaceResponse updateWorkspaceMetadata(Long id, Map<String, String> metadata, Long actorId);
    WorkspaceResponse updateWorkspaceTags(Long id, List<String> tags, Long actorId);

    // Category C - State Management
    WorkspaceResponse archiveWorkspace(Long id, Long actorId);
    WorkspaceResponse restoreWorkspace(Long id, Long actorId);
    WorkspaceResponse lockWorkspace(Long id, Long actorId);
    WorkspaceResponse unlockWorkspace(Long id, Long actorId);
    WorkspaceResponse freezeWorkspace(Long id, Long actorId);
    WorkspaceResponse unfreezeWorkspace(Long id, Long actorId);
    WorkspaceResponse enableReadOnlyMode(Long id, Long actorId);
    WorkspaceResponse disableReadOnlyMode(Long id, Long actorId);
    WorkspaceResponse activateWorkspace(Long id, Long actorId);
    WorkspaceResponse deactivateWorkspace(Long id, Long actorId);
    WorkspaceResponse scheduleArchive(Long id, LocalDateTime dateTime, Long actorId);
    WorkspaceResponse cancelScheduledArchive(Long id, Long actorId);
    WorkspaceResponse scheduleDeletion(Long id, LocalDateTime dateTime, Long actorId);
    WorkspaceResponse cancelDeletion(Long id, Long actorId);
    WorkspaceResponse markWorkspaceMaintenance(Long id, Long actorId);
    WorkspaceResponse unmarkMaintenance(Long id, Long actorId);

    // Category D - Ownership
    WorkspaceResponse transferOwnership(Long id, Long newOwnerId, Long actorId);
    WorkspaceResponse requestOwnershipTransfer(Long id, Long newOwnerId, Long actorId);
    WorkspaceResponse acceptOwnershipTransfer(Long id, Long requestId, Long actorId);
    WorkspaceResponse rejectOwnershipTransfer(Long id, Long requestId, Long actorId);
    WorkspaceResponse cancelOwnershipTransfer(Long id, Long requestId, Long actorId);
    WorkspaceResponse assignCoOwner(Long id, Long userId, Long actorId);
    WorkspaceResponse removeCoOwner(Long id, Long userId, Long actorId);
    List<WorkspaceMemberResponse> listOwners(Long id);
    boolean verifyOwnership(Long id, Long userId);
    WorkspaceResponse claimAbandonedWorkspace(Long id, Long newOwnerId, Long actorId);

    // Category E - Workspace Relations
    WorkspaceResponse mergeWorkspaces(Long sourceId, Long targetId, Long actorId);
    WorkspaceResponse splitWorkspace(Long workspaceId, WorkspaceSplitRequest request, Long actorId);
    void linkWorkspace(Long workspaceId, Long linkedWorkspaceId, String relationType, Long actorId);
    void unlinkWorkspace(Long workspaceId, Long linkedWorkspaceId, Long actorId);
    WorkspaceResponse convertWorkspaceToTemplate(Long workspaceId, Long actorId);
    WorkspaceResponse createWorkspaceFromWorkspace(Long workspaceId, Long actorId);
    void archiveChildWorkspaces(Long parentWorkspaceId, Long actorId);
    void moveProjectsBetweenWorkspaces(Long sourceWorkspaceId, Long targetWorkspaceId, List<Long> projectIds, Long actorId);

    // Category F - Recovery
    void deleteWorkspace(Long id, Long actorId);
    void softDeleteWorkspace(Long id, Long actorId);
    void hardDeleteWorkspace(Long id, Long actorId);
    WorkspaceResponse recoverWorkspace(Long id, Long actorId);
    WorkspaceResponse recoverWorkspaceVersion(Long id, Integer version, Long actorId);
    WorkspaceResponse undoLastWorkspaceOperation(Long id, Long actorId);
    WorkspaceResponse rollbackWorkspace(Long id, Long snapshotId, Long actorId);
    List<WorkspaceRecoveryHistoryResponse> viewRecoveryHistory(Long id, Long actorId);

    // Category G - Favorites/Follows
    void favoriteWorkspace(Long id, Long userId, Long actorId);
    void unfavoriteWorkspace(Long id, Long userId, Long actorId);
    void pinWorkspace(Long id, Long userId, Long actorId);
    void unpinWorkspace(Long id, Long userId, Long actorId);
    void starWorkspace(Long id, Long userId, Long actorId);
    void unstarWorkspace(Long id, Long userId, Long actorId);
    void followWorkspace(Long id, Long userId, Long actorId);
    void unfollowWorkspace(Long id, Long userId, Long actorId);

    // Category H - Utilities
    boolean workspaceExists(Long id);
    boolean validateWorkspaceName(String name);
    boolean validateWorkspaceSlug(String slug);
    String generateWorkspaceSlug(String name);
    boolean isWorkspaceArchived(Long id);
    boolean isWorkspaceLocked(Long id);
    boolean isWorkspaceFrozen(Long id);
    boolean isWorkspaceEditable(Long id);
    boolean canDeleteWorkspace(Long id, Long userId);
    boolean canArchiveWorkspace(Long id, Long userId);
    boolean canTransferOwnership(Long id, Long userId);

    // Category I - Enterprise
    void exportWorkspaceSnapshot(Long id, Long actorId);
    void duplicateWorkspaceRegion(Long id, String targetRegion, Long actorId);
    void moveWorkspaceRegion(Long id, String targetRegion, Long actorId);
    WorkspaceResponse convertWorkspaceEdition(Long id, String edition, Long actorId);
    WorkspaceResponse migrateWorkspace(Long id, String targetVersion, Long actorId);
    void compressWorkspace(Long id, Long actorId);
    void optimizeWorkspace(Long id, Long actorId);
    void repairWorkspace(Long id, Long actorId);
    boolean verifyWorkspaceIntegrity(Long id, Long actorId);

    // Category J - AI Workspace Lifecycle
    String analyzeWorkspaceStructure(Long id);
    String evaluateWorkspaceOrganization(Long id);
    String recommendWorkspaceStructure(Long id);
    String recommendWorkspaceCleanup(Long id);
    String detectWorkspaceRedundancy(Long id);
    List<Long> detectUnusedProjects(Long id);
    List<Long> detectAbandonedProjects(Long id);
    List<Long> detectInactiveMembers(Long id);
    String recommendWorkspaceSplit(Long id);
    String recommendWorkspaceMerge(Long id);
    String recommendWorkspaceArchive(Long id);
    String predictWorkspaceGrowth(Long id);
    String predictWorkspaceStorage(Long id);
    Double estimateWorkspaceMaintenanceCost(Long id);
    WorkspaceHealthReportResponse generateWorkspaceHealthReport(Long id);
}
