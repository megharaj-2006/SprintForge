package org.SprintForge.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionResponse {
    private Long id;
    private Long userId;
    private String ipAddress;
    private String userAgent;
    private String deviceType;
    private String browser;
    private String os;
    private String location;
    private Boolean isCurrent;
    private Boolean isActive;
    private LocalDateTime lastActivityAt;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}
