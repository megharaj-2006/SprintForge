package org.SprintForge.modules.auth.controller;

import org.SprintForge.common.exception.GlobalExceptionHandler;
import org.SprintForge.common.util.JsonUtil;
import org.SprintForge.modules.auth.dto.*;
import org.SprintForge.modules.auth.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void signup_WithValidData_ShouldReturn200() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("john_doe");
        request.setEmail("john@example.com");
        request.setPassword("password123");

        JwtResponse jwtResponse = JwtResponse.builder()
                .token("access_token")
                .refreshToken("refresh_token")
                .build();

        when(authService.signup(any(SignupRequest.class), any(), any())).thenReturn(jwtResponse);

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("access_token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh_token"));
    }

    @Test
    void signup_WithInvalidEmail_ShouldReturn400() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("john_doe");
        request.setEmail("invalid-email");
        request.setPassword("password123");

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.validationErrors.email").value("Invalid email format"));
    }

    @Test
    void signup_WithShortPassword_ShouldReturn400() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("john_doe");
        request.setEmail("john@example.com");
        request.setPassword("123"); // Too short (min = 6)

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.validationErrors.password").value("Password must be between 6 and 40 characters"));
    }

    @Test
    void login_WithValidData_ShouldReturn200() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsernameOrEmail("john_doe");
        request.setPassword("password123");

        JwtResponse jwtResponse = JwtResponse.builder()
                .token("access_token")
                .refreshToken("refresh_token")
                .build();

        when(authService.login(any(LoginRequest.class), any(), any())).thenReturn(jwtResponse);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("access_token"));
    }

    @Test
    void login_WithMissingCredentials_ShouldReturn400() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsernameOrEmail("");
        request.setPassword("");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.validationErrors.usernameOrEmail").value("Username or email is required"))
                .andExpect(jsonPath("$.validationErrors.password").value("Password is required"));
    }

    @Test
    void refresh_WithMissingToken_ShouldReturn400() throws Exception {
        RefreshRequest request = new RefreshRequest();
        request.setRefreshToken("");

        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.validationErrors.refreshToken").value("Refresh token is required"));
    }

    @Test
    void forgotPassword_WithInvalidEmail_ShouldReturn400() throws Exception {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail("not-an-email");

        mockMvc.perform(post("/api/v1/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.validationErrors.email").value("Invalid email format"));
    }

    @Test
    void resetPassword_WithShortPassword_ShouldReturn400() throws Exception {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setToken("valid-token");
        request.setNewPassword("123"); // Too short

        mockMvc.perform(post("/api/v1/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.validationErrors.newPassword").value("Password must be between 6 and 40 characters"));
    }

    @Test
    void changePassword_WithShortNewPassword_ShouldReturn400() throws Exception {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("old_pass");
        request.setNewPassword("123"); // Too short

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("john_doe");

        mockMvc.perform(post("/api/v1/auth/change-password")
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.validationErrors.newPassword").value("Password must be between 6 and 40 characters"));
    }
}
