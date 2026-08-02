package org.SprintForge.modules.workspace.project.service.member;

import org.SprintForge.modules.workspace.project.dto.request.AddProjectMemberRequest;
import org.SprintForge.modules.workspace.project.dto.request.UpdateProjectMemberRoleRequest;
import org.SprintForge.modules.workspace.project.dto.response.ProjectMemberResponse;

import java.util.List;

public interface ProjectMemberService {

    ProjectMemberResponse addMember(Long projectId, AddProjectMemberRequest request, Long actorId);

    void removeMember(Long projectId, Long memberId, Long actorId);

    void leaveProject(Long projectId, Long actorId);

    ProjectMemberResponse changeRole(Long projectId, Long memberId, UpdateProjectMemberRoleRequest request, Long actorId);

    ProjectMemberResponse activateMember(Long projectId, Long memberId, Long actorId);

    ProjectMemberResponse deactivateMember(Long projectId, Long memberId, Long actorId);

    List<ProjectMemberResponse> getMembers(Long projectId, Long actorId);

    List<ProjectMemberResponse> getAdmins(Long projectId, Long actorId);

    List<ProjectMemberResponse> searchMembers(Long projectId, String query, Long actorId);

    long countMembers(Long projectId, Long actorId);

    boolean isProjectMember(Long projectId, Long userId);

    boolean canAccessProject(Long projectId, Long userId);

    ProjectMemberResponse toggleFavorite(Long projectId, Long actorId);

    ProjectMemberResponse changeAllocation(Long projectId, Long memberId, Double allocationPercentage, Long actorId);
}
