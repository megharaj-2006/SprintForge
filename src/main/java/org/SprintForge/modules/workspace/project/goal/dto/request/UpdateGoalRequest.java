package org.SprintForge.modules.workspace.project.goal.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.goal.entity.enums.GoalPriority;
import org.SprintForge.modules.workspace.project.goal.entity.enums.GoalStatus;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGoalRequest {

    @Size(min = 2, max = 150, message = "Goal title must be between 2 and 150 characters")
    private String title;

    private String description;
    private Long ownerId;
    private GoalPriority priority;
    private GoalStatus status;
    private LocalDate startDate;
    private LocalDate targetDate;
    private Double weight;
    private Boolean isArchived;
}
