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
public class TrustedDeviceResponse {
    private Long id;
    private Long workspaceId;
    private Long userId;
    private String deviceFingerprint;
    private String deviceName;
    private String deviceType;
    private LocalDateTime trustedAt;
    private LocalDateTime lastSeenAt;
    private Boolean isActive;
}
