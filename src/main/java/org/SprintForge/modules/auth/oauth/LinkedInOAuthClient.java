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
public class LinkedInOAuthClient implements OAuthClient {

    private final OAuthProperties oAuthProperties;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static final String TOKEN_URL = "https://www.linkedin.com/oauth/v2/accessToken";
    private static final String USERINFO_URL = "https://api.linkedin.com/v2/userinfo";
    private static final String AUTH_URL = "https://www.linkedin.com/oauth/v2/authorization";
    private static final String SCOPE = "openid profile email";

    @Override
    public AuthProvider getProvider() {
        return AuthProvider.LINKEDIN;
    }

    @Override
    public boolean isConfigured() {
        return oAuthProperties.getLinkedin().isConfigured();
    }

    @Override
    public String getClientId() {
        return oAuthProperties.getLinkedin().getClientId();
    }

    @Override
    public String getScope() {
        return SCOPE;
    }

    @Override
    public String getAuthorizeUrl(String redirectUri, String state) {
        return AUTH_URL + "?response_type=code"
                + "&client_id=" + URLEncoder.encode(getClientId(), StandardCharsets.UTF_8)
                + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
                + "&scope=" + URLEncoder.encode(SCOPE, StandardCharsets.UTF_8)
                + (state != null ? "&state=" + URLEncoder.encode(state, StandardCharsets.UTF_8) : "");
    }

    @Override
    public OAuthUserInfo exchangeCodeAndFetchProfile(String code, String redirectUri, String codeVerifier) {
        try {
            String formBody = "grant_type=authorization_code"
                    + "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8)
                    + "&client_id=" + URLEncoder.encode(oAuthProperties.getLinkedin().getClientId(), StandardCharsets.UTF_8)
                    + "&client_secret=" + URLEncoder.encode(oAuthProperties.getLinkedin().getClientSecret(), StandardCharsets.UTF_8)
                    + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);

            HttpRequest tokenRequest = HttpRequest.newBuilder()
                    .uri(URI.create(TOKEN_URL))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .timeout(Duration.ofSeconds(15))
                    .POST(HttpRequest.BodyPublishers.ofString(formBody))
                    .build();

            HttpResponse<String> tokenResponse = httpClient.send(tokenRequest, HttpResponse.BodyHandlers.ofString());

            if (tokenResponse.statusCode() >= 400) {
                log.error("LinkedIn token exchange failed: status={}, body={}", tokenResponse.statusCode(), tokenResponse.body());
                throw new AuthException("Failed to authenticate with LinkedIn: " + tokenResponse.body());
            }

            JsonNode tokenJson = objectMapper.readTree(tokenResponse.body());
            String accessToken = tokenJson.path("access_token").asText();

            if (accessToken == null || accessToken.isBlank()) {
                throw new AuthException("No access token returned from LinkedIn");
            }

            // Fetch LinkedIn OpenID UserInfo Profile
            HttpRequest userinfoRequest = HttpRequest.newBuilder()
                    .uri(URI.create(USERINFO_URL))
                    .header("Authorization", "Bearer " + accessToken)
                    .timeout(Duration.ofSeconds(15))
                    .GET()
                    .build();

            HttpResponse<String> userinfoResponse = httpClient.send(userinfoRequest, HttpResponse.BodyHandlers.ofString());

            if (userinfoResponse.statusCode() >= 400) {
                log.error("LinkedIn userinfo fetch failed: status={}, body={}", userinfoResponse.statusCode(), userinfoResponse.body());
                throw new AuthException("Failed to fetch LinkedIn user profile: " + userinfoResponse.body());
            }

            JsonNode userJson = objectMapper.readTree(userinfoResponse.body());
            String providerId = userJson.path("sub").asText();
            String name = userJson.hasNonNull("name") ? userJson.path("name").asText() : null;
            String email = userJson.hasNonNull("email") ? userJson.path("email").asText() : null;
            String picture = userJson.hasNonNull("picture") ? userJson.path("picture").asText() : null;

            return OAuthUserInfo.builder()
                    .provider(AuthProvider.LINKEDIN)
                    .providerId(providerId)
                    .email(email)
                    .name(name)
                    .avatarUrl(picture)
                    .headline("Professional on LinkedIn")
                    .build();

        } catch (AuthException ae) {
            throw ae;
        } catch (Exception e) {
            log.error("Exception during LinkedIn OAuth exchange", e);
            throw new AuthException("LinkedIn authentication error: " + e.getMessage());
        }
    }
}
