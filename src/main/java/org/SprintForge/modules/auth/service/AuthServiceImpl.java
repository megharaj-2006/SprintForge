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
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public SignupResponse signup(SignupRequest request, String userAgent, String ipAddress) {
        var existingUserOpt = userRepository.findByEmail(request.getEmail().trim().toLowerCase());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            if (existingUser.isEmailVerified()) {
                throw new AuthException("This email address is already in use. Please sign in instead.");
            }
            // User registered previously but has not verified their email yet.
            // Update password & username, generate fresh OTP, and resend verification email.
            existingUser.setUsername(request.getUsername().trim());
            existingUser.setPassword(passwordService.encode(request.getPassword()));
            User savedUser = userRepository.save(existingUser);

            String code = generate6DigitCode();
            createOrUpdateVerificationToken(savedUser, code);
            emailService.sendVerificationEmail(savedUser.getEmail(), savedUser.getUsername(), code);

            return SignupResponse.builder()
                    .verificationRequired(true)
                    .email(savedUser.getEmail())
                    .message("A new verification code has been sent to your email. Please verify to complete registration.")
                    .build();
        }

        if (userRepository.existsByUsername(request.getUsername().trim())) {
            throw new AuthException("Username is already taken");
        }

        Role defaultRole = roleRepository.findByName("DEVELOPER")
                .orElseGet(() -> roleRepository.save(
                        Role.builder()
                                .name("DEVELOPER")
                                .description("Default Developer Role")
                                .build()
                ));

        User user = User.builder()
                .username(request.getUsername().trim())
                .email(request.getEmail().trim().toLowerCase())
                .password(passwordService.encode(request.getPassword()))
                .provider(AuthProvider.LOCAL)
                .emailVerified(false)
                .role(defaultRole)
                .build();

        User savedUser = userRepository.save(user);

        String code = generate6DigitCode();
        createOrUpdateVerificationToken(savedUser, code);
        emailService.sendVerificationEmail(savedUser.getEmail(), savedUser.getUsername(), code);

        return SignupResponse.builder()
                .verificationRequired(true)
                .email(savedUser.getEmail())
                .message("Verification code has been sent to your email. Please verify your email to complete registration.")
                .build();
    }

    @Override
    @Transactional
    public JwtResponse verifyEmail(VerifyEmailRequest request, String userAgent, String ipAddress) {
        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new AuthException("No account found with this email address"));

        if (user.isEmailVerified()) {
            RefreshToken refreshToken = createRefreshToken(user, userAgent, ipAddress);
            String jwt = jwtService.generateToken(user.getUsername(), refreshToken.getToken());
            return JwtResponse.builder()
                    .token(jwt)
                    .refreshToken(refreshToken.getToken())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .role(user.getRole() != null ? user.getRole().getName() : null)
                    .build();
        }

        EmailVerificationToken token = emailVerificationTokenRepository.findByUser(user)
                .orElseThrow(() -> new AuthException("No pending verification found. Please request a new code."));

        if (token.getExpiryDate().isBefore(Instant.now())) {
            throw new AuthException("Verification code has expired. Please request a new code.");
        }

        if (!token.getToken().equals(request.getCode().trim())) {
            throw new AuthException("Invalid verification code. Please check your email and try again.");
        }

        user.setEmailVerified(true);
        User savedUser = userRepository.save(user);
        emailVerificationTokenRepository.delete(token);

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
    public void resendVerification(ResendVerificationRequest request) {
        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new AuthException("No account found with this email address"));

        if (user.isEmailVerified()) {
            throw new AuthException("This email address is already verified. Please sign in.");
        }

        String code = generate6DigitCode();
        createOrUpdateVerificationToken(user, code);
        emailService.sendVerificationEmail(user.getEmail(), user.getUsername(), code);
    }


    @Override
    @Transactional
    public JwtResponse login(LoginRequest request, String userAgent, String ipAddress) {
        var userOpt = userRepository.findByUsername(request.getUsernameOrEmail())
                .or(() -> userRepository.findByEmail(request.getUsernameOrEmail()));
        if (userOpt.isPresent() && !userOpt.get().isEmailVerified()) {
            throw new AuthException("Email address not verified. Please verify your email before logging in.");
        }

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
        var userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            log.info("Password reset requested for non-existent email (enumeration mitigated).");
            return;
        }

        User user = userOpt.get();
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .expiryDate(Instant.now().plus(1, ChronoUnit.HOURS))
                .user(user)
                .build();

        passwordResetTokenRepository.save(resetToken);
        log.info("Password reset token generated for user ID: {}", user.getId());
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
        // Revoke all existing sessions upon password reset
        refreshTokenRepository.deleteByUser(user);
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

        // Revoke all existing sessions upon password change
        refreshTokenRepository.deleteByUser(user);
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

    private String generate6DigitCode() {
        return String.format("%06d", java.util.concurrent.ThreadLocalRandom.current().nextInt(100000, 1000000));
    }

    private void createOrUpdateVerificationToken(User user, String code) {
        EmailVerificationToken token = emailVerificationTokenRepository.findByUser(user)
                .orElse(EmailVerificationToken.builder()
                        .user(user)
                        .build());
        token.setToken(code);
        token.setExpiryDate(Instant.now().plus(15, ChronoUnit.MINUTES));
        emailVerificationTokenRepository.save(token);
    }
}