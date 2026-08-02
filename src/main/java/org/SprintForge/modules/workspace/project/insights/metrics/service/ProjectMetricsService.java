package org.SprintForge.modules.workspace.project.insights.metrics.service;

import org.SprintForge.modules.workspace.project.insights.metrics.dto.MetricHistoryResponse;
import org.SprintForge.modules.workspace.project.insights.metrics.dto.ProjectMetricsResponse;

import java.util.List;

public interface ProjectMetricsService {
    ProjectMetricsResponse getProjectMetrics(Long projectId);
    List<MetricHistoryResponse> getMetricsHistory(Long projectId);
    ProjectMetricsResponse recalculateMetrics(Long projectId);
}
