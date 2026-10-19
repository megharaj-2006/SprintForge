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
public class GitHubOAuthClient implements OAuthClient {

    private final OAuthProperties oAuthProperties;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static final String TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String USER_URL = "https://api.github.com/user";
    private static final String EMAILS_URL = "https://api.github.com/user/emails";
    private static final String AUTH_URL = "https://github.com/login/oauth/authorize";
    private static final String SCOPE = "read:user user:email";

    @Override
    public AuthProvider getProvider() {
        return AuthProvider.GITHUB;
    }

    @Override
    public boolean isConfigured() {
        return oAuthProperties.getGithub().isConfigured();
    }

    @Override
    public String getClientId() {
        return oAuthProperties.getGithub().getClientId();
    }

    @Override
    public String getScope() {
        return SCOPE;
    }

    @Override
    public String getAuthorizeUrl(String redirectUri, String state) {
        return AUTH_URL + "?client_id=" + URLEncoder.encode(getClientId(), StandardCharsets.UTF_8)
                + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
                + "&scope=" + URLEncoder.encode(SCOPE, StandardCharsets.UTF_8)
                + (state != null ? "&state=" + URLEncoder.encode(state, StandardCharsets.UTF_8) : "");
    }

    @Override
    public OAuthUserInfo exchangeCodeAndFetchProfile(String code, String redirectUri, String codeVerifier) {
        try {
            String formBody = "code=" + URLEncoder.encode(code, StandardCharsets.UTF_8)
                    + "&client_id=" + URLEncoder.encode(oAuthProperties.getGithub().getClientId(), StandardCharsets.UTF_8)
                    + "&client_secret=" + URLEncoder.encode(oAuthProperties.getGithub().getClientSecret(), StandardCharsets.UTF_8)
                    + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);

            HttpRequest tokenRequest = HttpRequest.newBuilder()
                    .uri(URI.create(TOKEN_URL))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(15))
                    .POST(HttpRequest.BodyPublishers.ofString(formBody))
                    .build();

            HttpResponse<String> tokenResponse = httpClient.send(tokenRequest, HttpResponse.BodyHandlers.ofString());

            if (tokenResponse.statusCode() >= 400) {
                log.error("GitHub token exchange failed: status={}, body={}", tokenResponse.statusCode(), tokenResponse.body());
                throw new AuthException("Failed to authenticate with GitHub: " + tokenResponse.body());
            }

            JsonNode tokenJson = objectMapper.readTree(tokenResponse.body());
            String accessToken = tokenJson.path("access_token").asText();

            if (accessToken == null || accessToken.isBlank()) {
                String errorDescription = tokenJson.path("error_description").asText("No access token returned from GitHub");
                throw new AuthException("GitHub authentication error: " + errorDescription);
            }

            // Fetch GitHub Profile
            HttpRequest userRequest = HttpRequest.newBuilder()
                    .uri(URI.create(USER_URL))
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Accept", "application/vnd.github.v3+json")
                    .header("User-Agent", "SprintForge-App")
                    .timeout(Duration.ofSeconds(15))
                    .GET()
                    .build();

            HttpResponse<String> userResponse = httpClient.send(userRequest, HttpResponse.BodyHandlers.ofString());

            if (userResponse.statusCode() >= 400) {
                log.error("GitHub user fetch failed: status={}, body={}", userResponse.statusCode(), userResponse.body());
                throw new AuthException("Failed to fetch GitHub profile: " + userResponse.body());
            }

            JsonNode userJson = objectMapper.readTree(userResponse.body());
            String providerId = userJson.path("id").asText();
            String login = userJson.path("login").asText();
            String name = userJson.hasNonNull("name") ? userJson.path("name").asText() : login;
            String avatarUrl = userJson.hasNonNull("avatar_url") ? userJson.path("avatar_url").asText() : null;
            String bio = userJson.hasNonNull("bio") ? userJson.path("bio").asText() : null;
            String location = userJson.hasNonNull("location") ? userJson.path("location").asText() : null;
            String blog = userJson.hasNonNull("blog") ? userJson.path("blog").asText() : null;
            String htmlUrl = userJson.hasNonNull("html_url") ? userJson.path("html_url").asText() : "https://github.com/" + login;
            String company = userJson.hasNonNull("company") ? userJson.path("company").asText() : null;

            String email = userJson.hasNonNull("email") ? userJson.path("email").asText() : null;

            // If email is private on GitHub profile, fetch from /user/emails
            if (email == null || email.isBlank()) {
                HttpRequest emailsRequest = HttpRequest.newBuilder()
                        .uri(URI.create(EMAILS_URL))
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Accept", "application/vnd.github.v3+json")
                        .header("User-Agent", "SprintForge-App")
                        .timeout(Duration.ofSeconds(10))
                        .GET()
                        .build();

                HttpResponse<String> emailsResponse = httpClient.send(emailsRequest, HttpResponse.BodyHandlers.ofString());
                if (emailsResponse.statusCode() < 400) {
                    JsonNode emailsJson = objectMapper.readTree(emailsResponse.body());
                    if (emailsJson.isArray()) {
                        for (JsonNode emailNode : emailsJson) {
                            if (emailNode.path("primary").asBoolean(false) && emailNode.path("verified").asBoolean(false)) {
                                email = emailNode.path("email").asText();
                                break;
                            }
                        }
                        if (email == null && emailsJson.size() > 0) {
                            email = emailsJson.get(0).path("email").asText();
                        }
                    }
                }
            }

            if (email == null || email.isBlank()) {
                email = login + "@users.noreply.github.com";
            }

            return OAuthUserInfo.builder()
                    .provider(AuthProvider.GITHUB)
                    .providerId(providerId)
                    .username(login)
                    .email(email)
                    .name(name)
                    .avatarUrl(avatarUrl)
                    .bio(bio)
                    .location(location)
                    .websiteUrl(blog)
                    .githubUrl(htmlUrl)
                    .headline(company != null ? "Developer at " + company : "GitHub Engineer")
                    .build();

        } catch (AuthException ae) {
            throw ae;
        } catch (Exception e) {
            log.error("Exception during GitHub OAuth exchange", e);
            throw new AuthException("GitHub authentication error: " + e.getMessage());
        }
    }
}
