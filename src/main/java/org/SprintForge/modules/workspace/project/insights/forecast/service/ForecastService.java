package org.SprintForge.modules.workspace.project.insights.forecast.service;

import org.SprintForge.modules.workspace.project.insights.forecast.dto.CompletionForecastResponse;
import org.SprintForge.modules.workspace.project.insights.forecast.dto.ReleaseForecastResponse;
import org.SprintForge.modules.workspace.project.insights.forecast.dto.ResourceForecastResponse;

public interface ForecastService {
    CompletionForecastResponse getCompletionForecast(Long projectId);
    ReleaseForecastResponse getReleaseForecast(Long projectId);
    ResourceForecastResponse getResourceForecast(Long projectId);
}
