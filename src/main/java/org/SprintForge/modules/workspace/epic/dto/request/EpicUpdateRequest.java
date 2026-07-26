package org.SprintForge.modules.workspace.epic.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EpicUpdateRequest {

    @Size(min = 2, max = 100, message = "Epic name must be between 2 and 100 characters")
    private String name;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    private String color;
    private String status;
    private Long ownerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double progressPercentage;
    private Integer estimatedStoryPoints;
    private Integer completedStoryPoints;
}
