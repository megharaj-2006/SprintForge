package org.SprintForge.modules.auth.oauth;

import lombok.Builder;
import java.util.Map;

@Builder
public record OAuthPublicConfigResponse(
    Map<String, ProviderInfo> providers
) {
    @Builder
    public record ProviderInfo(
        boolean enabled,
        String clientId,
        String authorizeUrl,
        String scope
    ) {}
}
