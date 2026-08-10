package org.SprintForge.modules.workspace.workspace.branding.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.branding.dto.request.WorkspaceBrandingUpdateRequest;
import org.SprintForge.modules.workspace.workspace.branding.dto.response.WorkspaceBrandingResponse;
import org.SprintForge.modules.workspace.workspace.branding.service.WorkspaceBrandingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workspaces/{workspaceId}/branding")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@Tag(name = "Workspace Branding")
public class WorkspaceBrandingController {

    private final WorkspaceBrandingService workspaceBrandingService;

    @GetMapping
    public ResponseEntity<WorkspaceBrandingResponse> getBranding(@PathVariable Long workspaceId) {
        return ResponseEntity.ok(workspaceBrandingService.getBranding(workspaceId));
    }

    @PutMapping
    public ResponseEntity<WorkspaceBrandingResponse> updateBranding(
            @PathVariable Long workspaceId,
            @RequestBody WorkspaceBrandingUpdateRequest request,
            @RequestHeader(value = "X-User-Id", required = false) Long actorId) {
        return ResponseEntity.ok(workspaceBrandingService.updateBranding(workspaceId, request, actorId));
    }

    @DeleteMapping
    public ResponseEntity<Void> resetBranding(
            @PathVariable Long workspaceId,
            @RequestHeader(value = "X-User-Id", required = false) Long actorId) {
        workspaceBrandingService.resetBranding(workspaceId, actorId);
        return ResponseEntity.noContent().build();
    }
}
