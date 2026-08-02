package org.SprintForge.modules.workspace.project.insights.forecast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceForecastResponse {

    private Long projectId;
    private double requiredHoursNext30Days;
    private double availableHoursNext30Days;
    private double predictedDeficitHours;
    private String resourceActionRecommendation;
}
