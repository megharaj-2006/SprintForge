package org.SprintForge.modules.workspace.project.objective.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class CreateObjectiveRequest {

    @NotBlank(message = "Objective title is required")
    @Size(min = 2, max = 150, message = "Objective title must be between 2 and 150 characters")
    private String title;

    private String description;
    private Long ownerId;
    private LocalDate startDate;
    private LocalDate targetDate;

    @Positive(message = "Objective weight must be positive")
    private Double weight;
}
