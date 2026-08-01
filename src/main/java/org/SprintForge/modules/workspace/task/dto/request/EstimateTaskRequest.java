package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstimateTaskRequest {

    @NotBlank(message = "Estimate type is required")
    private String estimateType; // HOURS, DAYS, STORY_POINTS, COMPLEXITY, TSHIRT_SIZE, COST_ESTIMATE

    @NotNull(message = "Estimated value is required")
    private Double estimatedValue;

    private Double actualValue;
}
