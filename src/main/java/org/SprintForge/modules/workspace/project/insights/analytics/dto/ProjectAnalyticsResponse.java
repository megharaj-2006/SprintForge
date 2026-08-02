package org.SprintForge.modules.workspace.project.insights.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAnalyticsResponse {

    private Long projectId;
    private double velocity;
    private double throughputTasksPerWeek;
    private double cycleTimeDays;
    private double leadTimeDays;
    private double sprintSuccessRate;
    private double estimateAccuracyPercentage;
    private double blockedTasksPercentage;
    private double overduePercentage;
    private String productivityStatus; // EXCELLENT, STABLE, NEEDS_ATTENTION
}
