package org.SprintForge.modules.workspace.workspace.team.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.team.dto.request.AssignTeamMemberRequest;
import org.SprintForge.modules.workspace.workspace.team.dto.request.CreateTeamRequest;
import org.SprintForge.modules.workspace.workspace.team.dto.request.TransferTeamMemberRequest;
import org.SprintForge.modules.workspace.workspace.team.dto.request.UpdateTeamRequest;
import org.SprintForge.modules.workspace.workspace.team.dto.response.TeamResponse;
import org.SprintForge.modules.workspace.workspace.team.dto.response.TeamSummaryResponse;
import org.SprintForge.modules.workspace.workspace.team.service.TeamMembershipService;
import org.SprintForge.modules.workspace.workspace.team.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Team Management", description = "Endpoints for managing teams")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TeamController {

    private final TeamService teamService;
    private final TeamMembershipService membershipService;

    @PostMapping("/workspaces/{workspaceId}/teams")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a team")
    public TeamResponse createTeam(@PathVariable Long workspaceId, 
                                   @Valid @RequestBody CreateTeamRequest request, 
                                   @RequestHeader("X-User-Id") Long actorId) {
        request.setWorkspaceId(workspaceId);
        return teamService.createTeam(request, actorId);
    }

    @GetMapping("/workspaces/{workspaceId}/teams")
    @Operation(summary = "List workspace teams")
    public List<TeamResponse> listWorkspaceTeams(@PathVariable Long workspaceId) {
        return teamService.listWorkspaceTeams(workspaceId);
    }

    @GetMapping("/teams/{id}")
    @Operation(summary = "Get team by ID")
    public TeamResponse getTeam(@PathVariable Long id) {
        return teamService.getTeam(id);
    }

    @PatchMapping("/teams/{id}")
    @Operation(summary = "Update team")
    public TeamResponse updateTeam(@PathVariable Long id, 
                                   @Valid @RequestBody UpdateTeamRequest request, 
                                   @RequestHeader("X-User-Id") Long actorId) {
        return teamService.updateTeam(id, request, actorId);
    }

    @PostMapping("/teams/{id}/archive")
    @Operation(summary = "Archive team")
    public void archiveTeam(@PathVariable Long id, @RequestHeader("X-User-Id") Long actorId) {
        teamService.archiveTeam(id, actorId);
    }

    @PostMapping("/teams/{id}/restore")
    @Operation(summary = "Restore team")
    public void restoreTeam(@PathVariable Long id, @RequestHeader("X-User-Id") Long actorId) {
        teamService.restoreTeam(id, actorId);
    }

    @PostMapping("/teams/{id}/members")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add member to team")
    public void addMember(@PathVariable Long id, 
                          @Valid @RequestBody AssignTeamMemberRequest request, 
                          @RequestHeader("X-User-Id") Long actorId) {
        membershipService.addMember(id, request, actorId);
    }

    @DeleteMapping("/teams/{id}/members/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove member from team")
    public void removeMember(@PathVariable Long id, 
                             @PathVariable Long memberId, 
                             @RequestHeader("X-User-Id") Long actorId) {
        membershipService.removeMember(id, memberId, actorId);
    }

    @PostMapping("/teams/{id}/members/transfer")
    @Operation(summary = "Transfer member to another team")
    public void transferMember(@PathVariable Long id, 
                               @Valid @RequestBody TransferTeamMemberRequest request, 
                               @RequestHeader("X-User-Id") Long actorId) {
        membershipService.transferMember(id, request, actorId);
    }

    @GetMapping("/workspaces/{workspaceId}/teams/search")
    @Operation(summary = "Search teams")
    public List<TeamSummaryResponse> searchTeams(@PathVariable Long workspaceId, 
                                                 @RequestParam("q") String query) {
        return teamService.searchTeams(workspaceId, query);
    }
}
