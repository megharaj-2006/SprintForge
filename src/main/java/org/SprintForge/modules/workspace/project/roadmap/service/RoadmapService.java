package org.SprintForge.modules.workspace.project.roadmap.service;

import org.SprintForge.modules.workspace.project.roadmap.dto.StrategicRoadmapResponse;

public interface RoadmapService {
    StrategicRoadmapResponse getProjectRoadmap(Long projectId, String timeframe, String viewMode);
}
