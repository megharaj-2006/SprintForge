package org.SprintForge.modules.workspace.workspace.security.token.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiTokenCreatedResponse {
    private Long id;
    private Long workspaceId;
    private String name;
    private List<String> permissions;
    private LocalDateTime expiresAt;
    private LocalDateTime lastUsedAt;
    private Boolean isRevoked;
    private LocalDateTime createdAt;
    
    private String rawToken;
    @Builder.Default
    private String note = "Store this token securely. It will not be shown again.";
}
