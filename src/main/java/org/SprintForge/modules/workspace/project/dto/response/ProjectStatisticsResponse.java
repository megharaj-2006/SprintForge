package org.SprintForge.modules.workspace.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectStatisticsResponse {
    private Long projectId;
    private Integer activeMemberCount;
    private Integer openTaskCount;
    private Integer completedTaskCount;
    private Double progressPercentage;
    private Double budget;
    private Double loggedHours;
    private Double estimatedHours;
}
