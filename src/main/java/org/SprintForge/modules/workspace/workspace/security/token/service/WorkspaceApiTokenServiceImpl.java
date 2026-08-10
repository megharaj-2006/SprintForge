package org.SprintForge.modules.workspace.workspace.security.token.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceApiToken;
import org.SprintForge.modules.workspace.workspace.security.token.dto.request.CreateApiTokenRequest;
import org.SprintForge.modules.workspace.workspace.security.token.dto.request.RotateApiTokenRequest;
import org.SprintForge.modules.workspace.workspace.security.token.dto.response.ApiTokenCreatedResponse;
import org.SprintForge.modules.workspace.workspace.security.token.dto.response.ApiTokenResponse;
import org.SprintForge.modules.workspace.workspace.security.token.event.ApiTokenCreatedEvent;
import org.SprintForge.modules.workspace.workspace.security.token.event.ApiTokenRevokedEvent;
import org.SprintForge.modules.workspace.workspace.security.token.repository.WorkspaceApiTokenRepository;
import org.SprintForge.modules.workspace.workspace.validation.ApiTokenValidator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkspaceApiTokenServiceImpl implements WorkspaceApiTokenService {

    private final WorkspaceApiTokenRepository workspaceApiTokenRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ApiTokenValidator apiTokenValidator;

    @Override
    public ApiTokenCreatedResponse generateToken(Long workspaceId, CreateApiTokenRequest request, Long actorId) {
        String rawToken = UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "");
        String tokenHash = hashToken(rawToken);

        String perms = request.getPermissions() != null ? String.join(",", request.getPermissions()) : "";

        WorkspaceApiToken token = WorkspaceApiToken.builder()
                .workspaceId(workspaceId)
                .name(request.getName())
                .tokenHash(tokenHash)
                .permissions(perms)
                .createdById(actorId)
                .expiresAt(request.getExpiresAt())
                .isRevoked(false)
                .build();
        
        token = workspaceApiTokenRepository.save(token);
        
        eventPublisher.publishEvent(new ApiTokenCreatedEvent(token.getId(), workspaceId, actorId, LocalDateTime.now()));

        return mapToCreatedResponse(token, rawToken);
    }

    @Override
    public ApiTokenCreatedResponse rotateToken(Long workspaceId, Long tokenId, RotateApiTokenRequest request, Long actorId) {
        WorkspaceApiToken oldToken = workspaceApiTokenRepository.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
        if (!oldToken.getWorkspaceId().equals(workspaceId) || oldToken.isDeleted()) {
            throw new ResourceNotFoundException("Token not found in workspace");
        }

        oldToken.setIsRevoked(true);
        workspaceApiTokenRepository.save(oldToken);
        eventPublisher.publishEvent(new ApiTokenRevokedEvent(oldToken.getId(), workspaceId, actorId, LocalDateTime.now()));

        CreateApiTokenRequest createReq = CreateApiTokenRequest.builder()
                .name(request.getName() != null ? request.getName() : oldToken.getName())
                .permissions(oldToken.getPermissions() != null && !oldToken.getPermissions().isEmpty() ? Arrays.asList(oldToken.getPermissions().split(",")) : List.of())
                .expiresAt(oldToken.getExpiresAt())
                .build();

        return generateToken(workspaceId, createReq, actorId);
    }

    @Override
    public void revokeToken(Long workspaceId, Long tokenId, Long actorId) {
        WorkspaceApiToken token = workspaceApiTokenRepository.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
        if (!token.getWorkspaceId().equals(workspaceId) || token.isDeleted()) {
            throw new ResourceNotFoundException("Token not found in workspace");
        }
        token.setIsRevoked(true);
        workspaceApiTokenRepository.save(token);
        eventPublisher.publishEvent(new ApiTokenRevokedEvent(token.getId(), workspaceId, actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApiTokenResponse> listTokens(Long workspaceId, Long actorId) {
        return workspaceApiTokenRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ApiTokenResponse getToken(Long workspaceId, Long tokenId) {
        WorkspaceApiToken token = workspaceApiTokenRepository.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
        if (!token.getWorkspaceId().equals(workspaceId) || token.isDeleted()) {
            throw new ResourceNotFoundException("Token not found in workspace");
        }
        return mapToResponse(token);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateToken(String rawToken) {
        String tokenHash = hashToken(rawToken);
        return workspaceApiTokenRepository.findByTokenHashAndIsDeletedFalse(tokenHash)
                .map(t -> {
                    try {
                        apiTokenValidator.validateNotRevoked(t);
                        apiTokenValidator.validateNotExpired(t);
                        return true;
                    } catch (BusinessRuleException e) {
                        return false;
                    }
                }).orElse(false);
    }

    private String hashToken(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(rawToken.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    private ApiTokenResponse mapToResponse(WorkspaceApiToken token) {
        return ApiTokenResponse.builder()
                .id(token.getId())
                .workspaceId(token.getWorkspaceId())
                .name(token.getName())
                .permissions(token.getPermissions() != null && !token.getPermissions().isEmpty() ? Arrays.asList(token.getPermissions().split(",")) : List.of())
                .expiresAt(token.getExpiresAt())
                .lastUsedAt(token.getLastUsedAt())
                .isRevoked(token.getIsRevoked())
                .createdAt(token.getCreatedAt())
                .build();
    }

    private ApiTokenCreatedResponse mapToCreatedResponse(WorkspaceApiToken token, String rawToken) {
        return ApiTokenCreatedResponse.builder()
                .id(token.getId())
                .workspaceId(token.getWorkspaceId())
                .name(token.getName())
                .permissions(token.getPermissions() != null && !token.getPermissions().isEmpty() ? Arrays.asList(token.getPermissions().split(",")) : List.of())
                .expiresAt(token.getExpiresAt())
                .lastUsedAt(token.getLastUsedAt())
                .isRevoked(token.getIsRevoked())
                .createdAt(token.getCreatedAt())
                .rawToken(rawToken)
                .note("Store this token securely. It will not be shown again.")
                .build();
    }
}
