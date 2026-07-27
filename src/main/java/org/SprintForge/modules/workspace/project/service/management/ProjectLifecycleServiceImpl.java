package org.SprintForge.modules.workspace.project.service.management;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.dto.request.ProjectCreateRequest;
import org.SprintForge.modules.workspace.project.dto.request.ProjectUpdateRequest;
import org.SprintForge.modules.workspace.project.dto.response.ProjectResponse;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.entity.ProjectSettings;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectStatusType;
import org.SprintForge.modules.workspace.project.event.*;
import org.SprintForge.modules.workspace.project.mapper.ProjectMapper;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectSettingsRepository;
import org.SprintForge.modules.workspace.workspace.entity.Workspace;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceSubscription;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRepository;
import org.SprintForge.modules.workspace.project.entity.ProjectMember;
import org.SprintForge.modules.workspace.project.entity.ProjectRole;
import org.SprintForge.modules.workspace.project.repository.ProjectMemberRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRoleRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceSubscriptionRepository;
import org.SprintForge.modules.workspace.workspace.service.WorkspacePermissionService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProjectLifecycleServiceImpl implements ProjectLifecycleService {

    private final ProjectRepository projectRepository;
    private final ProjectSettingsRepository projectSettingsRepository;
    private final ProjectRoleRepository projectRoleRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceSubscriptionRepository workspaceSubscriptionRepository;
    private final WorkspacePermissionService workspacePermissionService;
    private final ProjectMapper projectMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public ProjectResponse createProject(Long workspaceId, ProjectCreateRequest request, Long actorId) {
        // 1. Workspace exists
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .filter(w -> !w.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found with ID: " + workspaceId));

        // 2. Workspace active
        if (workspace.isArchived()) {
            throw new BusinessRuleException("Cannot create project in an archived workspace.");
        }

        // 3. User belongs to workspace
        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, actorId)
                .orElseThrow(() -> new ForbiddenException("User is not a member of this workspace."));

        if (member.getStatus() != WorkspaceMemberStatus.ACTIVE) {
            throw new ForbiddenException("User membership in workspace is not active.");
        }

        // 4. User has CREATE_PROJECT permission
        if (!workspacePermissionService.canCreateProjects(workspaceId, actorId)) {
            throw new ForbiddenException("User does not have permission to create projects in this workspace.");
        }

        // 5. Project name unique
        if (projectRepository.existsByWorkspaceIdAndNameAndIsDeletedFalse(workspaceId, request.getName())) {
            throw new ConflictException("Project name already exists in this workspace.");
        }

        // 6. Project key unique
        if (projectRepository.existsByWorkspaceIdAndProjectKeyAndIsDeletedFalse(workspaceId, request.getProjectKey())) {
            throw new ConflictException("Project key already exists in this workspace.");
        }

        // 7. Workspace project limit
        workspaceSubscriptionRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId).ifPresent(subscription -> {
            if (subscription.getMaxProjects() != null) {
                long currentCount = projectRepository.countByWorkspaceIdAndIsDeletedFalse(workspaceId);
                if (currentCount >= subscription.getMaxProjects()) {
                    throw new BusinessRuleException("Cannot exceed workspace project limit of " + subscription.getMaxProjects());
                }
            }
        });

        // 8. Create project
        Project project = projectMapper.toEntity(request);
        project.setWorkspaceId(workspaceId);
        project.setOwnerId(request.getOwnerId() != null ? request.getOwnerId() : actorId);
        project.setIsArchived(false);
        if (project.getStatus() == null) {
            project.setStatus(ProjectStatusType.PLANNING);
        }

        Project saved = projectRepository.save(project);

        // Initialize default project settings
        initDefaultSettings(saved);

        // Initialize default project roles and add owner as member
        initDefaultRolesAndOwner(saved);

        // 9. Publish event
        eventPublisher.publishEvent(new ProjectCreatedEvent(saved.getId(), workspaceId, actorId, LocalDateTime.now()));

        return projectMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(Long projectId, ProjectUpdateRequest request, Long actorId) {
        // 1. Project exists
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        // 2. Project not archived
        if (Boolean.TRUE.equals(project.getIsArchived())) {
            throw new BusinessRuleException("Cannot update an archived project.");
        }

        // 3. Permission (project owner OR PROJECT_MANAGE permission in workspace)
        boolean isOwner = actorId.equals(project.getOwnerId());
        boolean canManage = workspacePermissionService.canManageProjects(project.getWorkspaceId(), actorId);
        if (!isOwner && !canManage) {
            throw new ForbiddenException("Access Denied: You do not have permission to manage this project.");
        }

        // Validate name unique if name changed
        if (request.getName() != null && !request.getName().equals(project.getName())) {
            if (projectRepository.existsByWorkspaceIdAndNameAndIsDeletedFalse(project.getWorkspaceId(), request.getName())) {
                throw new ConflictException("Project name already exists in this workspace.");
            }
        }

        // 4. Update
        Long prevOwnerId = project.getOwnerId();
        projectMapper.updateEntity(request, project);

        // If ownerId changed, check permission and trigger ownership event
        if (request.getOwnerId() != null && !request.getOwnerId().equals(prevOwnerId)) {
            // Verify new owner belongs to workspace
            WorkspaceMember member = workspaceMemberRepository
                    .findByWorkspaceIdAndUserIdAndIsDeletedFalse(project.getWorkspaceId(), request.getOwnerId())
                    .orElseThrow(() -> new BusinessRuleException("New owner must be a member of the workspace."));
            if (member.getStatus() != WorkspaceMemberStatus.ACTIVE) {
                throw new BusinessRuleException("New owner membership is not active.");
            }
            project.setOwnerId(request.getOwnerId());
            eventPublisher.publishEvent(new ProjectOwnershipTransferredEvent(projectId, prevOwnerId, request.getOwnerId(), actorId, LocalDateTime.now()));
        }

        Project saved = projectRepository.save(project);

        // 5. Publish event
        eventPublisher.publishEvent(new ProjectUpdatedEvent(projectId, actorId, LocalDateTime.now()));

        return projectMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProjectResponse archiveProject(Long projectId, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        boolean isOwner = actorId.equals(project.getOwnerId());
        boolean canManage = workspacePermissionService.canManageProjects(project.getWorkspaceId(), actorId);
        if (!isOwner && !canManage) {
            throw new ForbiddenException("Access Denied: You do not have permission to archive this project.");
        }

        project.setIsArchived(true);
        project.setStatus(ProjectStatusType.ARCHIVED);
        Project saved = projectRepository.save(project);

        eventPublisher.publishEvent(new ProjectArchivedEvent(projectId, actorId, LocalDateTime.now()));

        return projectMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProjectResponse restoreProject(Long projectId, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        boolean isOwner = actorId.equals(project.getOwnerId());
        boolean canManage = workspacePermissionService.canManageProjects(project.getWorkspaceId(), actorId);
        if (!isOwner && !canManage) {
            throw new ForbiddenException("Access Denied: You do not have permission to restore this project.");
        }

        project.setIsArchived(false);
        project.setStatus(ProjectStatusType.ACTIVE);
        Project saved = projectRepository.save(project);

        eventPublisher.publishEvent(new ProjectRestoredEvent(projectId, actorId, LocalDateTime.now()));

        return projectMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        boolean isOwner = actorId.equals(project.getOwnerId());
        boolean canManage = workspacePermissionService.canManageProjects(project.getWorkspaceId(), actorId);
        if (!isOwner && !canManage) {
            throw new ForbiddenException("Access Denied: You do not have permission to delete this project.");
        }

        project.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        projectRepository.save(project);

        // Soft delete settings as well
        projectSettingsRepository.findByProjectIdAndIsDeletedFalse(projectId).ifPresent(settings -> {
            settings.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
            projectSettingsRepository.save(settings);
        });

        eventPublisher.publishEvent(new ProjectDeletedEvent(projectId, actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public ProjectResponse duplicateProject(Long projectId, Long actorId) {
        Project source = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        boolean isOwner = actorId.equals(source.getOwnerId());
        boolean canManage = workspacePermissionService.canManageProjects(source.getWorkspaceId(), actorId);
        if (!isOwner && !canManage) {
            throw new ForbiddenException("Access Denied: You do not have permission to duplicate this project.");
        }

        // Workspace project limit check for duplicating target
        workspaceSubscriptionRepository.findByWorkspaceIdAndIsDeletedFalse(source.getWorkspaceId()).ifPresent(subscription -> {
            if (subscription.getMaxProjects() != null) {
                long currentCount = projectRepository.countByWorkspaceIdAndIsDeletedFalse(source.getWorkspaceId());
                if (currentCount >= subscription.getMaxProjects()) {
                    throw new BusinessRuleException("Cannot duplicate project: workspace project limit of " + subscription.getMaxProjects() + " exceeded.");
                }
            }
        });

        // Generate unique name
        String baseName = "Copy of " + source.getName();
        String name = baseName;
        int nameIndex = 1;
        while (projectRepository.existsByWorkspaceIdAndNameAndIsDeletedFalse(source.getWorkspaceId(), name)) {
            name = baseName + " (" + nameIndex + ")";
            nameIndex++;
        }

        // Generate unique key (up to 10 characters)
        String baseKey = source.getProjectKey();
        String key = baseKey + "C";
        int keyIndex = 1;
        while (projectRepository.existsByWorkspaceIdAndProjectKeyAndIsDeletedFalse(source.getWorkspaceId(), key)) {
            key = baseKey + "C" + keyIndex;
            keyIndex++;
        }
        if (key.length() > 10) {
            key = key.substring(0, 10);
            while (projectRepository.existsByWorkspaceIdAndProjectKeyAndIsDeletedFalse(source.getWorkspaceId(), key)) {
                key = key.substring(0, key.length() - String.valueOf(keyIndex).length()) + keyIndex;
                keyIndex++;
            }
        }

        Project duplicated = new Project();
        duplicated.setWorkspaceId(source.getWorkspaceId());
        duplicated.setName(name);
        duplicated.setProjectKey(key);
        duplicated.setDescription(source.getDescription());
        duplicated.setIcon(source.getIcon());
        duplicated.setCoverImage(source.getCoverImage());
        duplicated.setColor(source.getColor());
        duplicated.setVisibility(source.getVisibility());
        duplicated.setStatus(source.getStatus());
        duplicated.setOwnerId(actorId); // New duplicated project owned by the duplicating actor
        duplicated.setStartDate(source.getStartDate());
        duplicated.setTargetEndDate(source.getTargetEndDate());
        duplicated.setBudget(source.getBudget());
        duplicated.setCurrency(source.getCurrency());
        duplicated.setEstimatedHours(source.getEstimatedHours());
        duplicated.setIsTemplate(source.getIsTemplate());
        duplicated.setIsArchived(false);

        Project saved = projectRepository.save(duplicated);

        // Copy settings
        projectSettingsRepository.findByProjectIdAndIsDeletedFalse(projectId).ifPresentOrElse(sourceSettings -> {
            ProjectSettings duplicatedSettings = new ProjectSettings();
            duplicatedSettings.setProjectId(saved.getId());
            duplicatedSettings.setAllowMultipleAssignees(sourceSettings.getAllowMultipleAssignees());
            duplicatedSettings.setAllowTimeTracking(sourceSettings.getAllowTimeTracking());
            duplicatedSettings.setAllowStoryPoints(sourceSettings.getAllowStoryPoints());
            duplicatedSettings.setAllowCustomFields(sourceSettings.getAllowCustomFields());
            duplicatedSettings.setAllowRecurringTasks(sourceSettings.getAllowRecurringTasks());
            duplicatedSettings.setAllowAutomation(sourceSettings.getAllowAutomation());
            duplicatedSettings.setAllowGuestAccess(sourceSettings.getAllowGuestAccess());
            duplicatedSettings.setDefaultIssueType(sourceSettings.getDefaultIssueType());
            duplicatedSettings.setDefaultTaskStatus(sourceSettings.getDefaultTaskStatus());
            duplicatedSettings.setDefaultPriority(sourceSettings.getDefaultPriority());
            duplicatedSettings.setDefaultView(sourceSettings.getDefaultView());
            projectSettingsRepository.save(duplicatedSettings);
        }, () -> initDefaultSettings(saved));

        // Initialize roles and owner for duplicated project
        initDefaultRolesAndOwner(saved);

        eventPublisher.publishEvent(new ProjectDuplicatedEvent(projectId, saved.getId(), actorId, LocalDateTime.now()));

        return projectMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProjectResponse transferOwnership(Long projectId, Long newOwnerId, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        boolean isOwner = actorId.equals(project.getOwnerId());
        boolean canManage = workspacePermissionService.canManageProjects(project.getWorkspaceId(), actorId);
        if (!isOwner && !canManage) {
            throw new ForbiddenException("Access Denied: You do not have permission to transfer ownership of this project.");
        }

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(project.getWorkspaceId(), newOwnerId)
                .orElseThrow(() -> new BusinessRuleException("New owner must be an active member of the workspace."));

        if (member.getStatus() != WorkspaceMemberStatus.ACTIVE) {
            throw new BusinessRuleException("New owner membership is not active.");
        }

        Long prevOwnerId = project.getOwnerId();
        project.setOwnerId(newOwnerId);
        Project saved = projectRepository.save(project);

        eventPublisher.publishEvent(new ProjectOwnershipTransferredEvent(projectId, prevOwnerId, newOwnerId, actorId, LocalDateTime.now()));

        return projectMapper.toResponse(saved);
    }

    private void initDefaultSettings(Project project) {
        ProjectSettings settings = new ProjectSettings();
        settings.setProjectId(project.getId());
        settings.setAllowMultipleAssignees(true);
        settings.setAllowTimeTracking(true);
        settings.setAllowStoryPoints(true);
        settings.setAllowCustomFields(true);
        settings.setAllowRecurringTasks(true);
        settings.setAllowAutomation(true);
        settings.setAllowGuestAccess(true);
        settings.setDefaultIssueType("TASK");
        settings.setDefaultTaskStatus("TODO");
        settings.setDefaultPriority("MEDIUM");
        settings.setDefaultView("LIST");
        projectSettingsRepository.save(settings);
    }

    private void initDefaultRolesAndOwner(Project project) {
        ProjectRole ownerRole = createProjectRole(project.getId(), "OWNER", "PROJECT_MEMBER_MANAGE,SPRINT_CREATE,PROJECT_DELETE,PROJECT_ARCHIVE,TASK_MANAGE,TASK_ASSIGN,PROJECT_VIEW", "#FF4D4D");
        createProjectRole(project.getId(), "ADMIN", "PROJECT_MEMBER_MANAGE,SPRINT_CREATE,TASK_MANAGE,TASK_ASSIGN,PROJECT_VIEW", "#FF9900");
        createProjectRole(project.getId(), "MEMBER", "SPRINT_CREATE,TASK_MANAGE,TASK_ASSIGN,PROJECT_VIEW", "#33CC33");
        createProjectRole(project.getId(), "VIEWER", "PROJECT_VIEW", "#999999");

        workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(project.getWorkspaceId(), project.getOwnerId())
                .ifPresent(wsMember -> {
                    ProjectMember member = new ProjectMember();
                    member.setProjectId(project.getId());
                    member.setWorkspaceMemberId(wsMember.getId());
                    member.setRoleId(ownerRole.getId());
                    member.setJoinedAt(LocalDateTime.now());
                    member.setAddedBy(project.getOwnerId());
                    member.setStatus(org.SprintForge.modules.workspace.project.entity.enums.ProjectMemberStatus.ACTIVE);
                    member.setFavorite(false);
                    member.setNotificationsEnabled(true);
                    projectMemberRepository.save(member);
                });
    }

    private ProjectRole createProjectRole(Long projectId, String name, String permissions, String color) {
        ProjectRole role = new ProjectRole();
        role.setProjectId(projectId);
        role.setName(name);
        role.setPermissions(permissions);
        role.setColor(color);
        role.setDescription("Default " + name + " role for project " + projectId);
        return projectRoleRepository.save(role);
    }
}
