package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstimationAccuracyReportResponse {

    private Long projectId;
    private int totalEstimatedTasks;
    private double averageEstimatedHours;
    private double averageActualHours;
    private double averageVariance;
    private double accuracyPercentage;
    private int overEstimatedCount;
    private int underEstimatedCount;
    private int accurateCount;
}
