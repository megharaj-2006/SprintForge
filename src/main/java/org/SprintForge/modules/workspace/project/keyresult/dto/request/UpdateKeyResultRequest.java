package org.SprintForge.modules.workspace.project.keyresult.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.keyresult.entity.enums.KeyResultMetricType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateKeyResultRequest {

    @Size(min = 2, max = 150, message = "Key result title must be between 2 and 150 characters")
    private String title;

    private String description;
    private KeyResultMetricType metricType;
    private Double targetValue;
    private Double currentValue;
    private String unit;
    private Double weight;
    private String status;
}
