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
public class RoadmapResponse {

    private Long projectId;
    private String projectName;
    private List<RoadmapEpicItem> epics;
    private List<RoadmapSprintItem> sprints;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoadmapEpicItem {
        private Long id;
        private String name;
        private String status;
        private LocalDate startDate;
        private LocalDate endDate;
        private Double progressPercentage;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoadmapSprintItem {
        private Long id;
        private String name;
        private String status;
        private LocalDate startDate;
        private LocalDate endDate;
    }
}
