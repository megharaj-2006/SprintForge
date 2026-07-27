package org.SprintForge.modules.workspace.workspace.service.invitation;

import org.SprintForge.modules.workspace.workspace.dto.request.InviteMemberRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceInvitationResponse;

import java.util.List;

public interface WorkspaceInvitationService {

    WorkspaceInvitationResponse inviteMember(Long workspaceId, InviteMemberRequest request, Long actorId);

    WorkspaceInvitationResponse acceptInvitation(String token, Long userId);

    WorkspaceInvitationResponse rejectInvitation(String token, Long userId);

    void cancelInvitation(String token, Long actorId);

    WorkspaceInvitationResponse resendInvitation(String token, Long actorId);

    void expireInvitation(String token);

    WorkspaceInvitationResponse getInvitation(String token);

    List<WorkspaceInvitationResponse> getWorkspaceInvitations(Long workspaceId, Long actorId);

    List<WorkspaceInvitationResponse> getPendingInvitations(Long workspaceId, Long actorId);

    List<WorkspaceInvitationResponse> getUserInvitations(Long userId);
}
