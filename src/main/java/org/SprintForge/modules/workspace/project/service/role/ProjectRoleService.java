package org.SprintForge.modules.workspace.project.service.role;

import org.SprintForge.modules.workspace.project.dto.request.CreateProjectRoleRequest;
import org.SprintForge.modules.workspace.project.dto.response.ProjectRoleResponse;

import java.util.List;

public interface ProjectRoleService {
    ProjectRoleResponse createRole(Long projectId, CreateProjectRoleRequest request, Long actorId);
    List<ProjectRoleResponse> getRoles(Long projectId);
    ProjectRoleResponse getRole(Long roleId);
    void deleteRole(Long roleId, Long actorId);
    ProjectRoleResponse assignRole(Long projectId, Long memberId, Long roleId, Long actorId);
    ProjectRoleResponse cloneRole(Long roleId, String newRoleName, Long actorId);
    ProjectRoleResponse duplicatePermissions(Long sourceRoleId, Long targetRoleId, Long actorId);
}
