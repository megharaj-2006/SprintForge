package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.request.CreateTaskRelationshipRequest;
import org.SprintForge.modules.workspace.task.dto.response.DependencyGraphResponse;
import org.SprintForge.modules.workspace.task.entity.AdvancedTaskRelationship;
import org.SprintForge.modules.workspace.task.service.DependencyGraphService;
import org.SprintForge.modules.workspace.task.service.TaskRelationshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Relationship Controller", description = "REST endpoints for advanced task relationships, dependency graph, and cycle detection")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TaskRelationshipController {

    private final TaskRelationshipService taskRelationshipService;
    private final DependencyGraphService dependencyGraphService;

    @Operation(summary = "Create an advanced task relationship")
    @PostMapping("/tasks/{id}/relationships")
    public ResponseEntity<AdvancedTaskRelationship> createRelationship(
            @PathVariable Long id,
            @Valid @RequestBody CreateTaskRelationshipRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        AdvancedTaskRelationship response = taskRelationshipService.createRelationship(id, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Delete a task relationship")
    @DeleteMapping("/relationships/{id}")
    public ResponseEntity<Void> deleteRelationship(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        taskRelationshipService.deleteRelationship(id, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get full dependency graph rooted at a task")
    @GetMapping("/tasks/{id}/graph")
    public ResponseEntity<DependencyGraphResponse> getDependencyGraph(@PathVariable Long id) {
        DependencyGraphResponse response = dependencyGraphService.buildGraph(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get tasks blocked by this task")
    @GetMapping("/tasks/{id}/blocked")
    public ResponseEntity<List<AdvancedTaskRelationship>> getBlockedTasks(@PathVariable Long id) {
        List<AdvancedTaskRelationship> response = taskRelationshipService.getBlockedTasks(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get tasks blocking this task")
    @GetMapping("/tasks/{id}/blocking")
    public ResponseEntity<List<AdvancedTaskRelationship>> getBlockingTasks(@PathVariable Long id) {
        List<AdvancedTaskRelationship> response = taskRelationshipService.getBlockingTasks(id);
        return ResponseEntity.ok(response);
    }
}
