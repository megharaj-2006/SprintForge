package org.SprintForge.modules.workspace.workspace.department.service;

import java.util.Map;

public interface DepartmentStatisticsService {
    Map<String, Object> getDepartmentStats(Long workspaceId);
    long getMemberCount(Long deptId);
    long getTeamCount(Long deptId);
}
