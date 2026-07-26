package org.SprintForge.modules.auth.service;

import org.SprintForge.modules.auth.dto.*;

public interface AuthService {
    JwtResponse signup(SignupRequest request, String userAgent, String ipAddress);
    JwtResponse login(LoginRequest request, String userAgent, String ipAddress);
    JwtResponse refresh(RefreshRequest request);
    void logout(String token);
    void forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
    void changePassword(String username, ChangePasswordRequest request);
    void logoutAll(String username);
}