package org.SprintForge.platform.communication.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.communication.ports.EmailSenderPort;
import org.SprintForge.platform.communication.ports.NotificationDeliveryPort;
import org.SprintForge.platform.communication.ports.WebhookSenderPort;
import org.springframework.stereotype.Service;

@Slf4j
@Service("platformNotificationDeliveryService")
@RequiredArgsConstructor
public class NotificationDeliveryService {

    private final NotificationDeliveryPort notificationDeliveryPort;
    private final EmailSenderPort emailSenderPort;
    private final WebhookSenderPort webhookSenderPort;

    public void deliver(PlatformNotification notification) {
        log.info("[NotificationDeliveryService] Delivering notification ID {} via channel {}",
                notification.getId(), notification.getChannel());

        switch (notification.getChannel()) {
            case IN_APP -> notificationDeliveryPort.deliverInApp(
                    notification.getRecipientId(),
                    notification.getTitle(),
                    notification.getMessage(),
                    notification.getActionUrl()
            );
            case EMAIL -> emailSenderPort.sendEmail(
                    notification.getRecipientId(),
                    notification.getTitle(),
                    notification.getMessage()
            );
            case WEBHOOK -> webhookSenderPort.deliverWebhook(
                    notification.getActionUrl() != null ? notification.getActionUrl() : "https://webhook.sprintforge.internal",
                    notification.getNotificationType().name(),
                    notification.getMessage()
            );
            case FUTURE_PUSH -> log.info("[NotificationDeliveryService] PUSH channel skipped (reserved for V2)");
        }

        notification.setStatus(NotificationStatus.DELIVERED);
    }
}
