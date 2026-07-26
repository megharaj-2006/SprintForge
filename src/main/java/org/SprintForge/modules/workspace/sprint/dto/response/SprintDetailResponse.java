package org.SprintForge.modules.workspace.sprint.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintDetailResponse {

    private Long id;
    private Long projectId;
    private String projectName;
    private String name;
    private String goal;
    private SprintStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime completedAt;
    private Integer plannedStoryPoints;
    private Integer completedStoryPoints;
    private Double velocity;
    private Double capacity;
    private Integer completedTaskCount;
    private Integer totalTaskCount;
    private Double progressPercentage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
