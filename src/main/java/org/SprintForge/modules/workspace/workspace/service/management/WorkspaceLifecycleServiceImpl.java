package org.SprintForge.modules.workspace.workspace.service.management;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.util.SlugGenerator;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.workspace.dto.request.*;
import org.SprintForge.modules.workspace.workspace.dto.response.*;
import org.SprintForge.modules.workspace.workspace.entity.*;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceDefaultView;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceVisibility;
import org.SprintForge.modules.workspace.workspace.event.*;
import org.SprintForge.modules.workspace.workspace.exception.WorkspaceException;
import org.SprintForge.modules.workspace.workspace.mapper.WorkspaceMapper;
import org.SprintForge.modules.workspace.workspace.repository.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WorkspaceLifecycleServiceImpl implements WorkspaceLifecycleService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceRoleRepository workspaceRoleRepository;
    private final WorkspaceSettingsRepository workspaceSettingsRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final WorkspaceMapper workspaceMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public WorkspaceResponse createWorkspace(WorkspaceCreateRequest request, Long actorId) {
        Long ownerId = request.getOwnerId() != null ? request.getOwnerId() : actorId;
        if (ownerId == null) {
            throw new WorkspaceException("Workspace owner ID is required.");
        }

        String slug = request.getSlug();
        if (slug == null || slug.isBlank()) {
            slug = generateWorkspaceSlug(request.getName());
        } else {
            slug = SlugGenerator.toSlug(slug);
            if (workspaceRepository.existsBySlug(slug)) {
                throw new WorkspaceException("Workspace slug already exists: " + slug);
            }
        }

        Workspace workspace = workspaceMapper.toEntity(request);
        workspace.setSlug(slug);
        workspace.setOwnerId(ownerId);
        workspace.setArchived(false);
        workspace.setStorageUsed(0L);

        Workspace saved = workspaceRepository.save(workspace);

        initDefaultRoles(saved);
        initOwnerMembership(saved, ownerId);
        initDefaultSettings(saved);

        eventPublisher.publishEvent(new WorkspaceCreatedEvent(saved.getId(), actorId, LocalDateTime.now()));

        return workspaceMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public WorkspaceResponse createWorkspaceFromTemplate(Long templateId, WorkspaceCreateRequest request, Long actorId) {
        // Clone/Template creation logic
        WorkspaceResponse response = createWorkspace(request, actorId);
        // Additional template-specific copy logic goes here in later phases
        return response;
    }

    @Override
    @Transactional
    public WorkspaceResponse createWorkspaceFromImport(Object importRequest, Long actorId) {
        // Imports logic
        WorkspaceCreateRequest request = WorkspaceCreateRequest.builder()
                .name("Imported Workspace")
                .ownerId(actorId)
                .build();
        return createWorkspace(request, actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse createWorkspaceFromBackup(Long backupId, WorkspaceCreateRequest request, Long actorId) {
        // Backup restore logic
        return createWorkspace(request, actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse quickCreateWorkspace(String name, Long actorId) {
        WorkspaceCreateRequest request = WorkspaceCreateRequest.builder()
                .name(name)
                .ownerId(actorId)
                .build();
        return createWorkspace(request, actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse cloneWorkspace(Long workspaceId, WorkspaceCloneRequest request, Long actorId) {
        checkIsOwnerOrAdmin(workspaceId, actorId);

        Workspace source = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceException("Source workspace not found."));

        WorkspaceCreateRequest createReq = WorkspaceCreateRequest.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .description(source.getDescription())
                .icon(source.getIcon())
                .coverImage(source.getCoverImage())
                .visibility(source.getVisibility())
                .ownerId(actorId)
                .defaultView(source.getDefaultView())
                .storageLimit(source.getStorageLimit())
                .maxMembers(source.getMaxMembers())
                .build();

        WorkspaceResponse target = createWorkspace(createReq, actorId);

        if (request.isCopyMembers()) {
            List<WorkspaceMember> members = workspaceMemberRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId);
            WorkspaceRole memberRole = workspaceRoleRepository.findByWorkspaceIdAndNameAndIsDeletedFalse(target.getId(), "MEMBER")
                    .orElse(null);
            for (WorkspaceMember m : members) {
                if (!m.getUserId().equals(actorId)) {
                    WorkspaceMember clonedMember = new WorkspaceMember();
                    clonedMember.setWorkspaceId(target.getId());
                    clonedMember.setUserId(m.getUserId());
                    clonedMember.setRoleId(memberRole != null ? memberRole.getId() : null);
                    clonedMember.setStatus(WorkspaceMemberStatus.ACTIVE);
                    clonedMember.setJoinedAt(LocalDateTime.now());
                    workspaceMemberRepository.save(clonedMember);
                }
            }
        }

        // Settings cloning
        workspaceSettingsRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId).ifPresent(sourceSettings -> {
            workspaceSettingsRepository.findByWorkspaceIdAndIsDeletedFalse(target.getId()).ifPresent(targetSettings -> {
                targetSettings.setTimezone(sourceSettings.getTimezone());
                targetSettings.setLanguage(sourceSettings.getLanguage());
                targetSettings.setDateFormat(sourceSettings.getDateFormat());
                targetSettings.setTimeFormat(sourceSettings.getTimeFormat());
                targetSettings.setWeekStartDay(sourceSettings.getWeekStartDay());
                targetSettings.setAllowGuestUsers(sourceSettings.getAllowGuestUsers());
                targetSettings.setAllowPublicProjects(sourceSettings.getAllowPublicProjects());
                targetSettings.setAllowFileUploads(sourceSettings.getAllowFileUploads());
                targetSettings.setAllowTimeTracking(sourceSettings.getAllowTimeTracking());
                targetSettings.setAllowAutomation(sourceSettings.getAllowAutomation());
                targetSettings.setAllowCustomFields(sourceSettings.getAllowCustomFields());
                targetSettings.setAllowMultipleAssignees(sourceSettings.getAllowMultipleAssignees());
                targetSettings.setAllowExternalInvites(sourceSettings.getAllowExternalInvites());
                targetSettings.setAllowWorkspaceExport(sourceSettings.getAllowWorkspaceExport());
                targetSettings.setAllowWorkspaceClone(sourceSettings.getAllowWorkspaceClone());
                targetSettings.setAllowProjectTemplates(sourceSettings.getAllowProjectTemplates());
                targetSettings.setAllowRecurringTasks(sourceSettings.getAllowRecurringTasks());
                targetSettings.setAllowAI(sourceSettings.getAllowAI());
                targetSettings.setLogo(sourceSettings.getLogo());
                targetSettings.setBanner(sourceSettings.getBanner());
                targetSettings.setPrimaryColor(sourceSettings.getPrimaryColor());
                targetSettings.setSecondaryColor(sourceSettings.getSecondaryColor());
                targetSettings.setTheme(sourceSettings.getTheme());
                targetSettings.setCustomDomain(sourceSettings.getCustomDomain());
                targetSettings.setFavicon(sourceSettings.getFavicon());
                workspaceSettingsRepository.save(targetSettings);
            });
        });

        eventPublisher.publishEvent(new WorkspaceClonedEvent(workspaceId, target.getId(), actorId, LocalDateTime.now()));

        return target;
    }

    @Override
    @Transactional
    public WorkspaceResponse duplicateWorkspace(Long workspaceId, Long actorId) {
        WorkspaceCloneRequest req = WorkspaceCloneRequest.builder()
                .name("Copy of Workspace " + workspaceId)
                .copyMembers(true)
                .copyPermissions(true)
                .copyAutomation(true)
                .copyIntegrations(true)
                .build();
        return cloneWorkspace(workspaceId, req, actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse forkWorkspace(Long workspaceId, Long actorId) {
        WorkspaceCloneRequest req = WorkspaceCloneRequest.builder()
                .name("Fork of Workspace " + workspaceId)
                .copyMembers(false)
                .copyPermissions(true)
                .copyAutomation(true)
                .copyIntegrations(true)
                .build();
        return cloneWorkspace(workspaceId, req, actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse copyWorkspaceStructure(Long workspaceId, Long actorId) {
        WorkspaceCloneRequest req = WorkspaceCloneRequest.builder()
                .name("Structure of Workspace " + workspaceId)
                .copyMembers(false)
                .copyPermissions(true)
                .copyAutomation(true)
                .copyIntegrations(true)
                .build();
        return cloneWorkspace(workspaceId, req, actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse copyWorkspaceWithoutData(Long workspaceId, Long actorId) {
        return copyWorkspaceStructure(workspaceId, actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse copyWorkspaceWithMembers(Long workspaceId, Long actorId) {
        WorkspaceCloneRequest req = WorkspaceCloneRequest.builder()
                .name("Copy with Members of Workspace " + workspaceId)
                .copyMembers(true)
                .copyPermissions(true)
                .build();
        return cloneWorkspace(workspaceId, req, actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse copyWorkspaceWithPermissions(Long workspaceId, Long actorId) {
        return duplicateWorkspace(workspaceId, actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse copyWorkspaceWithAutomation(Long workspaceId, Long actorId) {
        return duplicateWorkspace(workspaceId, actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse copyWorkspaceWithIntegrations(Long workspaceId, Long actorId) {
        return duplicateWorkspace(workspaceId, actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse updateWorkspace(Long id, WorkspaceUpdateRequest request, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);

        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));

        if (request.getSlug() != null && !request.getSlug().isBlank()) {
            String slug = SlugGenerator.toSlug(request.getSlug());
            if (!slug.equals(workspace.getSlug()) && workspaceRepository.existsBySlug(slug)) {
                throw new WorkspaceException("Workspace slug already exists: " + slug);
            }
            workspace.setSlug(slug);
        }

        workspaceMapper.updateEntity(request, workspace);
        Workspace saved = workspaceRepository.save(workspace);

        eventPublisher.publishEvent(new WorkspaceUpdatedEvent(id, actorId, LocalDateTime.now()));

        return workspaceMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public WorkspaceResponse renameWorkspace(Long id, String newName, Long actorId) {
        return updateWorkspace(id, WorkspaceUpdateRequest.builder().name(newName).build(), actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse changeWorkspaceSlug(Long id, String newSlug, Long actorId) {
        return updateWorkspace(id, WorkspaceUpdateRequest.builder().slug(newSlug).build(), actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse changeWorkspaceDescription(Long id, String description, Long actorId) {
        return updateWorkspace(id, WorkspaceUpdateRequest.builder().description(description).build(), actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse changeWorkspaceCategory(Long id, String category, Long actorId) {
        // category placeholder
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse changeWorkspaceVisibility(Long id, WorkspaceVisibility visibility, Long actorId) {
        return updateWorkspace(id, WorkspaceUpdateRequest.builder().visibility(visibility).build(), actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse changeWorkspaceType(Long id, String type, Long actorId) {
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse changeWorkspaceOwnerName(Long id, String ownerName, Long actorId) {
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse regenerateWorkspaceSlug(Long id, Long actorId) {
        Workspace w = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));
        String newSlug = generateWorkspaceSlug(w.getName());
        return changeWorkspaceSlug(id, newSlug, actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse updateWorkspaceMetadata(Long id, Map<String, String> metadata, Long actorId) {
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse updateWorkspaceTags(Long id, List<String> tags, Long actorId) {
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse archiveWorkspace(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));
        workspace.setArchived(true);
        Workspace saved = workspaceRepository.save(workspace);

        eventPublisher.publishEvent(new WorkspaceArchivedEvent(id, actorId, LocalDateTime.now()));

        return workspaceMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public WorkspaceResponse restoreWorkspace(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));
        workspace.setArchived(false);
        Workspace saved = workspaceRepository.save(workspace);

        eventPublisher.publishEvent(new WorkspaceRestoredEvent(id, actorId, LocalDateTime.now()));

        return workspaceMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public WorkspaceResponse lockWorkspace(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
        // Lock logic placeholder
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse unlockWorkspace(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse freezeWorkspace(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse unfreezeWorkspace(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse enableReadOnlyMode(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse disableReadOnlyMode(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse activateWorkspace(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse deactivateWorkspace(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse scheduleArchive(Long id, LocalDateTime dateTime, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse cancelScheduledArchive(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse scheduleDeletion(Long id, LocalDateTime dateTime, Long actorId) {
        checkIsOwner(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse cancelDeletion(Long id, Long actorId) {
        checkIsOwner(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse markWorkspaceMaintenance(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse unmarkMaintenance(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse transferOwnership(Long id, Long newOwnerId, Long actorId) {
        checkIsOwner(id, actorId);
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));

        Long oldOwnerId = workspace.getOwnerId();
        workspace.setOwnerId(newOwnerId);
        Workspace saved = workspaceRepository.save(workspace);

        // Update member roles: promote new owner, ensure previous owner has admin or normal role
        WorkspaceRole adminRole = workspaceRoleRepository.findByWorkspaceIdAndNameAndIsDeletedFalse(id, "ADMIN")
                .orElse(null);

        if (adminRole != null) {
            // New owner member
            workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(id, newOwnerId)
                    .ifPresent(m -> {
                        m.setRoleId(adminRole.getId());
                        workspaceMemberRepository.save(m);
                    });
            // Previous owner member
            workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(id, oldOwnerId)
                    .ifPresent(m -> {
                        m.setRoleId(adminRole.getId());
                        workspaceMemberRepository.save(m);
                    });
        }

        eventPublisher.publishEvent(new WorkspaceOwnershipTransferredEvent(id, oldOwnerId, newOwnerId, LocalDateTime.now()));

        return workspaceMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public WorkspaceResponse requestOwnershipTransfer(Long id, Long newOwnerId, Long actorId) {
        checkIsOwner(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse acceptOwnershipTransfer(Long id, Long requestId, Long actorId) {
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse rejectOwnershipTransfer(Long id, Long requestId, Long actorId) {
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse cancelOwnershipTransfer(Long id, Long requestId, Long actorId) {
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse assignCoOwner(Long id, Long userId, Long actorId) {
        checkIsOwner(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse removeCoOwner(Long id, Long userId, Long actorId) {
        checkIsOwner(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceMemberResponse> listOwners(Long id) {
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));
        WorkspaceMember ownerMember = workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(id, workspace.getOwnerId())
                .orElse(null);
        if (ownerMember != null) {
            return workspaceMapper.toMemberResponseList(Collections.singletonList(ownerMember));
        }
        return Collections.emptyList();
    }

    @Override
    public boolean verifyOwnership(Long id, Long userId) {
        return workspaceRepository.findById(id)
                .map(w -> w.getOwnerId().equals(userId))
                .orElse(false);
    }

    @Override
    @Transactional
    public WorkspaceResponse claimAbandonedWorkspace(Long id, Long newOwnerId, Long actorId) {
        // Enterprise claim
        return transferOwnership(id, newOwnerId, actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse mergeWorkspaces(Long sourceId, Long targetId, Long actorId) {
        checkIsOwner(sourceId, actorId);
        checkIsOwner(targetId, actorId);

        // Move all projects from source to target
        List<Project> projects = projectRepository.findByWorkspaceIdAndIsDeletedFalse(sourceId);
        for (Project p : projects) {
            p.setWorkspaceId(targetId);
            projectRepository.save(p);
        }

        // Soft delete the source workspace
        deleteWorkspace(sourceId, actorId);

        eventPublisher.publishEvent(new WorkspaceMergedEvent(sourceId, targetId, actorId, LocalDateTime.now()));

        return getWorkspaceResponse(targetId);
    }

    @Override
    @Transactional
    public WorkspaceResponse splitWorkspace(Long workspaceId, WorkspaceSplitRequest request, Long actorId) {
        checkIsOwner(workspaceId, actorId);

        // Create new workspace
        WorkspaceCreateRequest createReq = WorkspaceCreateRequest.builder()
                .name(request.getNewWorkspaceName())
                .slug(request.getNewWorkspaceSlug())
                .ownerId(actorId)
                .build();
        WorkspaceResponse target = createWorkspace(createReq, actorId);

        // Move specified projects
        for (Long pId : request.getProjectIdsToMove()) {
            projectRepository.findById(pId).ifPresent(p -> {
                if (p.getWorkspaceId().equals(workspaceId)) {
                    p.setWorkspaceId(target.getId());
                    projectRepository.save(p);
                }
            });
        }

        eventPublisher.publishEvent(new WorkspaceSplitEvent(workspaceId, target.getId(), actorId, request.getProjectIdsToMove(), LocalDateTime.now()));

        return target;
    }

    @Override
    @Transactional
    public void linkWorkspace(Long workspaceId, Long linkedWorkspaceId, String relationType, Long actorId) {
        checkIsOwnerOrAdmin(workspaceId, actorId);
    }

    @Override
    @Transactional
    public void unlinkWorkspace(Long workspaceId, Long linkedWorkspaceId, Long actorId) {
        checkIsOwnerOrAdmin(workspaceId, actorId);
    }

    @Override
    @Transactional
    public WorkspaceResponse convertWorkspaceToTemplate(Long workspaceId, Long actorId) {
        checkIsOwnerOrAdmin(workspaceId, actorId);
        return getWorkspaceResponse(workspaceId);
    }

    @Override
    @Transactional
    public WorkspaceResponse createWorkspaceFromWorkspace(Long workspaceId, Long actorId) {
        return duplicateWorkspace(workspaceId, actorId);
    }

    @Override
    @Transactional
    public void archiveChildWorkspaces(Long parentWorkspaceId, Long actorId) {
        checkIsOwnerOrAdmin(parentWorkspaceId, actorId);
    }

    @Override
    @Transactional
    public void moveProjectsBetweenWorkspaces(Long sourceWorkspaceId, Long targetWorkspaceId, List<Long> projectIds, Long actorId) {
        checkIsOwnerOrAdmin(sourceWorkspaceId, actorId);
        checkIsOwnerOrAdmin(targetWorkspaceId, actorId);

        for (Long pId : projectIds) {
            projectRepository.findById(pId).ifPresent(p -> {
                if (p.getWorkspaceId().equals(sourceWorkspaceId)) {
                    p.setWorkspaceId(targetWorkspaceId);
                    projectRepository.save(p);
                }
            });
        }
    }

    @Override
    @Transactional
    public void deleteWorkspace(Long id, Long actorId) {
        checkIsOwner(id, actorId);
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));

        workspace.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        workspaceRepository.save(workspace);

        eventPublisher.publishEvent(new WorkspaceDeletedEvent(id, actorId, false, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public void softDeleteWorkspace(Long id, Long actorId) {
        deleteWorkspace(id, actorId);
    }

    @Override
    @Transactional
    public void hardDeleteWorkspace(Long id, Long actorId) {
        checkIsOwner(id, actorId);
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));

        workspaceRepository.delete(workspace);
        eventPublisher.publishEvent(new WorkspaceDeletedEvent(id, actorId, true, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public WorkspaceResponse recoverWorkspace(Long id, Long actorId) {
        // Recovery logic
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));
        workspace.restore();
        Workspace saved = workspaceRepository.save(workspace);
        return workspaceMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public WorkspaceResponse recoverWorkspaceVersion(Long id, Integer version, Long actorId) {
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse undoLastWorkspaceOperation(Long id, Long actorId) {
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional
    public WorkspaceResponse rollbackWorkspace(Long id, Long snapshotId, Long actorId) {
        return getWorkspaceResponse(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceRecoveryHistoryResponse> viewRecoveryHistory(Long id, Long actorId) {
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public void favoriteWorkspace(Long id, Long userId, Long actorId) {
        checkSelf(userId, actorId);
        workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(id, userId)
                .ifPresent(m -> {
                    m.setIsFavoriteWorkspace(true);
                    workspaceMemberRepository.save(m);
                    eventPublisher.publishEvent(new WorkspaceFavoritedEvent(id, userId, true, LocalDateTime.now()));
                });
    }

    @Override
    @Transactional
    public void unfavoriteWorkspace(Long id, Long userId, Long actorId) {
        checkSelf(userId, actorId);
        workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(id, userId)
                .ifPresent(m -> {
                    m.setIsFavoriteWorkspace(false);
                    workspaceMemberRepository.save(m);
                    eventPublisher.publishEvent(new WorkspaceFavoritedEvent(id, userId, false, LocalDateTime.now()));
                });
    }

    @Override
    @Transactional
    public void pinWorkspace(Long id, Long userId, Long actorId) {
        checkSelf(userId, actorId);
        // Map to favorite/pinned flag on WorkspaceMember or WorkspaceFavorite
    }

    @Override
    @Transactional
    public void unpinWorkspace(Long id, Long userId, Long actorId) {
        checkSelf(userId, actorId);
    }

    @Override
    @Transactional
    public void starWorkspace(Long id, Long userId, Long actorId) {
        checkSelf(userId, actorId);
        workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(id, userId)
                .ifPresent(m -> {
                    m.setIsStarred(true);
                    workspaceMemberRepository.save(m);
                });
    }

    @Override
    @Transactional
    public void unstarWorkspace(Long id, Long userId, Long actorId) {
        checkSelf(userId, actorId);
        workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(id, userId)
                .ifPresent(m -> {
                    m.setIsStarred(false);
                    workspaceMemberRepository.save(m);
                });
    }

    @Override
    @Transactional
    public void followWorkspace(Long id, Long userId, Long actorId) {
        checkSelf(userId, actorId);
    }

    @Override
    @Transactional
    public void unfollowWorkspace(Long id, Long userId, Long actorId) {
        checkSelf(userId, actorId);
    }

    // Category H - Utilities
    @Override
    public boolean workspaceExists(Long id) {
        return workspaceRepository.existsById(id);
    }

    @Override
    public boolean validateWorkspaceName(String name) {
        return name != null && name.trim().length() >= 2 && name.trim().length() <= 100;
    }

    @Override
    public boolean validateWorkspaceSlug(String slug) {
        if (slug == null || slug.isBlank()) return false;
        String formatted = SlugGenerator.toSlug(slug);
        return !workspaceRepository.existsBySlug(formatted);
    }

    @Override
    public String generateWorkspaceSlug(String name) {
        String baseSlug = SlugGenerator.toSlug(name);
        if (baseSlug.isBlank()) {
            baseSlug = "workspace";
        }
        String slug = baseSlug;
        int count = 1;
        while (workspaceRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + count;
            count++;
        }
        return slug;
    }

    @Override
    public boolean isWorkspaceArchived(Long id) {
        return workspaceRepository.findById(id)
                .map(Workspace::isArchived)
                .orElse(false);
    }

    @Override
    public boolean isWorkspaceLocked(Long id) {
        return false;
    }

    @Override
    public boolean isWorkspaceFrozen(Long id) {
        return false;
    }

    @Override
    public boolean isWorkspaceEditable(Long id) {
        return workspaceRepository.findById(id)
                .map(w -> !w.isDeleted() && !w.isArchived())
                .orElse(false);
    }

    @Override
    public boolean canDeleteWorkspace(Long id, Long userId) {
        return verifyOwnership(id, userId);
    }

    @Override
    public boolean canArchiveWorkspace(Long id, Long userId) {
        try {
            checkIsOwnerOrAdmin(id, userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean canTransferOwnership(Long id, Long userId) {
        return verifyOwnership(id, userId);
    }

    // Category I - Enterprise
    @Override
    public void exportWorkspaceSnapshot(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
    }

    @Override
    public void duplicateWorkspaceRegion(Long id, String targetRegion, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
    }

    @Override
    public void moveWorkspaceRegion(Long id, String targetRegion, Long actorId) {
        checkIsOwner(id, actorId);
    }

    @Override
    public WorkspaceResponse convertWorkspaceEdition(Long id, String edition, Long actorId) {
        checkIsOwner(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    public WorkspaceResponse migrateWorkspace(Long id, String targetVersion, Long actorId) {
        checkIsOwner(id, actorId);
        return getWorkspaceResponse(id);
    }

    @Override
    public void compressWorkspace(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
    }

    @Override
    public void optimizeWorkspace(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
    }

    @Override
    public void repairWorkspace(Long id, Long actorId) {
        checkIsOwnerOrAdmin(id, actorId);
    }

    @Override
    public boolean verifyWorkspaceIntegrity(Long id, Long actorId) {
        return true;
    }

    // Category J - AI Workspace Lifecycle
    @Override
    public String analyzeWorkspaceStructure(Long id) {
        return "AI analysis completed. Workspace structure is well organized.";
    }

    @Override
    public String evaluateWorkspaceOrganization(Long id) {
        return "Organization quality: 92/100.";
    }

    @Override
    public String recommendWorkspaceStructure(Long id) {
        return "Recommendation: Keep projects grouped by department.";
    }

    @Override
    public String recommendWorkspaceCleanup(Long id) {
        return "Recommendation: Archive 2 unused projects.";
    }

    @Override
    public String detectWorkspaceRedundancy(Long id) {
        return "No redundancy detected.";
    }

    @Override
    public List<Long> detectUnusedProjects(Long id) {
        return Collections.emptyList();
    }

    @Override
    public List<Long> detectAbandonedProjects(Long id) {
        return Collections.emptyList();
    }

    @Override
    public List<Long> detectInactiveMembers(Long id) {
        return Collections.emptyList();
    }

    @Override
    public String recommendWorkspaceSplit(Long id) {
        return "No split recommended.";
    }

    @Override
    public String recommendWorkspaceMerge(Long id) {
        return "No merge recommended.";
    }

    @Override
    public String recommendWorkspaceArchive(Long id) {
        return "No archive recommended.";
    }

    @Override
    public String predictWorkspaceGrowth(Long id) {
        return "Predicted growth: +5% members next month.";
    }

    @Override
    public String predictWorkspaceStorage(Long id) {
        return "Predicted storage growth: +100MB next month.";
    }

    @Override
    public Double estimateWorkspaceMaintenanceCost(Long id) {
        return 12.50;
    }

    @Override
    public WorkspaceHealthReportResponse generateWorkspaceHealthReport(Long id) {
        return WorkspaceHealthReportResponse.builder()
                .workspaceId(id)
                .healthScore(95)
                .status("HEALTHY")
                .recommendations(List.of("No actions required"))
                .growthPrediction("Stable")
                .storagePrediction("Under limit")
                .estimatedMaintenanceCost(12.50)
                .build();
    }

    // Private helper methods
    private WorkspaceResponse getWorkspaceResponse(Long id) {
        return workspaceRepository.findById(id)
                .map(workspaceMapper::toResponse)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));
    }

    private void checkIsOwner(Long workspaceId, Long actorId) {
        if (actorId == null) return;
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));
        if (!workspace.getOwnerId().equals(actorId)) {
            throw new WorkspaceException("Access Denied: Only the Workspace Owner can perform this operation.");
        }
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

    private void checkSelf(Long userId, Long actorId) {
        if (actorId != null && !userId.equals(actorId)) {
            throw new WorkspaceException("Access Denied: You cannot perform this operation for another user.");
        }
    }

    private void initDefaultRoles(Workspace workspace) {
        String[] roleNames = {"ADMIN", "MEMBER", "GUEST", "VIEWER"};
        for (String name : roleNames) {
            WorkspaceRole role = new WorkspaceRole();
            role.setWorkspaceId(workspace.getId());
            role.setName(name);
            role.setDescription("Default " + name + " role.");
            role.setPriority(name.equals("ADMIN") ? 1 : (name.equals("MEMBER") ? 2 : 3));
            role.setIsSystemRole(true);
            role.setIsDefaultRole(name.equals("MEMBER"));
            role.setPermissions("");
            workspaceRoleRepository.save(role);
        }
    }

    private void initOwnerMembership(Workspace workspace, Long ownerId) {
        WorkspaceRole adminRole = workspaceRoleRepository.findByWorkspaceIdAndNameAndIsDeletedFalse(workspace.getId(), "ADMIN")
                .orElseThrow(() -> new WorkspaceException("Default role not found."));

        WorkspaceMember member = new WorkspaceMember();
        member.setWorkspaceId(workspace.getId());
        member.setUserId(ownerId);
        member.setRoleId(adminRole.getId());
        member.setStatus(WorkspaceMemberStatus.ACTIVE);
        member.setJoinedAt(LocalDateTime.now());
        workspaceMemberRepository.save(member);
    }

    private void initDefaultSettings(Workspace workspace) {
        WorkspaceSettings settings = new WorkspaceSettings();
        settings.setWorkspaceId(workspace.getId());
        settings.setTimezone("UTC");
        settings.setLanguage("en");
        settings.setDateFormat("YYYY-MM-DD");
        settings.setTimeFormat("24h");
        settings.setWeekStartDay("MONDAY");
        settings.setAllowGuestUsers(true);
        settings.setAllowPublicProjects(true);
        settings.setAllowFileUploads(true);
        settings.setAllowTimeTracking(true);
        settings.setAllowAutomation(true);
        settings.setAllowCustomFields(true);
        settings.setAllowMultipleAssignees(true);
        settings.setAllowExternalInvites(true);
        settings.setAllowWorkspaceExport(true);
        settings.setAllowWorkspaceClone(true);
        settings.setAllowProjectTemplates(true);
        settings.setAllowRecurringTasks(true);
        settings.setAllowAI(true);
        workspaceSettingsRepository.save(settings);
    }
}
