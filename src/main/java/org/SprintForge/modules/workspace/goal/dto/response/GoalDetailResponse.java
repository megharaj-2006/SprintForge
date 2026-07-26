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
public class GoalDetailResponse {

    private Long id;
    private Long projectId;
    private String projectName;
    private String title;
    private String description;
    private Long ownerId;
    private String ownerName;
    private String status;
    private String priority;
    private Double progressPercentage;
    private LocalDate startDate;
    private LocalDate targetDate;
    private LocalDateTime completedAt;
    private Integer keyResultCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
