package org.SprintForge.modules.workspace.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectStatusType;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectVisibility;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {

    private Long id;
    private Long workspaceId;
    private String name;
    private String projectKey;
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
    private LocalDate actualEndDate;
    private LocalDateTime completedAt;
    private Double progressPercentage;
    private Double budget;
    private String currency;
    private Double estimatedHours;
    private Double loggedHours;
    private Boolean isTemplate;
    private Boolean isArchived;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
