package org.SprintForge.modules.workspace.project.release.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.release.dto.request.CreateReleaseRequest;
import org.SprintForge.modules.workspace.project.release.dto.request.UpdateReleaseRequest;
import org.SprintForge.modules.workspace.project.release.dto.response.ReleaseProgressResponse;
import org.SprintForge.modules.workspace.project.release.dto.response.ReleaseResponse;
import org.SprintForge.modules.workspace.project.release.service.ReleaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("strategicReleaseController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Release Controller", description = "REST endpoints for project release management and planning")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ReleaseController {

    private final ReleaseService releaseService;

    @Operation(summary = "Create a new release for a project")
    @PostMapping("/projects/{projectId}/releases")
    public ResponseEntity<ReleaseResponse> createRelease(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody CreateReleaseRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(releaseService.createRelease(projectId, request, actorId));
    }

    @Operation(summary = "Get all releases for a project")
    @GetMapping("/projects/{projectId}/releases")
    public ResponseEntity<List<ReleaseResponse>> getReleases(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(releaseService.getReleases(projectId));
    }

    @Operation(summary = "Get release details by ID")
    @GetMapping("/releases/{releaseId}")
    public ResponseEntity<ReleaseResponse> getRelease(@PathVariable("releaseId") Long releaseId) {
        return ResponseEntity.ok(releaseService.getRelease(releaseId));
    }

    @Operation(summary = "Update release details")
    @PatchMapping("/releases/{releaseId}")
    public ResponseEntity<ReleaseResponse> updateRelease(
            @PathVariable("releaseId") Long releaseId,
            @Valid @RequestBody UpdateReleaseRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(releaseService.updateRelease(releaseId, request, actorId));
    }

    @Operation(summary = "Delete / archive a release")
    @DeleteMapping("/releases/{releaseId}")
    public ResponseEntity<Void> deleteRelease(
            @PathVariable("releaseId") Long releaseId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        releaseService.deleteRelease(releaseId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Publish a release")
    @PostMapping("/releases/{releaseId}/publish")
    public ResponseEntity<ReleaseResponse> publishRelease(
            @PathVariable("releaseId") Long releaseId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(releaseService.publishRelease(releaseId, actorId));
    }

    @Operation(summary = "Clone a release")
    @PostMapping("/releases/{releaseId}/clone")
    public ResponseEntity<ReleaseResponse> cloneRelease(
            @PathVariable("releaseId") Long releaseId,
            @RequestParam(value = "newVersion", required = false) String newVersion,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(releaseService.cloneRelease(releaseId, newVersion, actorId));
    }

    @Operation(summary = "Assign a task to a release")
    @PostMapping("/releases/{releaseId}/tasks/{taskId}")
    public ResponseEntity<Void> assignTaskToRelease(
            @PathVariable("releaseId") Long releaseId,
            @PathVariable("taskId") Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        releaseService.assignTaskToRelease(releaseId, taskId, actorId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get release progress metrics")
    @GetMapping("/releases/{releaseId}/progress")
    public ResponseEntity<ReleaseProgressResponse> getReleaseProgress(@PathVariable("releaseId") Long releaseId) {
        return ResponseEntity.ok(releaseService.getReleaseProgress(releaseId));
    }
}
