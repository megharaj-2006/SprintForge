package org.SprintForge.modules.workspace.project.governance.dashboard.service;

import org.SprintForge.modules.workspace.project.governance.dashboard.dto.GovernanceDashboardResponse;
import org.SprintForge.modules.workspace.project.governance.dashboard.dto.GovernanceSummaryResponse;

public interface GovernanceDashboardService {
    GovernanceDashboardResponse getProjectGovernance(Long projectId);
    GovernanceSummaryResponse getProjectGovernanceSummary(Long projectId);
}
