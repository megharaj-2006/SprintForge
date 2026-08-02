package org.SprintForge.modules.workspace.project.service.dashboard;

import org.SprintForge.modules.workspace.project.dto.response.ProjectDashboardSummaryResponse;

public interface ProjectDashboardService {
    ProjectDashboardSummaryResponse getDashboardSummary(Long projectId, Long actorId);
}
