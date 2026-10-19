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
public class MicrosoftOAuthClient implements OAuthClient {

    private final OAuthProperties oAuthProperties;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static final String MS_GRAPH_ME = "https://graph.microsoft.com/v1.0/me";
    private static final String SCOPE = "openid profile email User.Read";

    private String getTenant() {
        String tenant = oAuthProperties.getMicrosoft().getTenantId();
        return (tenant == null || tenant.isBlank()) ? "common" : tenant;
    }

    private String getTokenUrl() {
        return "https://login.microsoftonline.com/" + getTenant() + "/oauth2/v2.0/token";
    }

    private String getAuthUrl() {
        return "https://login.microsoftonline.com/" + getTenant() + "/oauth2/v2.0/authorize";
    }

    @Override
    public AuthProvider getProvider() {
        return AuthProvider.MICROSOFT;
    }

    @Override
    public boolean isConfigured() {
        return oAuthProperties.getMicrosoft().isConfigured();
    }

    @Override
    public String getClientId() {
        return oAuthProperties.getMicrosoft().getClientId();
    }

    @Override
    public String getScope() {
        return SCOPE;
    }

    @Override
    public String getAuthorizeUrl(String redirectUri, String state) {
        return getAuthUrl() + "?client_id=" + URLEncoder.encode(getClientId(), StandardCharsets.UTF_8)
                + "&response_type=code"
                + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
                + "&response_mode=query"
                + "&scope=" + URLEncoder.encode(SCOPE, StandardCharsets.UTF_8)
                + (state != null ? "&state=" + URLEncoder.encode(state, StandardCharsets.UTF_8) : "");
    }

    @Override
    public OAuthUserInfo exchangeCodeAndFetchProfile(String code, String redirectUri, String codeVerifier) {
        try {
            String formBody = "client_id=" + URLEncoder.encode(oAuthProperties.getMicrosoft().getClientId(), StandardCharsets.UTF_8)
                    + "&scope=" + URLEncoder.encode(SCOPE, StandardCharsets.UTF_8)
                    + "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8)
                    + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
                    + "&grant_type=authorization_code"
                    + "&client_secret=" + URLEncoder.encode(oAuthProperties.getMicrosoft().getClientSecret(), StandardCharsets.UTF_8);

            HttpRequest tokenRequest = HttpRequest.newBuilder()
                    .uri(URI.create(getTokenUrl()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .timeout(Duration.ofSeconds(15))
                    .POST(HttpRequest.BodyPublishers.ofString(formBody))
                    .build();

            HttpResponse<String> tokenResponse = httpClient.send(tokenRequest, HttpResponse.BodyHandlers.ofString());

            if (tokenResponse.statusCode() >= 400) {
                log.error("Microsoft token exchange failed: status={}, body={}", tokenResponse.statusCode(), tokenResponse.body());
                throw new AuthException("Failed to authenticate with Microsoft: " + tokenResponse.body());
            }

            JsonNode tokenJson = objectMapper.readTree(tokenResponse.body());
            String accessToken = tokenJson.path("access_token").asText();

            if (accessToken == null || accessToken.isBlank()) {
                throw new AuthException("No access token returned from Microsoft");
            }

            // Fetch Profile via Microsoft Graph
            HttpRequest userinfoRequest = HttpRequest.newBuilder()
                    .uri(URI.create(MS_GRAPH_ME))
                    .header("Authorization", "Bearer " + accessToken)
                    .timeout(Duration.ofSeconds(15))
                    .GET()
                    .build();

            HttpResponse<String> userinfoResponse = httpClient.send(userinfoRequest, HttpResponse.BodyHandlers.ofString());

            if (userinfoResponse.statusCode() >= 400) {
                log.error("Microsoft Graph /me fetch failed: status={}, body={}", userinfoResponse.statusCode(), userinfoResponse.body());
                throw new AuthException("Failed to fetch Microsoft profile: " + userinfoResponse.body());
            }

            JsonNode userJson = objectMapper.readTree(userinfoResponse.body());
            String providerId = userJson.path("id").asText();
            String displayName = userJson.hasNonNull("displayName") ? userJson.path("displayName").asText() : null;
            String email = userJson.hasNonNull("mail") && !userJson.path("mail").asText().isBlank()
                    ? userJson.path("mail").asText()
                    : userJson.path("userPrincipalName").asText();
            String jobTitle = userJson.hasNonNull("jobTitle") ? userJson.path("jobTitle").asText() : null;
            String officeLocation = userJson.hasNonNull("officeLocation") ? userJson.path("officeLocation").asText() : null;

            return OAuthUserInfo.builder()
                    .provider(AuthProvider.MICROSOFT)
                    .providerId(providerId)
                    .email(email)
                    .name(displayName)
                    .headline(jobTitle)
                    .location(officeLocation)
                    .build();

        } catch (AuthException ae) {
            throw ae;
        } catch (Exception e) {
            log.error("Exception during Microsoft OAuth exchange", e);
            throw new AuthException("Microsoft authentication error: " + e.getMessage());
        }
    }
}
