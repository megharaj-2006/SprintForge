package org.SprintForge.modules.workspace.project.keyresult.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateKeyResultProgressRequest {

    @NotNull(message = "Current value is required")
    private Double currentValue;
}
