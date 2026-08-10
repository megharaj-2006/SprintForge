package org.SprintForge.modules.workspace.workspace.team.service;

import java.util.Map;

public interface TeamStatisticsService {
    Map<String, Object> getTeamDashboard(Long workspaceId);
    long countMembers(Long teamId);
    Map<Long, Long> getMemberAllocation(Long teamId);
}
