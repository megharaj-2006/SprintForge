package org.SprintForge.platform.communication.webhook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.platform.communication.ports.WebhookSenderPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("platformWebhookRetryService")
@RequiredArgsConstructor
public class WebhookRetryService {

    private final PlatformWebhookDeliveryRepository deliveryRepository;
    private final WebhookSenderPort webhookSenderPort;

    @Transactional
    public PlatformWebhookDelivery retryDelivery(Long id) {
        PlatformWebhookDelivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WebhookDelivery", "id", id));

        long startTime = System.currentTimeMillis();
        delivery.setAttempts(delivery.getAttempts() + 1);

        try {
            int statusCode = webhookSenderPort.deliverWebhook(delivery.getWebhookUrl(), delivery.getEventType(), delivery.getPayload());
            long duration = System.currentTimeMillis() - startTime;
            delivery.setResponseCode(statusCode);
            delivery.setDurationMs(duration);
            delivery.setStatus(statusCode >= 200 && statusCode < 300 ? "SUCCESS" : "FAILED");
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            delivery.setDurationMs(duration);
            delivery.setStatus("FAILED");
            log.error("[WebhookRetryService] Manual retry ID {} failed: {}", id, e.getMessage());
        }

        return deliveryRepository.save(delivery);
    }
}
