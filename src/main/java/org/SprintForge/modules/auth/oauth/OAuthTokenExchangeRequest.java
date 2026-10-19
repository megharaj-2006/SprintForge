package org.SprintForge.modules.auth.oauth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuthTokenExchangeRequest {

    @NotBlank(message = "Authorization code is required")
    private String code;

    private String redirectUri;
    private String codeVerifier;
}
