package org.SprintForge.modules.workspace.project.objective.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.objective.entity.enums.ObjectiveStatus;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateObjectiveRequest {

    @Size(min = 2, max = 150, message = "Objective title must be between 2 and 150 characters")
    private String title;

    private String description;
    private Long ownerId;
    private ObjectiveStatus status;
    private Double weight;
    private LocalDate startDate;
    private LocalDate targetDate;
}
