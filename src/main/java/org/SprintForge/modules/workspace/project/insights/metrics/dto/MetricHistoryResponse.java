package org.SprintForge.modules.workspace.project.insights.metrics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricHistoryResponse {

    private Long projectId;
    private LocalDate snapshotDate;
    private double completionPercentage;
    private double velocity;
    private double healthScore;
}
