package org.SprintForge.modules.workspace.sprint.dto.response;

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
public class SprintBurndownResponse {

    private Long sprintId;
    private String sprintName;
    private Integer totalPlannedStoryPoints;
    private List<BurndownPoint> burndownData;
    private List<BurndownPoint> burnupData;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BurndownPoint {
        private LocalDate date;
        private Double idealRemaining;
        private Double actualRemaining;
        private Double completedToDate;
    }
}
