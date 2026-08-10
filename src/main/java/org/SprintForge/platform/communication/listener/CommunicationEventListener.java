package org.SprintForge.platform.communication.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.communication.activity.ActivityFeedService;
import org.SprintForge.platform.communication.activity.ActivityType;
import org.SprintForge.platform.communication.dto.CreateNotificationRequest;
import org.SprintForge.platform.communication.email.EmailQueueService;
import org.SprintForge.platform.communication.notification.NotificationChannel;
import org.SprintForge.platform.communication.notification.NotificationService;
import org.SprintForge.platform.communication.notification.NotificationType;
import org.SprintForge.platform.communication.webhook.WebhookDeliveryService;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.SprintForge.platform.event.listener.PlatformEventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component("platformCommunicationEventListener")
@Order(5)
@RequiredArgsConstructor
public class CommunicationEventListener implements PlatformEventListener {

    private final NotificationService notificationService;
    private final ActivityFeedService activityFeedService;
    private final EmailQueueService emailQueueService;
    private final WebhookDeliveryService webhookDeliveryService;

    @Override
    public void onEvent(BaseDomainEvent event) {
        log.info("[CommunicationEventListener] Processing communication pipeline for event {} (ID: {})",
                event.getEventType(), event.getEventId());

        String recipient = event.getTriggeredBy() != null ? event.getTriggeredBy() : "user-default";
        String summary = String.format("Event %s occurred on %s %s",
                event.getEventType(), event.getAggregateType(), event.getAggregateId());

        // 1. Create In-App Notification
        CreateNotificationRequest notifReq = CreateNotificationRequest.builder()
                .recipientId(recipient)
                .actorId(event.getTriggeredBy())
                .workspaceId(event.getWorkspaceId())
                .projectId(event.getProjectId())
                .entityType(event.getAggregateType())
                .entityId(event.getAggregateId())
                .notificationType(NotificationType.SYSTEM)
                .priority(event.getPriority())
                .title(event.getEventType())
                .message(summary)
                .channel(NotificationChannel.IN_APP)
                .build();
        notificationService.createNotification(notifReq);

        // 2. Record Activity Feed
        activityFeedService.recordActivity(
                event.getWorkspaceId(),
                event.getProjectId(),
                recipient,
                event.getAggregateType(),
                event.getAggregateId(),
                ActivityType.CREATED,
                summary,
                event.getEventId()
        );

        // 3. Queue Email
        emailQueueService.queueEmail(recipient, "DEFAULT_NOTIFICATION_TEMPLATE", Map.of(
                "userName", recipient,
                "taskTitle", summary,
                "projectName", event.getProjectId() != null ? event.getProjectId() : "Workspace"
        ));

        // 4. Trigger Webhook Delivery
        webhookDeliveryService.deliverWebhook(
                "https://webhook.sprintforge.internal/events",
                event.getEventType(),
                summary
        );
    }

    @Override
    public boolean supports(BaseDomainEvent event) {
        return true;
    }

    @Override
    public int getOrder() {
        return 5;
    }
}
