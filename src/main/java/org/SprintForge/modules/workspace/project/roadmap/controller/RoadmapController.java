package org.SprintForge.modules.workspace.project.roadmap.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.roadmap.dto.StrategicRoadmapResponse;
import org.SprintForge.modules.workspace.project.roadmap.service.RoadmapService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("strategicRoadmapController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Roadmap Controller", description = "REST endpoints for strategic project roadmaps")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class RoadmapController {

    private final RoadmapService roadmapService;

    @Operation(summary = "Get strategic project roadmap")
    @GetMapping("/projects/{projectId}/roadmap")
    public ResponseEntity<StrategicRoadmapResponse> getProjectRoadmap(
            @PathVariable("projectId") Long projectId,
            @RequestParam(value = "timeframe", defaultValue = "QUARTERLY") String timeframe,
            @RequestParam(value = "viewMode", defaultValue = "TIMELINE") String viewMode) {
        return ResponseEntity.ok(roadmapService.getProjectRoadmap(projectId, timeframe, viewMode));
    }
}
