package org.SprintForge.modules.workspace.project.insights.forecast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompletionForecastResponse {

    private Long projectId;
    private LocalDate estimatedCompletionDate;
    private long projectedDaysRemaining;
    private double confidenceScorePercentage;
    private String forecastStatus; // ON_TRACK, SLIGHT_DELAY, CRITICAL_DELAY
}
