package org.SprintForge.modules.auth.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final String fromAddress;

    public EmailServiceImpl(
            ObjectProvider<JavaMailSender> mailSenderProvider,
            @Value("${sprintforge.mail.from:vinithshanbhag007@gmail.com}") String fromAddress
    ) {
        this.mailSender = mailSenderProvider.getIfAvailable();
        String cleaned = fromAddress;
        if (cleaned != null && cleaned.contains("<") && cleaned.contains(">")) {
            cleaned = cleaned.substring(cleaned.indexOf('<') + 1, cleaned.indexOf('>')).trim();
        }
        this.fromAddress = (cleaned == null || cleaned.isBlank()) ? "vinithshanbhag007@gmail.com" : cleaned;
    }


    @Override
    @Async
    public void sendVerificationEmail(String toEmail, String username, String verificationCode) {
        log.info("Preparing verification email for {} ({})", toEmail, username);

        if (mailSender == null) {
            log.warn("================================================================================");
            log.warn("[EMAIL NOT SENT - SMTP DISABLED] JavaMailSender is not configured.");
            log.warn("Recipient : {}", toEmail);
            log.warn("Username  : {}", username);
            log.warn("OTP Code  : >>> {} <<< (valid for 15 minutes)", verificationCode);
            log.warn("================================================================================");
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromAddress, "SprintForge");
            helper.setTo(toEmail);
            helper.setSubject("Verify your SprintForge Account - Verification Code: " + verificationCode);

            String htmlContent = buildVerificationEmailTemplate(username, verificationCode);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Verification email successfully sent to {}", toEmail);

        } catch (Exception e) {
            log.error("Failed to send verification email to {}: {}. Printing fallback code.", toEmail, e.getMessage());
            log.warn("================================================================================");
            log.warn("Recipient : {}", toEmail);
            log.warn("OTP Code  : >>> {} <<<", verificationCode);
            log.warn("================================================================================");
        }
    }

    @Override
    @Async
    public void sendPasswordResetEmail(String toEmail, String username, String resetToken) {
        log.info("Preparing password reset email for {} ({})", toEmail, username);

        if (mailSender == null) {
            log.warn("================================================================================");
            log.warn("[EMAIL NOT SENT - SMTP DISABLED] Password reset token for {}: {}", toEmail, resetToken);
            log.warn("================================================================================");
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromAddress, "SprintForge");
            helper.setTo(toEmail);
            helper.setSubject("SprintForge - Reset Your Password");

            String htmlContent = buildPasswordResetEmailTemplate(username, resetToken);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Password reset email successfully sent to {}", toEmail);

        } catch (Exception e) {
            log.error("Failed to send password reset email to {}: {}", toEmail, e.getMessage());
        }
    }

    private String buildVerificationEmailTemplate(String username, String code) {
        return "<!DOCTYPE html>"
                + "<html>"
                + "<head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'></head>"
                + "<body style='margin:0;padding:0;background-color:#09090b;font-family:-apple-system,BlinkMacSystemFont,\"Segoe UI\",Roboto,sans-serif;color:#f4f4f5;'>"
                + "  <table width='100%' cellpadding='0' cellspacing='0' style='background-color:#09090b;padding:40px 16px;'>"
                + "    <tr><td align='center'>"
                + "      <table width='100%' style='max-width:540px;background-color:#18181b;border:1px solid #27272a;border-radius:12px;padding:36px;box-shadow:0 8px 24px rgba(0,0,0,0.5);'>"
                + "        <tr><td align='center' style='padding-bottom:24px;'>"
                + "          <div style='display:inline-block;background-color:#fafafa;color:#09090b;font-weight:900;font-size:18px;line-height:36px;width:36px;height:36px;border-radius:8px;text-align:center;'>SF</div>"
                + "          <div style='font-size:18px;font-weight:700;letter-spacing:-0.5px;color:#fafafa;margin-top:8px;'>SprintForge</div>"
                + "        </td></tr>"
                + "        <tr><td>"
                + "          <h1 style='font-size:20px;font-weight:600;color:#fafafa;margin:0 0 12px 0;'>Verify your email address</h1>"
                + "          <p style='font-size:14px;color:#a1a1aa;line-height:22px;margin:0 0 20px 0;'>Hello <strong style='color:#fafafa;'>" + (username != null ? username : "there") + "</strong>,</p>"
                + "          <p style='font-size:14px;color:#a1a1aa;line-height:22px;margin:0 0 24px 0;'>Thank you for starting your registration on SprintForge. Use the verification code below to verify your email address and activate your workspace account:</p>"
                + "          <div style='background-color:#09090b;border:1px solid #3f3f46;border-radius:8px;padding:20px;text-align:center;margin:0 0 24px 0;'>"
                + "            <span style='font-family:\"SF Mono\",Menlo,Monaco,Consolas,monospace;font-size:32px;font-weight:800;letter-spacing:10px;color:#fafafa;padding-left:10px;'>" + code + "</span>"
                + "          </div>"
                + "          <p style='font-size:12px;color:#71717a;line-height:18px;margin:0 0 16px 0;'>This verification code is strictly confidential and will expire in <strong style='color:#a1a1aa;'>15 minutes</strong>. Never share this code with anyone.</p>"
                + "          <p style='font-size:12px;color:#71717a;line-height:18px;margin:0;'>If you did not initiate this request, you can safely ignore this email.</p>"
                + "        </td></tr>"
                + "        <tr><td style='border-top:1px solid #27272a;margin-top:28px;padding-top:20px;text-align:center;'>"
                + "          <p style='font-size:11px;color:#52525b;margin:0;'>SprintForge &bull; High-Velocity Enterprise Project Engineering</p>"
                + "        </td></tr>"
                + "      </table>"
                + "    </td></tr>"
                + "  </table>"
                + "</body>"
                + "</html>";
    }

    private String buildPasswordResetEmailTemplate(String username, String resetToken) {
        return "<!DOCTYPE html>"
                + "<html>"
                + "<head><meta charset='UTF-8'></head>"
                + "<body style='margin:0;padding:24px;background-color:#09090b;font-family:sans-serif;color:#f4f4f5;'>"
                + "  <div style='max-width:500px;margin:0 auto;background-color:#18181b;padding:32px;border:1px solid #27272a;border-radius:10px;'>"
                + "    <h2 style='color:#fafafa;margin-top:0;'>Reset Your SprintForge Password</h2>"
                + "    <p style='color:#a1a1aa;font-size:14px;'>Hello " + username + ",</p>"
                + "    <p style='color:#a1a1aa;font-size:14px;'>We received a request to reset your password. Use the token below to set a new password:</p>"
                + "    <div style='background:#09090b;padding:12px 16px;border-radius:6px;font-family:monospace;font-size:16px;color:#fafafa;word-break:break-all;margin:16px 0;'>" + resetToken + "</div>"
                + "    <p style='font-size:12px;color:#71717a;'>This token will expire in 1 hour.</p>"
                + "  </div>"
                + "</body>"
                + "</html>";
    }
}
