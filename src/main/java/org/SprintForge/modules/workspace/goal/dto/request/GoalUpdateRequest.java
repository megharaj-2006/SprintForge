package org.SprintForge.modules.workspace.goal.dto.request;

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
public class GoalUpdateRequest {

    @Size(min = 2, max = 200, message = "Title must be between 2 and 200 characters")
    private String title;

    private String description;
    private Long ownerId;
    private String status;
    private String priority;
    private Double progressPercentage;
    private LocalDate startDate;
    private LocalDate targetDate;
}
