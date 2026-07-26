package org.SprintForge.modules.workspace.goal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String status;
    private String priority;
    private Double progressPercentage;
    private LocalDate startDate;
    private LocalDate targetDate;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
