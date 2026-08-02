package org.SprintForge.modules.workspace.project.progress.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectProgressResponse {

    private Long projectId;
    private String projectName;
    private Double overallProgressPercentage;
    private Double goalsProgressPercentage;
    private Double releasesProgressPercentage;
    private Double tasksProgressPercentage;
    private String healthScore; // EXCELLENT, GOOD, AT_RISK, CRITICAL
    private Integer totalGoals;
    private Integer totalReleases;
    private Integer totalTasks;
    private List<GoalProgressDetail> goalDetails;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoalProgressDetail {
        private Long goalId;
        private String title;
        private Double progressPercentage;
        private Double weight;
    }
}
