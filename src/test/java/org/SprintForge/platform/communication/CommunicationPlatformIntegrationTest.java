package org.SprintForge.platform.communication;

import org.SprintForge.platform.communication.activity.ActivityQueryService;
import org.SprintForge.platform.communication.dashboard.CommunicationDashboardService;
import org.SprintForge.platform.communication.dto.*;
import org.SprintForge.platform.communication.email.EmailQueueRepository;
import org.SprintForge.platform.communication.notification.NotificationChannel;
import org.SprintForge.platform.communication.notification.NotificationManagementService;
import org.SprintForge.platform.communication.notification.NotificationQueryService;
import org.SprintForge.platform.communication.notification.NotificationType;
import org.SprintForge.platform.communication.preference.NotificationPreferenceService;
import org.SprintForge.platform.communication.template.CommunicationTemplateService;
import org.SprintForge.platform.communication.webhook.PlatformWebhookDeliveryRepository;
import org.SprintForge.platform.event.event.TaskCreatedEvent;
import org.SprintForge.platform.event.publisher.PlatformEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CommunicationPlatformIntegrationTest {

    @Autowired
    private PlatformEventPublisher platformEventPublisher;

    @Autowired
    private NotificationQueryService notificationQueryService;

    @Autowired
    private NotificationManagementService notificationManagementService;

    @Autowired
    private NotificationPreferenceService preferenceService;

    @Autowired
    private ActivityQueryService activityQueryService;

    @Autowired
    private EmailQueueRepository emailQueueRepository;

    @Autowired
    private PlatformWebhookDeliveryRepository webhookDeliveryRepository;

    @Autowired
    private CommunicationTemplateService templateService;

    @Autowired
    private CommunicationDashboardService dashboardService;

    @Test
    public void testEventToCommunicationPipeline() {
        TaskCreatedEvent event = TaskCreatedEvent.create("task-555", "Design Notification Engine", "Desc", "user-dev", "ws-999", "proj-777", "user-dev");
        event.setEventId("evt-test-101");

        platformEventPublisher.publish(event);

        // Verify In-App Notification created
        List<NotificationResponse> notifications = notificationQueryService.getAllNotifications("user-dev", 10);
        assertFalse(notifications.isEmpty());
        NotificationResponse notif = notifications.get(0);
        assertEquals("user-dev", notif.getRecipientId());
        assertEquals("TaskCreatedEvent", notif.getTitle());

        // Verify Activity Feed recorded
        List<ActivityFeedResponse> activities = activityQueryService.getUserActivity("user-dev", 0, 10);
        assertFalse(activities.isEmpty());

        // Verify Email Queued
        long queuedEmails = emailQueueRepository.count();
        assertTrue(queuedEmails > 0);

        // Verify Webhook Delivered
        long webhooks = webhookDeliveryRepository.count();
        assertTrue(webhooks > 0);
    }

    @Test
    public void testNotificationLifecycle() {
        TaskCreatedEvent event = TaskCreatedEvent.create("task-556", "Fix bug in auth", "Desc", "user-alice", "ws-999", "proj-777", "user-alice");
        event.setEventId("evt-test-102");

        platformEventPublisher.publish(event);

        List<NotificationResponse> notifications = notificationQueryService.getUnreadNotifications("user-alice", 10);
        assertFalse(notifications.isEmpty());
        NotificationResponse unread = notifications.get(0);
        assertFalse(unread.isRead());

        // Mark read
        NotificationResponse read = notificationManagementService.markAsRead(unread.getId());
        assertTrue(read.isRead());

        // Verify unread count decreases
        UnreadCountResponse unreadCount = notificationQueryService.getUnreadCount("user-alice");
        assertEquals(0, unreadCount.getUnreadCount());

        // Archive
        NotificationResponse archived = notificationManagementService.archiveNotification(read.getId());
        assertTrue(archived.isArchived());
    }

    @Test
    public void testPreferenceMutingAndMentionOverride() {
        // Disable TASK notifications for user-bob
        preferenceService.updatePreference(NotificationPreferenceUpdateRequest.builder()
                .userId("user-bob")
                .channel(NotificationChannel.IN_APP)
                .notificationType(NotificationType.TASK)
                .enabled(false)
                .build());

        // Verify preferences recorded
        List<NotificationPreferenceResponse> prefs = preferenceService.getUserPreferences("user-bob");
        assertEquals(1, prefs.size());
        assertFalse(prefs.get(0).isEnabled());
    }

    @Test
    public void testTemplateVariableRendering() {
        String templateText = "Hello ${userName}, task ${taskTitle} in project ${projectName} was updated!";
        Map<String, String> variables = Map.of(
                "userName", "Sarah",
                "taskTitle", "Refactor Cluster 2",
                "projectName", "SprintForge Core"
        );

        String rendered = templateService.applyVariables(templateText, variables);
        assertEquals("Hello Sarah, task Refactor Cluster 2 in project SprintForge Core was updated!", rendered);
    }

    @Test
    public void testDashboardMetrics() {
        CommunicationDashboardResponse metrics = dashboardService.getDashboardMetrics();
        assertNotNull(metrics);
        assertTrue(metrics.getWebhookSuccessRate() >= 0.0);
    }
}
