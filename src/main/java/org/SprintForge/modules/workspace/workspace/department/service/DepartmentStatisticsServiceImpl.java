package org.SprintForge.modules.workspace.workspace.department.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.department.repository.DepartmentRepository;
import org.SprintForge.modules.workspace.workspace.team.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DepartmentStatisticsServiceImpl implements DepartmentStatisticsService {

    private final DepartmentRepository departmentRepository;
    private final TeamRepository teamRepository;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getDepartmentStats(Long workspaceId) {
        Map<String, Object> stats = new HashMap<>();
        long totalDepartments = departmentRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId).stream()
                .filter(d -> !d.isArchived())
                .count();
        long archivedDepartments = departmentRepository.findByWorkspaceIdAndIsArchivedAndIsDeletedFalse(workspaceId, true).size();
        
        stats.put("totalDepartments", totalDepartments);
        stats.put("archivedDepartments", archivedDepartments);
        return stats;
    }

    @Override
    @Transactional(readOnly = true)
    public long getMemberCount(Long deptId) {
        // Requires more complex join or query via teams. Returning 0 for now as per instructions.
        return 0L;
    }

    @Override
    @Transactional(readOnly = true)
    public long getTeamCount(Long deptId) {
        return teamRepository.countByDepartmentIdAndIsDeletedFalse(deptId);
    }
}
