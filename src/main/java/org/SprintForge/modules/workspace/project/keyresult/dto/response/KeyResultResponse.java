package org.SprintForge.modules.workspace.project.keyresult.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.keyresult.entity.enums.KeyResultMetricType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyResultResponse {

    private Long id;
    private Long objectiveId;
    private String title;
    private String description;
    private KeyResultMetricType metricType;
    private Double targetValue;
    private Double currentValue;
    private String unit;
    private Double weight;
    private Double progressPercentage;
    private String status;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
