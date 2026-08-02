package org.SprintForge.modules.workspace.project.keyresult.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class CreateKeyResultRequest {

    @NotBlank(message = "Key result title is required")
    @Size(min = 2, max = 150, message = "Key result title must be between 2 and 150 characters")
    private String title;

    private String description;
    private KeyResultMetricType metricType;

    @NotNull(message = "Target value is required")
    @Positive(message = "Target value must be greater than zero")
    private Double targetValue;

    private Double currentValue;
    private String unit;

    @Positive(message = "Key result weight must be positive")
    private Double weight;
}
