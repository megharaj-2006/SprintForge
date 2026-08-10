package org.SprintForge.modules.workspace.workspace.security.token.service;

import org.SprintForge.modules.workspace.workspace.security.token.dto.request.CreateApiTokenRequest;
import org.SprintForge.modules.workspace.workspace.security.token.dto.request.RotateApiTokenRequest;
import org.SprintForge.modules.workspace.workspace.security.token.dto.response.ApiTokenCreatedResponse;
import org.SprintForge.modules.workspace.workspace.security.token.dto.response.ApiTokenResponse;

import java.util.List;

public interface WorkspaceApiTokenService {
    ApiTokenCreatedResponse generateToken(Long workspaceId, CreateApiTokenRequest request, Long actorId);
    ApiTokenCreatedResponse rotateToken(Long workspaceId, Long tokenId, RotateApiTokenRequest request, Long actorId);
    void revokeToken(Long workspaceId, Long tokenId, Long actorId);
    List<ApiTokenResponse> listTokens(Long workspaceId, Long actorId);
    ApiTokenResponse getToken(Long workspaceId, Long tokenId);
    boolean validateToken(String rawToken);
}
