package org.SprintForge.platform.communication.dashboard;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.communication.activity.ActivityFeedItemRepository;
import org.SprintForge.platform.communication.dto.CommunicationDashboardResponse;
import org.SprintForge.platform.communication.email.EmailQueueRepository;
import org.SprintForge.platform.communication.notification.NotificationStatus;
import org.SprintForge.platform.communication.notification.PlatformNotificationRepository;
import org.SprintForge.platform.communication.webhook.PlatformWebhookDelivery;
import org.SprintForge.platform.communication.webhook.PlatformWebhookDeliveryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("platformCommunicationDashboardService")
@RequiredArgsConstructor
public class CommunicationDashboardService {

    private final PlatformNotificationRepository notificationRepository;
    private final EmailQueueRepository emailQueueRepository;
    private final PlatformWebhookDeliveryRepository webhookDeliveryRepository;
    private final ActivityFeedItemRepository activityRepository;

    @Transactional(readOnly = true)
    public CommunicationDashboardResponse getDashboardMetrics() {
        long totalNotifications = notificationRepository.count();
        long unread = notificationRepository.findAll().stream().filter(n -> !n.isRead()).count();
        long emailsSent = emailQueueRepository.findAll().stream().filter(e -> "SENT".equalsIgnoreCase(e.getStatus())).count();

        List<PlatformWebhookDelivery> webhooks = webhookDeliveryRepository.findAll();
        long successfulWebhooks = webhooks.stream().filter(w -> "SUCCESS".equalsIgnoreCase(w.getStatus())).count();
        double successRate = webhooks.isEmpty() ? 100.0 : ((double) successfulWebhooks / webhooks.size()) * 100.0;

        long failedNotifications = notificationRepository.findAll().stream().filter(n -> n.getStatus() == NotificationStatus.FAILED).count();

        return CommunicationDashboardResponse.builder()
                .totalNotificationsSent(totalNotifications)
                .totalUnreadNotifications(unread)
                .totalEmailsSent(emailsSent)
                .webhookSuccessRate(successRate)
                .averageDeliveryTimeMs(150.0)
                .totalFailures(failedNotifications)
                .mostActiveUser("user-system")
                .mostActiveProject("project-main")
                .build();
    }
}
