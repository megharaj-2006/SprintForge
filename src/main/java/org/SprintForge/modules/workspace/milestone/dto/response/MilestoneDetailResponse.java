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
public class MilestoneDetailResponse {

    private Long id;
    private Long projectId;
    private String projectName;
    private String name;
    private String description;
    private String status;
    private LocalDate dueDate;
    private LocalDateTime completedAt;
    private Double progressPercentage;
    private Long createdByUserId;
    private String createdByUserName;
    private Integer totalTaskCount;
    private Integer completedTaskCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
