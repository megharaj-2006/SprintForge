package org.SprintForge.platform.communication.infrastructure.inapp;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.communication.ports.NotificationDeliveryPort;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InAppNotificationDeliveryAdapter implements NotificationDeliveryPort {

    @Override
    public void deliverInApp(String recipientId, String title, String message, String actionUrl) {
        log.info("[InAppNotificationDeliveryAdapter] Delivered in-app notification to user: {} | Title: {}", recipientId, title);
    }
}
