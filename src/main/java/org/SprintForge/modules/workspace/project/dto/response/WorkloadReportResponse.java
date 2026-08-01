package org.SprintForge.modules.workspace.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkloadReportResponse {

    private Long projectId;
    private int totalTeamMembers;
    private List<UserWorkloadSummary> memberWorkloads;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserWorkloadSummary {
        private Long userId;
        private int assignedTaskCount;
        private double totalAssignedHours;
        private double weeklyCapacityHours;
        private double remainingCapacityHours;
        private boolean isOverloaded;
        private boolean isOnVacation;
    }
}
