package org.SprintForge.platform.communication.ports;

public interface WebhookSenderPort {
    int deliverWebhook(String url, String eventType, String payload);
}
