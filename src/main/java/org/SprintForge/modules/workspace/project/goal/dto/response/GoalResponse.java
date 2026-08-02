package org.SprintForge.modules.workspace.project.goal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.goal.entity.enums.GoalPriority;
import org.SprintForge.modules.workspace.project.goal.entity.enums.GoalStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalResponse {

    private Long id;
    private Long projectId;
    private String title;
    private String description;
    private Long ownerId;
    private GoalPriority priority;
    private GoalStatus status;
    private LocalDate startDate;
    private LocalDate targetDate;
    private LocalDateTime completedDate;
    private Double weight;
    private Double progressPercentage;
    private Boolean isArchived;
    private Integer totalObjectives;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
