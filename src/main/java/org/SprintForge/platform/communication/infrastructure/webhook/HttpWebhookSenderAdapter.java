package org.SprintForge.platform.communication.infrastructure.webhook;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.communication.ports.WebhookSenderPort;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HttpWebhookSenderAdapter implements WebhookSenderPort {

    @Override
    public int deliverWebhook(String url, String eventType, String payload) {
        log.info("[HttpWebhookSenderAdapter] Delivering webhook payload to {} for event: {}", url, eventType);
        // HTTP 200 OK simulated response for outbound webhooks
        return 200;
    }
}
