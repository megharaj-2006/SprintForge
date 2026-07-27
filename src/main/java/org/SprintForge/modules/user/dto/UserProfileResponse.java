package org.SprintForge.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.auth.entity.AuthProvider;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String displayName;
    private String bio;
    private String profilePicture;
    private String avatar;
    private AuthProvider provider;
    private boolean emailVerified;
    private boolean isSuspended;
    private boolean isDeleted;
    private String roleName;
    private List<String> roles;
    private UserPreferenceResponse preferences;
    private UserStatisticsResponse stats;
    private LocalDateTime lastLogin;
    private LocalDateTime lastActiveAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
