package org.SprintForge.modules.workspace.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ProjectCreateRequest {

    @NotNull(message = "Workspace ID is required")
    private Long workspaceId;

    @NotBlank(message = "Project name is required")
    @Size(min = 2, max = 100, message = "Project name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Project key is required")
    @Size(min = 2, max = 10, message = "Project key must be between 2 and 10 characters")
    private String projectKey;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    private String icon;
    private String coverImage;
    private String color;

    private ProjectVisibility visibility;
    private ProjectStatusType status;

    private Long ownerId;
    private String slug;
    private Long leadId;
    private Long defaultAssigneeId;
    private Long categoryId;

    private LocalDate startDate;
    private LocalDate targetEndDate;

    private Double budget;
    private String currency;
    private Double estimatedHours;
    private Boolean isTemplate;
}
