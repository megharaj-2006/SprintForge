package org.SprintForge.modules.workspace.workspace.team.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.team.repository.TeamMemberRepository;
import org.SprintForge.modules.workspace.workspace.team.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TeamStatisticsServiceImpl implements TeamStatisticsService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getTeamDashboard(Long workspaceId) {
        Map<String, Object> stats = new HashMap<>();
        long totalTeams = teamRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId).stream()
                .filter(t -> !t.isArchived())
                .count();
        long archivedTeams = teamRepository.findByWorkspaceIdAndIsArchivedAndIsDeletedFalse(workspaceId, true).size();
        
        stats.put("totalTeams", totalTeams);
        stats.put("archivedTeams", archivedTeams);
        // Note: other stats to be added here based on precise requirements
        return stats;
    }

    @Override
    @Transactional(readOnly = true)
    public long countMembers(Long teamId) {
        return teamMemberRepository.countByTeamIdAndIsDeletedFalse(teamId);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, Long> getMemberAllocation(Long teamId) {
        Map<Long, Long> allocations = new HashMap<>();
        teamMemberRepository.findByTeamIdAndIsDeletedFalse(teamId).forEach(member -> {
            if (member.getAllocationPercentage() != null) {
                allocations.put(member.getMemberId(), member.getAllocationPercentage().longValue());
            }
        });
        return allocations;
    }
}
