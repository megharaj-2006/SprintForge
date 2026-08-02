package org.SprintForge.modules.workspace.project.insights.metrics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMetricsResponse {

    private Long projectId;
    private double completionPercentage;
    private long totalTasks;
    private long openTasks;
    private long completedTasks;
    private long blockedTasks;
    private int totalStoryPoints;
    private double velocity;
    private double cycleTimeDays;
    private double leadTimeDays;
    private double sprintSuccessRate;
    private double releaseProgressPercentage;
    private double goalProgressPercentage;
    private double objectiveProgressPercentage;
    private double keyResultProgressPercentage;
    private long openRisksCount;
    private long decisionsCount;
    private long approvalsCount;
    private long documentsCount;
    private long teamSize;
    private double averageWorkloadTasksPerMember;
}
