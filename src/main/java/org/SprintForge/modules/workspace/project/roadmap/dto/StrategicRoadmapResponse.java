package org.SprintForge.modules.workspace.project.roadmap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategicRoadmapResponse {

    private Long projectId;
    private String projectName;
    private String timeframe; // QUARTERLY, HALF_YEAR, YEARLY, MULTI_YEAR
    private String viewMode; // QUARTERLY, MONTHLY, TIMELINE, RELEASE_VIEW, EXECUTIVE
    private List<RoadmapItemResponse> items;
}
