package org.SprintForge.modules.workspace.sprint.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class SprintCreateRequest {

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotBlank(message = "Sprint name is required")
    @Size(min = 2, max = 100, message = "Sprint name must be between 2 and 100 characters")
    private String name;

    @Size(max = 1000, message = "Goal must not exceed 1000 characters")
    private String goal;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer plannedStoryPoints;
    private Double capacity;
}
