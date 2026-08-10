package org.SprintForge.platform.communication.email;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.communication.ports.EmailSenderPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("platformEmailQueueService")
@RequiredArgsConstructor
public class EmailQueueService {

    private final EmailQueueRepository emailQueueRepository;
    private final EmailTemplateRepository emailTemplateRepository;
    private final EmailSenderPort emailSenderPort;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Transactional
    public EmailQueue queueEmail(String recipient, String templateName, Map<String, String> variables) {
        String payloadJson = objectMapper.writeValueAsString(variables);
        EmailQueue queueItem = EmailQueue.builder()
                .recipient(recipient)
                .templateName(templateName)
                .payload(payloadJson)
                .status("PENDING")
                .attempts(0)
                .scheduledAt(Instant.now())
                .build();

        log.info("[EmailQueueService] Queued email to {} using template {}", recipient, templateName);
        return emailQueueRepository.save(queueItem);
    }

    @Transactional
    public void processQueue() {
        List<EmailQueue> pendingEmails = emailQueueRepository.findByStatusOrderByScheduledAtAsc("PENDING", PageRequest.of(0, 50));
        if (pendingEmails.isEmpty()) {
            return;
        }

        for (EmailQueue item : pendingEmails) {
            try {
                String subject = "SprintForge Notification: " + item.getTemplateName();
                String body = item.getPayload();

                EmailTemplate template = emailTemplateRepository.findByName(item.getTemplateName()).orElse(null);
                if (template != null) {
                    subject = template.getSubject();
                    body = template.getBody();
                }

                boolean sent = emailSenderPort.sendEmail(item.getRecipient(), subject, body);
                if (sent) {
                    item.setStatus("SENT");
                    item.setSentAt(Instant.now());
                } else {
                    item.setAttempts(item.getAttempts() + 1);
                    if (item.getAttempts() >= 3) {
                        item.setStatus("FAILED");
                    }
                }
            } catch (Exception e) {
                log.error("[EmailQueueProcessor] Error processing email queue item ID {}: {}", item.getId(), e.getMessage());
                item.setAttempts(item.getAttempts() + 1);
                item.setError(e.getMessage());
                if (item.getAttempts() >= 3) {
                    item.setStatus("FAILED");
                }
            }
            emailQueueRepository.save(item);
        }
    }
}
