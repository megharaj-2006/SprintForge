package org.SprintForge.platform.communication.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.communication.dto.CreateNotificationRequest;
import org.SprintForge.platform.communication.dto.NotificationResponse;
import org.SprintForge.platform.communication.preference.NotificationFilterService;
import org.SprintForge.platform.event.metadata.EventPriority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("platformNotificationService")
@RequiredArgsConstructor
public class NotificationService {

    private final PlatformNotificationRepository notificationRepository;
    private final NotificationFilterService notificationFilterService;
    private final NotificationDeliveryService notificationDeliveryService;

    @Transactional
    public NotificationResponse createNotification(CreateNotificationRequest request) {
        if (!notificationFilterService.shouldDeliver(request)) {
            log.info("[NotificationService] Notification filtered out for recipient: {}", request.getRecipientId());
            return null;
        }

        PlatformNotification notification = PlatformNotification.builder()
                .recipientId(request.getRecipientId())
                .actorId(request.getActorId())
                .workspaceId(request.getWorkspaceId())
                .projectId(request.getProjectId())
                .entityType(request.getEntityType())
                .entityId(request.getEntityId())
                .notificationType(request.getNotificationType() != null ? request.getNotificationType() : NotificationType.SYSTEM)
                .priority(request.getPriority() != null ? request.getPriority() : EventPriority.NORMAL)
                .title(request.getTitle())
                .message(request.getMessage())
                .channel(request.getChannel() != null ? request.getChannel() : NotificationChannel.IN_APP)
                .status(NotificationStatus.PENDING)
                .actionUrl(request.getActionUrl())
                .isRead(false)
                .isArchived(false)
                .build();

        notification = notificationRepository.save(notification);

        notificationDeliveryService.deliver(notification);
        notificationRepository.save(notification);

        return mapToResponse(notification);
    }

    public NotificationResponse mapToResponse(PlatformNotification n) {
        return NotificationResponse.builder()
                .id(n.getId())
                .recipientId(n.getRecipientId())
                .actorId(n.getActorId())
                .workspaceId(n.getWorkspaceId())
                .projectId(n.getProjectId())
                .entityType(n.getEntityType())
                .entityId(n.getEntityId())
                .notificationType(n.getNotificationType())
                .priority(n.getPriority())
                .title(n.getTitle())
                .message(n.getMessage())
                .channel(n.getChannel())
                .status(n.getStatus())
                .actionUrl(n.getActionUrl())
                .isRead(n.isRead())
                .readAt(n.getReadAt())
                .isArchived(n.isArchived())
                .expiresAt(n.getExpiresAt())
                .createdAt(n.getCreatedAt())
                .build();
    }
}
