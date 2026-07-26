package org.SprintForge.modules.workspace.milestone.dto.response;

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
public class MilestoneResponse {

    private Long id;
    private Long projectId;
    private String name;
    private String description;
    private String status;
    private LocalDate dueDate;
    private LocalDateTime completedAt;
    private Double progressPercentage;
    private Long createdByUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
