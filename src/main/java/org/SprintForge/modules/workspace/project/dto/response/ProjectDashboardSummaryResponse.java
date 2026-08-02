package org.SprintForge.modules.workspace.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDashboardSummaryResponse {

    private Long projectId;
    private String projectName;
    private String projectKey;
    private String status;
    private Double completionPercentage;

    private Integer totalTasks;
    private Integer openTasks;
    private Integer completedTasks;

    private Double velocity;
    
    private Long activeSprintId;
    private String activeSprintName;

    private Long nextMilestoneId;
    private String nextMilestoneName;
    private LocalDate nextMilestoneDueDate;

    private Integer upcomingReleasesCount;
    private Integer riskCount;
    private Integer teamSize;

    private String healthStatus;
    private List<String> recentActivities;
}
