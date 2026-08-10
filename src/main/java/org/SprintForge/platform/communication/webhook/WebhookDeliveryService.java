package org.SprintForge.platform.communication.webhook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.communication.ports.WebhookSenderPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("platformWebhookDeliveryService")
@RequiredArgsConstructor
public class WebhookDeliveryService {

    private final PlatformWebhookDeliveryRepository deliveryRepository;
    private final WebhookSenderPort webhookSenderPort;

    @Transactional
    public PlatformWebhookDelivery deliverWebhook(String url, String eventType, String payload) {
        long startTime = System.currentTimeMillis();
        PlatformWebhookDelivery delivery = PlatformWebhookDelivery.builder()
                .webhookUrl(url)
                .eventType(eventType)
                .payload(payload)
                .attempts(1)
                .status("PENDING")
                .build();

        try {
            int statusCode = webhookSenderPort.deliverWebhook(url, eventType, payload);
            long duration = System.currentTimeMillis() - startTime;
            delivery.setResponseCode(statusCode);
            delivery.setDurationMs(duration);
            delivery.setStatus(statusCode >= 200 && statusCode < 300 ? "SUCCESS" : "FAILED");
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            delivery.setDurationMs(duration);
            delivery.setStatus("FAILED");
            log.error("[WebhookDeliveryService] Webhook delivery to {} failed: {}", url, e.getMessage());
        }

        return deliveryRepository.save(delivery);
    }
}
