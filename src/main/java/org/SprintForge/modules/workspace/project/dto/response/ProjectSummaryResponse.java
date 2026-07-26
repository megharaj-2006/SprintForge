package org.SprintForge.modules.workspace.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectStatusType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSummaryResponse {

    private Long id;
    private String name;
    private String projectKey;
    private String icon;
    private String color;
    private ProjectStatusType status;
    private Double progressPercentage;
    private Boolean isArchived;
}
