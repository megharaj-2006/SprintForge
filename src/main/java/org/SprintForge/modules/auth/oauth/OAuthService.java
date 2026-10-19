package org.SprintForge.modules.auth.oauth;

import org.SprintForge.modules.auth.dto.JwtResponse;
import org.SprintForge.modules.auth.entity.AuthProvider;

public interface OAuthService {

    OAuthPublicConfigResponse getPublicConfig(String redirectUri);

    JwtResponse authenticateWithOAuth(
            AuthProvider provider,
            OAuthTokenExchangeRequest request,
            String userAgent,
            String ipAddress
    );
}
