package org.SprintForge.modules.workspace.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.workspace.project.dto.request.ReleasePlanRequest;
import org.SprintForge.modules.workspace.project.dto.response.RoadmapResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoadmapApplicationService {

    private final RoadmapService roadmapService;

    @Transactional
    public RoadmapResponse createRelease(Long projectId, ReleasePlanRequest request, Long actorId) {
        log.info("Creating release {} for project {} by user {}", request.getReleaseVersion(), projectId, actorId);
        return roadmapService.getProjectRoadmap(projectId);
    }
}
