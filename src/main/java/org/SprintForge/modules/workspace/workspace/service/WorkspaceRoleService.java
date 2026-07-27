package org.SprintForge.modules.workspace.workspace.service;

import org.SprintForge.modules.workspace.workspace.dto.request.AssignWorkspaceRoleRequest;
import org.SprintForge.modules.workspace.workspace.dto.request.UpdateWorkspaceRoleRequest;
import org.SprintForge.modules.workspace.workspace.dto.request.WorkspaceRoleCreateRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceMemberResponse;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceRoleResponse;

import java.util.List;

public interface WorkspaceRoleService {

    WorkspaceRoleResponse createRole(WorkspaceRoleCreateRequest request, Long actorId);

    WorkspaceRoleResponse updateRole(Long roleId, UpdateWorkspaceRoleRequest request, Long actorId);

    void deleteRole(Long roleId, Long actorId);

    WorkspaceMemberResponse assignRole(AssignWorkspaceRoleRequest request, Long actorId);

    WorkspaceMemberResponse removeRole(Long workspaceId, Long userId, Long actorId);

    WorkspaceRoleResponse duplicateRole(Long roleId, String newName, Long actorId);

    List<WorkspaceRoleResponse> reorderRoles(Long workspaceId, List<Long> roleIds, Long actorId);

    List<WorkspaceRoleResponse> getRoles(Long workspaceId);

    WorkspaceRoleResponse getRole(Long roleId);

    WorkspaceRoleResponse setDefaultRole(Long workspaceId, Long roleId, Long actorId);
}
