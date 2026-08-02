package org.SprintForge.modules.workspace.project.insights.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityAnalyticsResponse {

    private Long projectId;
    private long blockedTasksCount;
    private long reopenedTasksCount;
    private long overdueTasksCount;
    private String riskTrendStatus; // DECREASING, STABLE, INCREASING
    private double defectDensity;
}
