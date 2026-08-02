package org.SprintForge.modules.workspace.project.progress.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.progress.dto.GoalProgressResponse;
import org.SprintForge.modules.workspace.project.progress.dto.ProjectProgressResponse;
import org.SprintForge.modules.workspace.project.progress.service.ProgressEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("strategicProgressController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Progress Controller", description = "REST endpoints for strategic progress engine calculation and analytics")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ProgressController {

    private final ProgressEngineService progressEngineService;

    @Operation(summary = "Get aggregate strategic progress for a project")
    @GetMapping("/projects/{projectId}/progress")
    public ResponseEntity<ProjectProgressResponse> getProjectProgress(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(progressEngineService.calculateProjectProgress(projectId));
    }

    @Operation(summary = "Get strategic progress for a goal")
    @GetMapping("/goals/{goalId}/progress")
    public ResponseEntity<GoalProgressResponse> getGoalProgress(@PathVariable("goalId") Long goalId) {
        return ResponseEntity.ok(progressEngineService.calculateGoalProgress(goalId));
    }

    @Operation(summary = "Recalculate project progress metrics")
    @PostMapping("/projects/{projectId}/progress/recalculate")
    public ResponseEntity<ProjectProgressResponse> recalculateProgress(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(progressEngineService.calculateProjectProgress(projectId));
    }
}
