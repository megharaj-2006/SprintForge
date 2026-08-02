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
public class ReleaseForecastResponse {

    private Long projectId;
    private LocalDate targetReleaseDate;
    private LocalDate estimatedReleaseDate;
    private double delayProbabilityPercentage;
    private String riskLevel; // LOW, MEDIUM, HIGH
}
