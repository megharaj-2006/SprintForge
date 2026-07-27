package org.SprintForge.modules.workspace.workspace.service;

public interface WorkspacePermissionService {

    String WORKSPACE_MANAGE = "WORKSPACE_MANAGE";
    String PROJECT_MANAGE = "PROJECT_MANAGE";
    String TASK_DELETE = "TASK_DELETE";
    String MEMBER_INVITE = "MEMBER_INVITE";
    String ROLE_ASSIGN = "ROLE_ASSIGN";
    String PROJECT_CREATE = "PROJECT_CREATE";
    String WORKSPACE_ARCHIVE = "WORKSPACE_ARCHIVE";

    boolean hasPermission(Long workspaceId, Long userId, String permission);

    boolean canManageWorkspace(Long workspaceId, Long userId);

    boolean canManageProjects(Long workspaceId, Long userId);

    boolean canDeleteTasks(Long workspaceId, Long userId);

    boolean canInviteMembers(Long workspaceId, Long userId);

    boolean canAssignRoles(Long workspaceId, Long userId);

    boolean canCreateProjects(Long workspaceId, Long userId);

    boolean canArchiveWorkspace(Long workspaceId, Long userId);
}
