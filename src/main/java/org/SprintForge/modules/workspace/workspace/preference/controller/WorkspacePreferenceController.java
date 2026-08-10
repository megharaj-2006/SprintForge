package org.SprintForge.modules.workspace.workspace.preference.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.preference.dto.request.WorkspaceGlobalPreferenceRequest;
import org.SprintForge.modules.workspace.workspace.preference.dto.response.WorkspaceGlobalPreferenceResponse;
import org.SprintForge.modules.workspace.workspace.preference.service.WorkspacePreferenceAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workspaces/{workspaceId}/preferences")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@Tag(name = "Workspace Preferences")
public class WorkspacePreferenceController {

    private final WorkspacePreferenceAdminService workspacePreferenceAdminService;

    @GetMapping
    public ResponseEntity<WorkspaceGlobalPreferenceResponse> getWorkspacePreferences(@PathVariable Long workspaceId) {
        return ResponseEntity.ok(workspacePreferenceAdminService.getWorkspacePreferences(workspaceId));
    }

    @PutMapping
    public ResponseEntity<WorkspaceGlobalPreferenceResponse> updateWorkspacePreferences(
            @PathVariable Long workspaceId,
            @RequestBody WorkspaceGlobalPreferenceRequest request,
            @RequestHeader(value = "X-User-Id", required = false) Long actorId) {
        return ResponseEntity.ok(workspacePreferenceAdminService.updateWorkspacePreferences(workspaceId, request, actorId));
    }

    @DeleteMapping
    public ResponseEntity<Void> resetWorkspacePreferences(
            @PathVariable Long workspaceId,
            @RequestHeader(value = "X-User-Id", required = false) Long actorId) {
        workspacePreferenceAdminService.resetWorkspacePreferences(workspaceId, actorId);
        return ResponseEntity.noContent().build();
    }
}
