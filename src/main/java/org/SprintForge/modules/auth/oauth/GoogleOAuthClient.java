package org.SprintForge.modules.auth.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.auth.entity.AuthProvider;
import org.SprintForge.modules.auth.exception.AuthException;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleOAuthClient implements OAuthClient {

    private final OAuthProperties oAuthProperties;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static final String TOKEN_URL = "https://oauth2.googleapis.com/token";
    private static final String USERINFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";
    private static final String AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String SCOPE = "openid email profile";

    @Override
    public AuthProvider getProvider() {
        return AuthProvider.GOOGLE;
    }

    @Override
    public boolean isConfigured() {
        return oAuthProperties.getGoogle().isConfigured();
    }

    @Override
    public String getClientId() {
        return oAuthProperties.getGoogle().getClientId();
    }

    @Override
    public String getScope() {
        return SCOPE;
    }

    @Override
    public String getAuthorizeUrl(String redirectUri, String state) {
        return AUTH_URL + "?client_id=" + URLEncoder.encode(getClientId(), StandardCharsets.UTF_8)
                + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
                + "&response_type=code"
                + "&scope=" + URLEncoder.encode(SCOPE, StandardCharsets.UTF_8)
                + "&access_type=offline"
                + "&prompt=consent"
                + (state != null ? "&state=" + URLEncoder.encode(state, StandardCharsets.UTF_8) : "");
    }

    @Override
    public OAuthUserInfo exchangeCodeAndFetchProfile(String code, String redirectUri, String codeVerifier) {
        try {
            String formBody = "code=" + URLEncoder.encode(code, StandardCharsets.UTF_8)
                    + "&client_id=" + URLEncoder.encode(oAuthProperties.getGoogle().getClientId(), StandardCharsets.UTF_8)
                    + "&client_secret=" + URLEncoder.encode(oAuthProperties.getGoogle().getClientSecret(), StandardCharsets.UTF_8)
                    + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
                    + "&grant_type=authorization_code";

            HttpRequest tokenRequest = HttpRequest.newBuilder()
                    .uri(URI.create(TOKEN_URL))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .timeout(Duration.ofSeconds(15))
                    .POST(HttpRequest.BodyPublishers.ofString(formBody))
                    .build();

            HttpResponse<String> tokenResponse = httpClient.send(tokenRequest, HttpResponse.BodyHandlers.ofString());

            if (tokenResponse.statusCode() >= 400) {
                log.error("Google token exchange failed: status={}, body={}", tokenResponse.statusCode(), tokenResponse.body());
                throw new AuthException("Failed to authenticate with Google: " + tokenResponse.body());
            }

            JsonNode tokenJson = objectMapper.readTree(tokenResponse.body());
            String accessToken = tokenJson.path("access_token").asText();

            if (accessToken == null || accessToken.isBlank()) {
                throw new AuthException("No access token returned from Google");
            }

            // Fetch User Claims from Google UserInfo
            HttpRequest userinfoRequest = HttpRequest.newBuilder()
                    .uri(URI.create(USERINFO_URL))
                    .header("Authorization", "Bearer " + accessToken)
                    .timeout(Duration.ofSeconds(15))
                    .GET()
                    .build();

            HttpResponse<String> userinfoResponse = httpClient.send(userinfoRequest, HttpResponse.BodyHandlers.ofString());

            if (userinfoResponse.statusCode() >= 400) {
                log.error("Google userinfo fetch failed: status={}, body={}", userinfoResponse.statusCode(), userinfoResponse.body());
                throw new AuthException("Failed to fetch Google user profile: " + userinfoResponse.body());
            }

            JsonNode userJson = objectMapper.readTree(userinfoResponse.body());
            String providerId = userJson.path("sub").asText();
            String email = userJson.path("email").asText();
            String name = userJson.path("name").asText(null);
            String picture = userJson.path("picture").asText(null);

            return OAuthUserInfo.builder()
                    .provider(AuthProvider.GOOGLE)
                    .providerId(providerId)
                    .email(email)
                    .name(name)
                    .avatarUrl(picture)
                    .build();

        } catch (AuthException ae) {
            throw ae;
        } catch (Exception e) {
            log.error("Exception during Google OAuth exchange", e);
            throw new AuthException("Google authentication error: " + e.getMessage());
        }
    }
}
