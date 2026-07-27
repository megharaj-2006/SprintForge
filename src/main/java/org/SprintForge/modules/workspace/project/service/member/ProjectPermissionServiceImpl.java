package org.SprintForge.modules.workspace.project.service.member;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.entity.ProjectMember;
import org.SprintForge.modules.workspace.project.entity.ProjectRole;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectMemberStatus;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectVisibility;
import org.SprintForge.modules.workspace.project.repository.ProjectMemberRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRoleRepository;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.service.WorkspacePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectPermissionServiceImpl implements ProjectPermissionService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRoleRepository projectRoleRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspacePermissionService workspacePermissionService;

    @Override
    @Transactional(readOnly = true)
    public boolean hasPermission(Long projectId, Long userId, String permission) {
        if (projectId == null || userId == null || permission == null) {
            return false;
        }

        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null || project.isDeleted()) {
            return false;
        }

        // 1. Project Owner always has all permissions
        if (userId.equals(project.getOwnerId())) {
            return true;
        }

        // 2. Workspace Owner / Workspace Admin (PROJECT_MANAGE permission) always has all permissions
        if (workspacePermissionService.hasPermission(project.getWorkspaceId(), userId, WorkspacePermissionService.PROJECT_MANAGE)) {
            return true;
        }

        // Find workspace member
        WorkspaceMember workspaceMember = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(project.getWorkspaceId(), userId)
                .orElse(null);
        if (workspaceMember == null || workspaceMember.getStatus() != WorkspaceMemberStatus.ACTIVE) {
            return false;
        }

        // 3. Check Project membership
        ProjectMember projectMember = projectMemberRepository
                .findByProjectIdAndWorkspaceMemberIdAndIsDeletedFalse(projectId, workspaceMember.getId())
                .orElse(null);

        if (projectMember == null || projectMember.getStatus() != ProjectMemberStatus.ACTIVE) {
            return false;
        }

        if (projectMember.getRoleId() == null) {
            return false;
        }

        ProjectRole role = projectRoleRepository.findById(projectMember.getRoleId()).orElse(null);
        if (role == null || role.isDeleted()) {
            return false;
        }

        // Project ADMIN always has all permissions
        if ("ADMIN".equalsIgnoreCase(role.getName()) || "OWNER".equalsIgnoreCase(role.getName())) {
            return true;
        }

        String permissionsStr = role.getPermissions();
        if (permissionsStr == null || permissionsStr.isBlank()) {
            return false;
        }

        Set<String> permissions = Arrays.stream(permissionsStr.split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());

        return permissions.contains(permission.toUpperCase());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canManageMembers(Long projectId, Long userId) {
        return hasPermission(projectId, userId, PROJECT_MEMBER_MANAGE);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canCreateSprint(Long projectId, Long userId) {
        return hasPermission(projectId, userId, SPRINT_CREATE);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canDeleteProject(Long projectId, Long userId) {
        return hasPermission(projectId, userId, PROJECT_DELETE);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canArchiveProject(Long projectId, Long userId) {
        return hasPermission(projectId, userId, PROJECT_ARCHIVE);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canManageTasks(Long projectId, Long userId) {
        return hasPermission(projectId, userId, TASK_MANAGE);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canAssignTasks(Long projectId, Long userId) {
        return hasPermission(projectId, userId, TASK_ASSIGN);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canViewProject(Long projectId, Long userId) {
        // Anyone who can view project can do so if project is PUBLIC,
        // OR workspace member (for WORKSPACE visibility),
        // OR private project member
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null || project.isDeleted()) {
            return false;
        }

        if (project.getVisibility() == ProjectVisibility.PUBLIC) {
            return true;
        }

        if (userId == null) {
            return false;
        }

        // Check if user has explicit view permission
        return hasPermission(projectId, userId, PROJECT_VIEW);
    }
}
