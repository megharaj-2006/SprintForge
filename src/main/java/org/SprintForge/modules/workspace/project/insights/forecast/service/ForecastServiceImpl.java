package org.SprintForge.modules.workspace.project.insights.forecast.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.insights.forecast.dto.CompletionForecastResponse;
import org.SprintForge.modules.workspace.project.insights.forecast.dto.ReleaseForecastResponse;
import org.SprintForge.modules.workspace.project.insights.forecast.dto.ResourceForecastResponse;
import org.SprintForge.modules.workspace.project.insights.metrics.dto.ProjectMetricsResponse;
import org.SprintForge.modules.workspace.project.insights.metrics.service.ProjectMetricsService;
import org.SprintForge.modules.workspace.project.insights.resource.dto.ResourceAllocationResponse;
import org.SprintForge.modules.workspace.project.insights.resource.service.ResourceAllocationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ForecastServiceImpl implements ForecastService {

    private final ProjectMetricsService projectMetricsService;
    private final ResourceAllocationService resourceAllocationService;

    @Override
    @Transactional(readOnly = true)
    public CompletionForecastResponse getCompletionForecast(Long projectId) {
        ProjectMetricsResponse metrics = projectMetricsService.getProjectMetrics(projectId);

        long openTasks = metrics.getOpenTasks();
        double velocity = metrics.getVelocity() > 0 ? metrics.getVelocity() : 10.0;
        long weeksRemaining = (long) Math.ceil((double) openTasks / velocity);
        long daysRemaining = weeksRemaining * 7L;

        LocalDate projectedDate = LocalDate.now().plusDays(daysRemaining);
        String status = daysRemaining <= 30 ? "ON_TRACK" : (daysRemaining <= 60 ? "SLIGHT_DELAY" : "CRITICAL_DELAY");

        return CompletionForecastResponse.builder()
                .projectId(projectId)
                .estimatedCompletionDate(projectedDate)
                .projectedDaysRemaining(daysRemaining)
                .confidenceScorePercentage(86.5)
                .forecastStatus(status)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ReleaseForecastResponse getReleaseForecast(Long projectId) {
        CompletionForecastResponse completion = getCompletionForecast(projectId);
        LocalDate targetDate = LocalDate.now().plusDays(30);

        boolean isDelayed = completion.getEstimatedCompletionDate().isAfter(targetDate);
        double delayProb = isDelayed ? 72.0 : 18.0;
        String riskLevel = isDelayed ? "HIGH" : "LOW";

        return ReleaseForecastResponse.builder()
                .projectId(projectId)
                .targetReleaseDate(targetDate)
                .estimatedReleaseDate(completion.getEstimatedCompletionDate())
                .delayProbabilityPercentage(delayProb)
                .riskLevel(riskLevel)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ResourceForecastResponse getResourceForecast(Long projectId) {
        ResourceAllocationResponse alloc = resourceAllocationService.getResourceAllocation(projectId);

        double required30Days = alloc.getTotalAssignedHours();
        double available30Days = alloc.getTotalCapacityHours();
        double deficit = Math.max(0.0, required30Days - available30Days);

        String recommendation = deficit > 0 ? "Add 1 developer to prevent schedule slippage." : "Resource capacity is optimal.";

        return ResourceForecastResponse.builder()
                .projectId(projectId)
                .requiredHoursNext30Days(required30Days)
                .availableHoursNext30Days(available30Days)
                .predictedDeficitHours(deficit)
                .resourceActionRecommendation(recommendation)
                .build();
    }
}
