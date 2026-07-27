package org.SprintForge.modules.workspace.workspace.service;

import org.SprintForge.modules.workspace.workspace.dto.request.AddWorkspaceMemberRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceMemberResponse;
import org.SprintForge.modules.workspace.workspace.dto.response.MemberSearchResponse;

import java.util.List;

public interface WorkspaceMemberService {

    WorkspaceMemberResponse addMember(AddWorkspaceMemberRequest request, Long actorId);

    void removeMember(Long workspaceId, Long userId, Long actorId);

    void leaveWorkspace(Long workspaceId, Long userId, Long actorId);

    WorkspaceMemberResponse suspendMember(Long workspaceId, Long userId, Long actorId);

    WorkspaceMemberResponse reactivateMember(Long workspaceId, Long userId, Long actorId);

    WorkspaceMemberResponse changeMemberRole(Long workspaceId, Long userId, Long roleId, Long actorId);

    List<WorkspaceMemberResponse> getMembers(Long workspaceId);

    List<WorkspaceMemberResponse> getActiveMembers(Long workspaceId);

    List<WorkspaceMemberResponse> getPendingMembers(Long workspaceId);

    List<WorkspaceMemberResponse> getWorkspaceAdmins(Long workspaceId);

    WorkspaceMemberResponse getWorkspaceOwner(Long workspaceId);

    boolean isWorkspaceMember(Long workspaceId, Long userId);

    long countMembers(Long workspaceId);

    MemberSearchResponse searchMembers(Long workspaceId, String query, int page, int size);
}
