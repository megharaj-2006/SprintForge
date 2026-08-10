package org.SprintForge.modules.workspace.workspace.team.service;

import org.SprintForge.modules.workspace.workspace.team.dto.request.AssignTeamMemberRequest;
import org.SprintForge.modules.workspace.workspace.team.dto.request.TransferTeamMemberRequest;
import org.SprintForge.modules.workspace.workspace.team.dto.response.TeamResponse;

import java.util.List;

public interface TeamMembershipService {
    void addMember(Long teamId, AssignTeamMemberRequest request, Long actorId);
    void removeMember(Long teamId, Long memberId, Long actorId);
    void transferMember(Long teamId, TransferTeamMemberRequest request, Long actorId);
    List<TeamResponse> listMembersOfTeam(Long teamId);
}
