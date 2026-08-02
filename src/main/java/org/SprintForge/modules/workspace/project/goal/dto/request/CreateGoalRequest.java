package org.SprintForge.modules.workspace.project.goal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.goal.entity.enums.GoalPriority;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGoalRequest {

    @NotBlank(message = "Goal title is required")
    @Size(min = 2, max = 150, message = "Goal title must be between 2 and 150 characters")
    private String title;

    private String description;
    private Long ownerId;
    private GoalPriority priority;
    private LocalDate startDate;
    private LocalDate targetDate;

    @Positive(message = "Goal weight must be positive")
    private Double weight;
}
