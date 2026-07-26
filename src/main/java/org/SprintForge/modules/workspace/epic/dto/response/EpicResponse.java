package org.SprintForge.modules.workspace.epic.dto.response;

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
public class EpicResponse {

    private Long id;
    private Long projectId;
    private String name;
    private String description;
    private String color;
    private String status;
    private Long ownerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime completedAt;
    private Double progressPercentage;
    private Integer estimatedStoryPoints;
    private Integer completedStoryPoints;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
