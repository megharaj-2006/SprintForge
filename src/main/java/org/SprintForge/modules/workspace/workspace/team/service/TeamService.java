package org.SprintForge.modules.workspace.workspace.team.service;

import org.SprintForge.modules.workspace.workspace.team.dto.request.CreateTeamRequest;
import org.SprintForge.modules.workspace.workspace.team.dto.request.UpdateTeamRequest;
import org.SprintForge.modules.workspace.workspace.team.dto.response.TeamResponse;
import org.SprintForge.modules.workspace.workspace.team.dto.response.TeamSummaryResponse;

import java.util.List;

public interface TeamService {
    TeamResponse createTeam(CreateTeamRequest request, Long actorId);
    TeamResponse updateTeam(Long teamId, UpdateTeamRequest request, Long actorId);
    void archiveTeam(Long teamId, Long actorId);
    void restoreTeam(Long teamId, Long actorId);
    TeamResponse assignLead(Long teamId, Long leadMemberId, Long actorId);
    TeamResponse getTeam(Long teamId);
    List<TeamResponse> listWorkspaceTeams(Long workspaceId);
    List<TeamResponse> listArchivedTeams(Long workspaceId);
    List<TeamSummaryResponse> searchTeams(Long workspaceId, String query);
}
