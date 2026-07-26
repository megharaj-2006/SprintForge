package org.SprintForge.modules.workspace.sprint.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintUpdateRequest {

    @Size(min = 2, max = 100, message = "Sprint name must be between 2 and 100 characters")
    private String name;

    @Size(max = 1000, message = "Goal must not exceed 1000 characters")
    private String goal;

    private SprintStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer plannedStoryPoints;
    private Integer completedStoryPoints;
    private Double capacity;
}
