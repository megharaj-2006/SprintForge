package org.SprintForge.modules.workspace.project.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberAllocationRequest {

    @NotNull(message = "Allocation percentage is required")
    @Min(value = 0, message = "Allocation percentage cannot be negative")
    @Max(value = 100, message = "Allocation percentage cannot exceed 100")
    private Double allocationPercentage;
}
