package org.SprintForge.modules.workspace.bookmark.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.bookmark.dto.request.*;
import org.SprintForge.modules.workspace.bookmark.dto.response.SavedViewResponse;
import org.SprintForge.modules.workspace.bookmark.dto.response.SavedViewSummaryResponse;
import org.SprintForge.modules.workspace.bookmark.service.SavedViewService;
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
@Tag(name = "Saved View Controller", description = "REST endpoints for managing saved task filters, layouts, and views")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class SavedViewController {

    private final SavedViewService savedViewService;

    @Operation(summary = "Create saved view for a project")
    @PostMapping("/projects/{projectId}/views")
    public ResponseEntity<SavedViewResponse> createView(
            @PathVariable Long projectId,
            @Valid @RequestBody CreateSavedViewRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        SavedViewResponse response = savedViewService.createView(projectId, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get accessible saved views for a project")
    @GetMapping("/projects/{projectId}/views")
    public ResponseEntity<List<SavedViewSummaryResponse>> getProjectViews(
            @PathVariable Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<SavedViewSummaryResponse> response = savedViewService.getProjectViews(projectId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get saved view by ID")
    @GetMapping("/views/{id}")
    public ResponseEntity<SavedViewResponse> getViewById(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        SavedViewResponse response = savedViewService.getViewById(id, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update saved view")
    @PatchMapping("/views/{id}")
    public ResponseEntity<SavedViewResponse> updateView(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSavedViewRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        SavedViewResponse response = savedViewService.updateView(id, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete saved view")
    @DeleteMapping("/views/{id}")
    public ResponseEntity<Void> deleteView(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        savedViewService.deleteView(id, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Toggle favorite status on saved view")
    @PostMapping("/views/{id}/favorite")
    public ResponseEntity<SavedViewResponse> favoriteView(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        SavedViewResponse response = savedViewService.favoriteView(id, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Share saved view with project or workspace")
    @PostMapping("/views/{id}/share")
    public ResponseEntity<SavedViewResponse> shareView(
            @PathVariable Long id,
            @Valid @RequestBody ShareSavedViewRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        SavedViewResponse response = savedViewService.shareView(id, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Apply saved view to retrieve configured task state")
    @PostMapping("/views/{id}/apply")
    public ResponseEntity<SavedViewResponse> applyView(
            @PathVariable Long id,
            @Valid @RequestBody(required = false) ApplySavedViewRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ApplySavedViewRequest req = request != null ? request : new ApplySavedViewRequest();
        SavedViewResponse response = savedViewService.applyView(id, req, actorId);
        return ResponseEntity.ok(response);
    }
}
