package org.SprintForge.modules.auth.oauth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sprintforge.oauth")
@Data
public class OAuthProperties {

    private ProviderConfig google = new ProviderConfig();
    private ProviderConfig github = new ProviderConfig();
    private ProviderConfig linkedin = new ProviderConfig();
    private MicrosoftProviderConfig microsoft = new MicrosoftProviderConfig();

    @Data
    public static class ProviderConfig {
        private String clientId = "";
        private String clientSecret = "";

        public boolean isConfigured() {
            return clientId != null && !clientId.isBlank() && clientSecret != null && !clientSecret.isBlank();
        }
    }

    @Data
    @lombok.EqualsAndHashCode(callSuper = false)
    public static class MicrosoftProviderConfig extends ProviderConfig {
        private String tenantId = "common";
    }
}
