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
public class GoalProgressResponse {

    private Long goalId;
    private String title;
    private Double progressPercentage;
    private String status;
    private Integer totalObjectives;
    private Integer completedObjectives;
    private List<ObjectiveProgressDetail> objectiveDetails;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ObjectiveProgressDetail {
        private Long objectiveId;
        private String title;
        private Double progressPercentage;
        private Double weight;
        private Integer totalKeyResults;
    }
}
