package org.SprintForge.modules.workspace.workspace.security.token.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.security.token.dto.request.CreateApiTokenRequest;
import org.SprintForge.modules.workspace.workspace.security.token.dto.request.RotateApiTokenRequest;
import org.SprintForge.modules.workspace.workspace.security.token.dto.response.ApiTokenCreatedResponse;
import org.SprintForge.modules.workspace.workspace.security.token.dto.response.ApiTokenResponse;
import org.SprintForge.modules.workspace.workspace.security.token.service.WorkspaceApiTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workspaces/{workspaceId}/tokens")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@Tag(name = "API Token Management")
public class WorkspaceApiTokenController {

    private final WorkspaceApiTokenService workspaceApiTokenService;

    @PostMapping
    public ResponseEntity<ApiTokenCreatedResponse> generateToken(
            @PathVariable Long workspaceId,
            @Valid @RequestBody CreateApiTokenRequest request,
            @RequestHeader("X-User-Id") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workspaceApiTokenService.generateToken(workspaceId, request, actorId));
    }

    @GetMapping
    public List<ApiTokenResponse> listTokens(
            @PathVariable Long workspaceId,
            @RequestHeader("X-User-Id") Long actorId) {
        return workspaceApiTokenService.listTokens(workspaceId, actorId);
    }

    @GetMapping("/{tokenId}")
    public ApiTokenResponse getToken(
            @PathVariable Long workspaceId,
            @PathVariable Long tokenId) {
        return workspaceApiTokenService.getToken(workspaceId, tokenId);
    }

    @DeleteMapping("/{tokenId}")
    public ResponseEntity<Void> revokeToken(
            @PathVariable Long workspaceId,
            @PathVariable Long tokenId,
            @RequestHeader("X-User-Id") Long actorId) {
        workspaceApiTokenService.revokeToken(workspaceId, tokenId, actorId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tokenId}/rotate")
    public ApiTokenCreatedResponse rotateToken(
            @PathVariable Long workspaceId,
            @PathVariable Long tokenId,
            @RequestBody RotateApiTokenRequest request,
            @RequestHeader("X-User-Id") Long actorId) {
        return workspaceApiTokenService.rotateToken(workspaceId, tokenId, request, actorId);
    }
}
