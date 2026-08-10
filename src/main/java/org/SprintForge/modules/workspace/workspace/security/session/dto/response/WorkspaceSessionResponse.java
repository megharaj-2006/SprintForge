package org.SprintForge.modules.workspace.workspace.security.session.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceSessionResponse {
    private Long id;
    private Long workspaceId;
    private Long userId;
    private String ipAddress;
    private String userAgent;
    private String deviceType;
    private String location;
    private Boolean isActive;
    private LocalDateTime lastActivityAt;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}
