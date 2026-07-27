package org.SprintForge.modules.workspace.workspace.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.entity.Workspace;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceRole;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspacePermissionServiceImpl implements WorkspacePermissionService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceRoleRepository workspaceRoleRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean hasPermission(Long workspaceId, Long userId, String permission) {
        if (workspaceId == null || userId == null || permission == null) {
            return false;
        }

        Workspace workspace = workspaceRepository.findById(workspaceId).orElse(null);
        if (workspace == null || workspace.isDeleted()) {
            return false;
        }

        // Owner always has all permissions
        if (userId.equals(workspace.getOwnerId())) {
            return true;
        }

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, userId)
                .orElse(null);

        if (member == null || member.getStatus() != WorkspaceMemberStatus.ACTIVE) {
            return false;
        }

        if (member.getRoleId() == null) {
            return false;
        }

        WorkspaceRole role = workspaceRoleRepository.findById(member.getRoleId()).orElse(null);
        if (role == null || role.isDeleted()) {
            return false;
        }

        // ADMIN role has all permissions
        if ("ADMIN".equalsIgnoreCase(role.getName())) {
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
    public boolean canManageWorkspace(Long workspaceId, Long userId) {
        return hasPermission(workspaceId, userId, WORKSPACE_MANAGE);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canManageProjects(Long workspaceId, Long userId) {
        return hasPermission(workspaceId, userId, PROJECT_MANAGE);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canDeleteTasks(Long workspaceId, Long userId) {
        return hasPermission(workspaceId, userId, TASK_DELETE);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canInviteMembers(Long workspaceId, Long userId) {
        return hasPermission(workspaceId, userId, MEMBER_INVITE);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canAssignRoles(Long workspaceId, Long userId) {
        return hasPermission(workspaceId, userId, ROLE_ASSIGN);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canCreateProjects(Long workspaceId, Long userId) {
        return hasPermission(workspaceId, userId, PROJECT_CREATE);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canArchiveWorkspace(Long workspaceId, Long userId) {
        return hasPermission(workspaceId, userId, WORKSPACE_ARCHIVE);
    }
}
