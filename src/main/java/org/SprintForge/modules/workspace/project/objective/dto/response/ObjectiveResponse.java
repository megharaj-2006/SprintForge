package org.SprintForge.modules.workspace.project.objective.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.objective.entity.enums.ObjectiveStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjectiveResponse {

    private Long id;
    private Long goalId;
    private String title;
    private String description;
    private Long ownerId;
    private ObjectiveStatus status;
    private Double weight;
    private Double progressPercentage;
    private LocalDate startDate;
    private LocalDate targetDate;
    private Integer totalKeyResults;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
