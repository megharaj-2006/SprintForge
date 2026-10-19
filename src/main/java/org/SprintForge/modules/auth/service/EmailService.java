package org.SprintForge.modules.auth.service;

public interface EmailService {
    void sendVerificationEmail(String toEmail, String username, String verificationCode);
    void sendPasswordResetEmail(String toEmail, String username, String resetToken);
}
