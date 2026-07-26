package org.SprintForge.modules.workspace.project.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectStatusType;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectVisibility;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdateRequest {

    @Size(min = 2, max = 100, message = "Project name must be between 2 and 100 characters")
    private String name;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    private String icon;
    private String coverImage;
    private String color;

    private ProjectVisibility visibility;
    private ProjectStatusType status;

    private Long ownerId;

    private LocalDate startDate;
    private LocalDate targetEndDate;

    private Double progressPercentage;
    private Double budget;
    private String currency;
    private Double estimatedHours;
    private Boolean isTemplate;
    private Boolean isArchived;
}
