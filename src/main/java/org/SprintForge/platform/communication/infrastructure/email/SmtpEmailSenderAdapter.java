package org.SprintForge.platform.communication.infrastructure.email;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.communication.ports.EmailSenderPort;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmtpEmailSenderAdapter implements EmailSenderPort {

    @Override
    public boolean sendEmail(String recipient, String subject, String body) {
        log.info("[SmtpEmailSenderAdapter] Delivering email to {} | Subject: {}", recipient, subject);
        // Clean pluggable adapter simulation; replace with JavaMailSender / SES when SMTP configured
        return true;
    }
}
