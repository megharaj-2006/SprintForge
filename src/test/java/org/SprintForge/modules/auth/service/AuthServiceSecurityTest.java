package org.SprintForge.modules.auth.service;

import org.SprintForge.modules.auth.dto.ChangePasswordRequest;
import org.SprintForge.modules.auth.dto.ForgotPasswordRequest;
import org.SprintForge.modules.auth.dto.ResetPasswordRequest;
import org.SprintForge.modules.auth.entity.PasswordResetToken;
import org.SprintForge.modules.auth.exception.AuthException;
import org.SprintForge.modules.auth.repository.EmailVerificationTokenRepository;
import org.SprintForge.modules.auth.repository.PasswordResetTokenRepository;
import org.SprintForge.modules.auth.repository.RefreshTokenRepository;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.RoleRepository;
import org.SprintForge.modules.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceSecurityTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private EmailVerificationTokenRepository emailVerificationTokenRepository;
    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Mock
    private PasswordService passwordService;
    @Mock
    private JwtService jwtService;
    @Mock
    private OtpService otpService;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .username("security_user")
                .email("sec@sprintforge.com")
                .password("encoded_pass")
                .build();
        testUser.setId(1L);
    }

    @Test
    void forgotPassword_WhenUserNotFound_ShouldNotThrowException() {
        // Prevents user/email enumeration
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail("unknown@sprintforge.com");

        when(userRepository.findByEmail("unknown@sprintforge.com")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> authService.forgotPassword(request));
        verify(passwordResetTokenRepository, never()).save(any());
    }

    @Test
    void forgotPassword_WhenUserExists_ShouldGenerateToken() {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail("sec@sprintforge.com");

        when(userRepository.findByEmail("sec@sprintforge.com")).thenReturn(Optional.of(testUser));

        assertDoesNotThrow(() -> authService.forgotPassword(request));
        verify(passwordResetTokenRepository, times(1)).save(any(PasswordResetToken.class));
    }

    @Test
    void resetPassword_ShouldRevokeAllExistingSessions() {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setToken("valid-reset-token");
        request.setNewPassword("new_secret_password");

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token("valid-reset-token")
                .user(testUser)
                .expiryDate(Instant.now().plus(1, ChronoUnit.HOURS))
                .build();
        resetToken.setId(10L);

        when(passwordResetTokenRepository.findByToken("valid-reset-token")).thenReturn(Optional.of(resetToken));
        when(passwordService.encode("new_secret_password")).thenReturn("new_encoded_pass");

        authService.resetPassword(request);

        verify(userRepository, times(1)).save(testUser);
        verify(passwordResetTokenRepository, times(1)).delete(resetToken);
        // Verify all refresh tokens for this user are deleted (session revocation)
        verify(refreshTokenRepository, times(1)).deleteByUser(testUser);
    }

    @Test
    void changePassword_WithCorrectCurrentPassword_ShouldRevokeAllExistingSessions() {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("correct_old_pass");
        request.setNewPassword("brand_new_pass");

        when(userRepository.findByUsername("security_user")).thenReturn(Optional.of(testUser));
        when(passwordService.matches("correct_old_pass", "encoded_pass")).thenReturn(true);
        when(passwordService.encode("brand_new_pass")).thenReturn("brand_new_encoded");

        authService.changePassword("security_user", request);

        verify(userRepository, times(1)).save(testUser);
        // Verify all refresh tokens revoked
        verify(refreshTokenRepository, times(1)).deleteByUser(testUser);
    }

    @Test
    void changePassword_WithWrongPassword_ShouldThrowExceptionAndNotRevoke() {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("wrong_pass");
        request.setNewPassword("brand_new_pass");

        when(userRepository.findByUsername("security_user")).thenReturn(Optional.of(testUser));
        when(passwordService.matches("wrong_pass", "encoded_pass")).thenReturn(false);

        assertThrows(AuthException.class, () -> authService.changePassword("security_user", request));
        verify(refreshTokenRepository, never()).deleteByUser(any());
    }
}