package org.SprintForge.modules.workspace.project.insights.analytics.service;

import org.SprintForge.modules.workspace.project.insights.analytics.dto.ProjectAnalyticsResponse;
import org.SprintForge.modules.workspace.project.insights.analytics.dto.QualityAnalyticsResponse;
import org.SprintForge.modules.workspace.project.insights.analytics.dto.TeamAnalyticsResponse;

public interface ProjectAnalyticsService {
    ProjectAnalyticsResponse getProjectAnalytics(Long projectId);
    TeamAnalyticsResponse getTeamAnalytics(Long projectId);
    QualityAnalyticsResponse getQualityAnalytics(Long projectId);
}
