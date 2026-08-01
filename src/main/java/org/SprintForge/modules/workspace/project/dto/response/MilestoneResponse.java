package org.SprintForge.modules.workspace.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.entity.enums.MilestoneStatus;

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
    private LocalDate dueDate;
    private MilestoneStatus status;
    private LocalDateTime completedAt;
    private Boolean isArchived;
    private Double progressPercentage;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
