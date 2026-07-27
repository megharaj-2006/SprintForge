package org.SprintForge.modules.workspace.sprint.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintDuplicateRequest {

    @NotBlank(message = "Sprint name is required")
    @Size(min = 2, max = 100, message = "Sprint name must be between 2 and 100 characters")
    private String name;

    @Builder.Default
    private boolean copyGoal = true;

    private LocalDate startDate;
}
