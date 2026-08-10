package org.SprintForge.modules.workspace.workspace.team.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.workspace.team.dto.request.CreateTeamRequest;
import org.SprintForge.modules.workspace.workspace.team.dto.request.UpdateTeamRequest;
import org.SprintForge.modules.workspace.workspace.team.dto.response.TeamResponse;
import org.SprintForge.modules.workspace.workspace.team.dto.response.TeamSummaryResponse;
import org.SprintForge.modules.workspace.workspace.team.entity.Team;
import org.SprintForge.modules.workspace.workspace.team.event.TeamArchivedEvent;
import org.SprintForge.modules.workspace.workspace.team.event.TeamCreatedEvent;
import org.SprintForge.modules.workspace.workspace.team.mapper.TeamMapper;
import org.SprintForge.modules.workspace.workspace.team.repository.TeamMemberRepository;
import org.SprintForge.modules.workspace.workspace.team.repository.TeamRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamMapper teamMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public TeamResponse createTeam(CreateTeamRequest request, Long actorId) {
        if (teamRepository.existsByWorkspaceIdAndNameAndIsDeletedFalse(request.getWorkspaceId(), request.getName())) {
            throw new BusinessRuleException("Team name already exists in this workspace: " + request.getName());
        }

        Team team = teamMapper.toEntity(request);
        team.setCreatedById(actorId);
        team = teamRepository.save(team);

        eventPublisher.publishEvent(new TeamCreatedEvent(team.getId(), team.getWorkspaceId(), actorId, LocalDateTime.now()));

        return mapToResponse(team);
    }

    @Override
    @Transactional
    public TeamResponse updateTeam(Long teamId, UpdateTeamRequest request, Long actorId) {
        Team team = getActiveTeam(teamId);
        
        if (team.isArchived()) {
            throw new BusinessRuleException("Cannot modify an archived team.");
        }

        if (request.getName() != null && !request.getName().equals(team.getName())) {
            if (teamRepository.existsByWorkspaceIdAndNameAndIsDeletedFalse(team.getWorkspaceId(), request.getName())) {
                throw new BusinessRuleException("Team name already exists in this workspace: " + request.getName());
            }
        }

        teamMapper.updateEntity(request, team);
        team = teamRepository.save(team);

        return mapToResponse(team);
    }

    @Override
    @Transactional
    public void archiveTeam(Long teamId, Long actorId) {
        Team team = getActiveTeam(teamId);
        team.setArchived(true);
        teamRepository.save(team);

        eventPublisher.publishEvent(new TeamArchivedEvent(team.getId(), team.getWorkspaceId(), actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public void restoreTeam(Long teamId, Long actorId) {
        Team team = teamRepository.findById(teamId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found: " + teamId));
                
        if (!team.isArchived()) {
            throw new BusinessRuleException("Team is not archived.");
        }
        
        team.setArchived(false);
        teamRepository.save(team);
    }

    @Override
    @Transactional
    public TeamResponse assignLead(Long teamId, Long leadMemberId, Long actorId) {
        Team team = getActiveTeam(teamId);
        if (team.isArchived()) {
            throw new BusinessRuleException("Cannot modify an archived team.");
        }
        
        team.setLeadMemberId(leadMemberId);
        team = teamRepository.save(team);
        
        return mapToResponse(team);
    }

    @Override
    @Transactional(readOnly = true)
    public TeamResponse getTeam(Long teamId) {
        return mapToResponse(getActiveTeam(teamId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamResponse> listWorkspaceTeams(Long workspaceId) {
        return teamRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId).stream()
                .filter(t -> !t.isArchived())
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamResponse> listArchivedTeams(Long workspaceId) {
        return teamRepository.findByWorkspaceIdAndIsArchivedAndIsDeletedFalse(workspaceId, true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamSummaryResponse> searchTeams(Long workspaceId, String query) {
        return teamRepository.searchByName(workspaceId, query).stream()
                .filter(t -> !t.isArchived())
                .map(team -> {
                    TeamSummaryResponse response = teamMapper.toSummaryResponse(team);
                    response.setMemberCount(teamMemberRepository.countByTeamIdAndIsDeletedFalse(team.getId()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    private Team getActiveTeam(Long teamId) {
        return teamRepository.findById(teamId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found: " + teamId));
    }

    private TeamResponse mapToResponse(Team team) {
        TeamResponse response = teamMapper.toResponse(team);
        response.setMemberCount(teamMemberRepository.countByTeamIdAndIsDeletedFalse(team.getId()));
        return response;
    }
}
