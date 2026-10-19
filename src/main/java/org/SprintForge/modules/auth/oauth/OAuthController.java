package org.SprintForge.modules.auth.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.auth.dto.JwtResponse;
import org.SprintForge.modules.auth.entity.AuthProvider;
import org.SprintForge.modules.auth.exception.AuthException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/config")
    public ResponseEntity<OAuthPublicConfigResponse> getOAuthPublicConfig(
            @RequestParam(value = "redirect_uri", required = false) String redirectUri
    ) {
        return ResponseEntity.ok(oAuthService.getPublicConfig(redirectUri));
    }

    @PostMapping("/{provider}")
    public ResponseEntity<JwtResponse> authenticateWithOAuth(
            @PathVariable("provider") String providerStr,
            @Valid @RequestBody OAuthTokenExchangeRequest request,
            @RequestHeader(value = "User-Agent", required = false) String userAgent,
            HttpServletRequest httpServletRequest
    ) {
        AuthProvider provider;
        try {
            provider = AuthProvider.valueOf(providerStr.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new AuthException("Unsupported OAuth provider: " + providerStr);
        }

        String ipAddress = httpServletRequest.getRemoteAddr();
        JwtResponse jwtResponse = oAuthService.authenticateWithOAuth(provider, request, userAgent, ipAddress);
        return ResponseEntity.ok(jwtResponse);
    }
}
