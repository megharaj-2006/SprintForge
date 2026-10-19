package org.SprintForge.modules.auth.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.auth.dto.JwtResponse;
import org.SprintForge.modules.auth.entity.AuthProvider;
import org.SprintForge.modules.auth.entity.RefreshToken;
import org.SprintForge.modules.auth.exception.AuthException;
import org.SprintForge.modules.auth.repository.RefreshTokenRepository;
import org.SprintForge.modules.auth.service.JwtService;
import org.SprintForge.modules.auth.service.PasswordService;
import org.SprintForge.modules.user.entity.Role;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.RoleRepository;
import org.SprintForge.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthServiceImpl implements OAuthService {

    private final List<OAuthClient> oAuthClients;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final PasswordService passwordService;

    @Override
    public OAuthPublicConfigResponse getPublicConfig(String redirectUri) {
        Map<String, OAuthPublicConfigResponse.ProviderInfo> providerMap = new LinkedHashMap<>();

        for (OAuthClient client : oAuthClients) {
            String key = client.getProvider().name().toLowerCase();
            boolean configured = client.isConfigured();
            String clientId = configured ? client.getClientId() : "";
            String scope = configured ? client.getScope() : "";
            String authUrl = (configured && redirectUri != null && !redirectUri.isBlank())
                    ? client.getAuthorizeUrl(redirectUri, UUID.randomUUID().toString())
                    : "";

            providerMap.put(key, OAuthPublicConfigResponse.ProviderInfo.builder()
                    .enabled(configured)
                    .clientId(clientId)
                    .authorizeUrl(authUrl)
                    .scope(scope)
                    .build());
        }

        return new OAuthPublicConfigResponse(providerMap);
    }

    @Override
    @Transactional
    public JwtResponse authenticateWithOAuth(
            AuthProvider provider,
            OAuthTokenExchangeRequest request,
            String userAgent,
            String ipAddress
    ) {
        OAuthClient client = oAuthClients.stream()
                .filter(c -> c.getProvider() == provider)
                .findFirst()
                .orElseThrow(() -> new AuthException("Unsupported OAuth provider: " + provider));

        if (!client.isConfigured()) {
            throw new AuthException("OAuth provider " + provider + " is not configured on this server");
        }

        log.info("Initiating OAuth code exchange with provider: {}", provider);
        OAuthUserInfo userInfo = client.exchangeCodeAndFetchProfile(
                request.getCode(),
                request.getRedirectUri(),
                request.getCodeVerifier()
        );

        if (userInfo.email() == null && userInfo.providerId() == null) {
            throw new AuthException("Failed to obtain user identity from " + provider);
        }

        User user = resolveOrCreateUser(userInfo, provider);
        user.setLastLogin(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        // Issue SprintForge Refresh Token
        RefreshToken refreshToken = RefreshToken.builder()
                .user(savedUser)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(7, ChronoUnit.DAYS))
                .userAgent(userAgent != null ? userAgent : "OAuth 2.0 Web Client")
                .ipAddress(ipAddress != null ? ipAddress : "127.0.0.1")
                .lastUsed(Instant.now())
                .build();
        refreshTokenRepository.save(refreshToken);

        // Issue SprintForge Access Token (JWT)
        String jwt = jwtService.generateToken(savedUser.getUsername(), refreshToken.getToken());

        log.info("Successfully authenticated OAuth user: username={}, provider={}", savedUser.getUsername(), provider);

        return JwtResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken.getToken())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .role(savedUser.getRole() != null ? savedUser.getRole().getName() : "DEVELOPER")
                .build();
    }

    private User resolveOrCreateUser(OAuthUserInfo userInfo, AuthProvider provider) {
        // 1. Try finding existing user by provider and providerId
        if (userInfo.providerId() != null && !userInfo.providerId().isBlank()) {
            Optional<User> byProvider = userRepository.findByProviderAndProviderId(provider, userInfo.providerId());
            if (byProvider.isPresent()) {
                User user = byProvider.get();
                hydrateProfileIfEmpty(user, userInfo);
                return user;
            }
        }

        // 2. Try finding existing user by verified Email
        if (userInfo.email() != null && !userInfo.email().isBlank()) {
            Optional<User> byEmail = userRepository.findByEmail(userInfo.email());
            if (byEmail.isPresent()) {
                User user = byEmail.get();
                // Link external provider identity
                user.setProvider(provider);
                user.setProviderId(userInfo.providerId());
                hydrateProfileIfEmpty(user, userInfo);
                return user;
            }
        }

        // 3. Create a brand new local User account
        Role defaultRole = roleRepository.findByName("DEVELOPER")
                .orElseGet(() -> roleRepository.save(
                        Role.builder()
                                .name("DEVELOPER")
                                .description("Default Developer Role")
                                .build()
                ));

        String username = generateUniqueUsername(userInfo);

        User newUser = User.builder()
                .username(username)
                .email(userInfo.email() != null ? userInfo.email() : username + "@oauth.sprintforge.dev")
                .fullName(userInfo.name() != null ? userInfo.name() : username)
                .displayName(userInfo.name() != null ? userInfo.name() : username)
                .profilePicture(userInfo.avatarUrl())
                .bio(userInfo.bio())
                .headline(userInfo.headline())
                .location(userInfo.location())
                .websiteUrl(userInfo.websiteUrl())
                .githubUrl(userInfo.githubUrl())
                .linkedinUrl(userInfo.linkedinUrl())
                .provider(provider)
                .providerId(userInfo.providerId())
                .emailVerified(true)
                .isSuspended(false)
                .role(defaultRole)
                .password(passwordService.encode(UUID.randomUUID().toString()))
                .build();

        return newUser;
    }

    private void hydrateProfileIfEmpty(User user, OAuthUserInfo userInfo) {
        if ((user.getProfilePicture() == null || user.getProfilePicture().isBlank()) && userInfo.avatarUrl() != null) {
            user.setProfilePicture(userInfo.avatarUrl());
        }
        if ((user.getBio() == null || user.getBio().isBlank()) && userInfo.bio() != null) {
            user.setBio(userInfo.bio());
        }
        if ((user.getHeadline() == null || user.getHeadline().isBlank()) && userInfo.headline() != null) {
            user.setHeadline(userInfo.headline());
        }
        if ((user.getLocation() == null || user.getLocation().isBlank()) && userInfo.location() != null) {
            user.setLocation(userInfo.location());
        }
        if ((user.getGithubUrl() == null || user.getGithubUrl().isBlank()) && userInfo.githubUrl() != null) {
            user.setGithubUrl(userInfo.githubUrl());
        }
        if ((user.getLinkedinUrl() == null || user.getLinkedinUrl().isBlank()) && userInfo.linkedinUrl() != null) {
            user.setLinkedinUrl(userInfo.linkedinUrl());
        }
        if ((user.getWebsiteUrl() == null || user.getWebsiteUrl().isBlank()) && userInfo.websiteUrl() != null) {
            user.setWebsiteUrl(userInfo.websiteUrl());
        }
        if ((user.getFullName() == null || user.getFullName().isBlank()) && userInfo.name() != null) {
            user.setFullName(userInfo.name());
        }
    }

    private String generateUniqueUsername(OAuthUserInfo userInfo) {
        String base = null;
        if (userInfo.username() != null && !userInfo.username().isBlank()) {
            base = sanitizeUsername(userInfo.username());
        } else if (userInfo.name() != null && !userInfo.name().isBlank()) {
            base = sanitizeUsername(userInfo.name());
        } else if (userInfo.email() != null && userInfo.email().contains("@")) {
            base = sanitizeUsername(userInfo.email().substring(0, userInfo.email().indexOf('@')));
        }

        if (base == null || base.length() < 3) {
            base = "user" + (1000 + new Random().nextInt(9000));
        }

        String candidate = base;
        int attempt = 1;
        while (userRepository.existsByUsername(candidate)) {
            candidate = base + (100 + attempt++);
        }
        return candidate;
    }

    private String sanitizeUsername(String input) {
        return input.trim().toLowerCase()
                .replaceAll("[^a-z0-9_-]", "")
                .replaceAll("^-+|-+$", "");
    }
}
