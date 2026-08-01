package org.SprintForge.modules.workspace.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.dto.request.ReleasePlanRequest;
import org.SprintForge.modules.workspace.project.dto.response.RoadmapResponse;
import org.SprintForge.modules.workspace.project.service.RoadmapApplicationService;
import org.SprintForge.modules.workspace.project.service.RoadmapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Validated
@Tag(name = "Roadmap Controller", description = "REST endpoints for project roadmaps, quarterly views, and releases")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class RoadmapController {

    private final RoadmapService roadmapService;
    private final RoadmapApplicationService roadmapApplicationService;

    @Operation(summary = "Get project roadmap view")
    @GetMapping("/{id}/roadmap")
    public ResponseEntity<RoadmapResponse> getProjectRoadmap(@PathVariable Long id) {
        RoadmapResponse response = roadmapService.getProjectRoadmap(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create release plan for project roadmap")
    @PostMapping("/{id}/roadmap/releases")
    public ResponseEntity<RoadmapResponse> createRelease(
            @PathVariable Long id,
            @Valid @RequestBody ReleasePlanRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        RoadmapResponse response = roadmapApplicationService.createRelease(id, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
