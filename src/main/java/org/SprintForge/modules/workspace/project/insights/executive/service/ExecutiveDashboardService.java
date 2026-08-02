package org.SprintForge.modules.workspace.project.insights.executive.service;

import org.SprintForge.modules.workspace.project.insights.executive.dto.ExecutiveDashboardResponse;

public interface ExecutiveDashboardService {
    ExecutiveDashboardResponse getExecutiveDashboard(Long workspaceId);
}
