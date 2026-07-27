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
public class PublicUserProfileResponse {
    private Long id;
    private String username;
    private String fullName;
    private String displayName;
    private String bio;
    private String profilePicture;
    private String avatar;
    private String roleName;
    private LocalDateTime createdAt;
}
