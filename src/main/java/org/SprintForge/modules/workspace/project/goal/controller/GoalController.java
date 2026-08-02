package org.SprintForge.modules.workspace.project.goal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.goal.dto.request.CreateGoalRequest;
import org.SprintForge.modules.workspace.project.goal.dto.request.UpdateGoalRequest;
import org.SprintForge.modules.workspace.project.goal.dto.response.GoalResponse;
import org.SprintForge.modules.workspace.project.goal.service.GoalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("strategicGoalController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Goal Controller", description = "REST endpoints for managing strategic goals")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class GoalController {

    private final GoalService goalService;

    @Operation(summary = "Create a strategic goal for a project")
    @PostMapping("/projects/{projectId}/goals")
    public ResponseEntity<GoalResponse> createGoal(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody CreateGoalRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(goalService.createGoal(projectId, request, actorId));
    }

    @Operation(summary = "Get all strategic goals for a project")
    @GetMapping("/projects/{projectId}/goals")
    public ResponseEntity<List<GoalResponse>> getGoals(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(goalService.getGoals(projectId));
    }

    @Operation(summary = "Get goal details by ID")
    @GetMapping("/goals/{goalId}")
    public ResponseEntity<GoalResponse> getGoal(@PathVariable("goalId") Long goalId) {
        return ResponseEntity.ok(goalService.getGoal(goalId));
    }

    @Operation(summary = "Update goal details")
    @PatchMapping("/goals/{goalId}")
    public ResponseEntity<GoalResponse> updateGoal(
            @PathVariable("goalId") Long goalId,
            @Valid @RequestBody UpdateGoalRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(goalService.updateGoal(goalId, request, actorId));
    }

    @Operation(summary = "Delete a goal")
    @DeleteMapping("/goals/{goalId}")
    public ResponseEntity<Void> deleteGoal(
            @PathVariable("goalId") Long goalId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        goalService.deleteGoal(goalId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Archive a goal")
    @PostMapping("/goals/{goalId}/archive")
    public ResponseEntity<GoalResponse> archiveGoal(
            @PathVariable("goalId") Long goalId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(goalService.archiveGoal(goalId, actorId));
    }

    @Operation(summary = "Clone a goal")
    @PostMapping("/goals/{goalId}/clone")
    public ResponseEntity<GoalResponse> cloneGoal(
            @PathVariable("goalId") Long goalId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(goalService.cloneGoal(goalId, actorId));
    }
}
