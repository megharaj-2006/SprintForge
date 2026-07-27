package org.SprintForge.modules.auth.service;

import org.SprintForge.modules.auth.dto.*;
import org.SprintForge.modules.auth.entity.*;
import org.SprintForge.modules.auth.exception.AuthException;
import org.SprintForge.modules.auth.repository.*;
import org.SprintForge.modules.auth.security.UserPrincipal;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.entity.Role;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.user.repository.RoleRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    
    private final PasswordService passwordService;
    private final JwtService jwtService;
    private final OtpService otpService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public JwtResponse signup(SignupRequest request, String userAgent, String ipAddress) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AuthException("Username is already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException("Email is already registered");
        }

        Role defaultRole = roleRepository.findByName("DEVELOPER")
                .orElseGet(() -> roleRepository.save(
                        Role.builder()
                                .name("DEVELOPER")
                                .description("Default Developer Role")
                                .build()
                ));

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordService.encode(request.getPassword()))
                .provider(AuthProvider.LOCAL)
                .emailVerified(true)
                .role(defaultRole)
                .build();

        User savedUser = userRepository.save(user);
        
        RefreshToken refreshToken = createRefreshToken(savedUser, userAgent, ipAddress);
        String jwt = jwtService.generateToken(savedUser.getUsername(), refreshToken.getToken());

        return JwtResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken.getToken())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().getName())
                .build();
    }

    @Override
    @Transactional
    public JwtResponse login(LoginRequest request, String userAgent, String ipAddress) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        User user = principal.getUser();
        
        user.setLastLogin(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        RefreshToken refreshToken = createRefreshToken(savedUser, userAgent, ipAddress);
        String jwt = jwtService.generateToken(savedUser.getUsername(), refreshToken.getToken());

        return JwtResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken.getToken())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .role(savedUser.getRole() != null ? savedUser.getRole().getName() : null)
                .build();
    }

    @Override
    @Transactional
    public JwtResponse refresh(RefreshRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new AuthException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new AuthException("Refresh token has expired. Please log in again.");
        }

        User user = refreshToken.getUser();
        
        refreshToken.setLastUsed(Instant.now());
        refreshTokenRepository.save(refreshToken);

        String newJwt = jwtService.generateToken(user.getUsername(), refreshToken.getToken());

        return JwtResponse.builder()
                .token(newJwt)
                .refreshToken(refreshToken.getToken())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole() != null ? user.getRole().getName() : null)
                .build();
    }

    @Override
    @Transactional
    public void logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            String jti = jwtService.getJwtIdFromToken(jwt);
            if (jti != null) {
                refreshTokenRepository.findByToken(jti).ifPresent(refreshTokenRepository::delete);
            }
        }
    }

    @Override
    @Transactional
    public void logoutAll(String username) {
        userRepository.findByUsername(username).ifPresent(refreshTokenRepository::deleteByUser);
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException("User not found with email: " + request.getEmail()));

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .expiryDate(Instant.now().plus(1, ChronoUnit.HOURS))
                .user(user)
                .build();

        passwordResetTokenRepository.save(resetToken);

        log.debug("Password reset token generated for user: {} - Token: {}", user.getUsername(), token);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new AuthException("Invalid reset password token"));

        if (resetToken.getExpiryDate().isBefore(Instant.now())) {
            passwordResetTokenRepository.delete(resetToken);
            throw new AuthException("Reset password token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordService.encode(request.getNewPassword()));
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }

    @Override
    @Transactional
    public void changePassword(String username, ChangePasswordRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException("User not found"));

        if (!passwordService.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new AuthException("Incorrect current password");
        }

        user.setPassword(passwordService.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    // Cron job to run daily at 2 AM to clean up expired refresh tokens
    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void cleanExpiredTokens() {
        refreshTokenRepository.deleteByExpiryDateBefore(Instant.now());
    }

    private RefreshToken createRefreshToken(User user, String userAgent, String ipAddress) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(7, ChronoUnit.DAYS))
                .userAgent(userAgent)
                .ipAddress(ipAddress)
                .lastUsed(Instant.now())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }
}