package org.SprintForge.modules.workspace.project.insights.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamAnalyticsResponse {

    private Long projectId;
    private long totalMembers;
    private long activeContributorsCount;
    private double averageTasksAssignedPerMember;
    private double workloadBalanceScore; // 0.0 - 100.0
    private String teamHealth;
}
