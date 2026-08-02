package org.SprintForge.modules.workspace.project.dto.response;

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
public class ProjectCardResponse {

    private Long id;
    private Long workspaceId;
    private String name;
    private String projectKey;
    private String slug;
    private String icon;
    private String color;
    private ProjectVisibility visibility;
    private ProjectStatusType status;
    private Long ownerId;
    private Long leadId;
    private LocalDate targetEndDate;
    private Double progressPercentage;
    private Integer memberCount;
    private Integer taskCount;
    private String healthScore;
}
