package org.SprintForge.modules.auth.oauth;

import lombok.Builder;
import org.SprintForge.modules.auth.entity.AuthProvider;

@Builder
public record OAuthUserInfo(
    String providerId,
    AuthProvider provider,
    String email,
    String name,
    String username,
    String avatarUrl,
    String bio,
    String headline,
    String location,
    String websiteUrl,
    String githubUrl,
    String linkedinUrl
) {}
