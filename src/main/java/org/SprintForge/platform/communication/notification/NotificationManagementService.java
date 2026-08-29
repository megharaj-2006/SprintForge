package org.SprintForge.platform.communication.notification;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.platform.communication.dto.NotificationResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service("platformNotificationManagementService")
@RequiredArgsConstructor
public class NotificationManagementService {

    private final PlatformNotificationRepository notificationRepository;
    private final NotificationService notificationService;

    @Transactional
    public NotificationResponse markAsRead(Long id) {
        PlatformNotification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));
        if (!notification.isRead()) {
            notification.setRead(true);
            notification.setReadAt(LocalDateTime.now());
            notification.setStatus(NotificationStatus.READ);
            notification = notificationRepository.save(notification);
        }
        return notificationService.mapToResponse(notification);
    }

    @Transactional
    public void markAllAsRead(String recipientId) {
        List<PlatformNotification> unread = notificationRepository
                .findByRecipientIdAndIsReadFalseAndIsArchivedFalseOrderByCreatedAtDesc(recipientId, Pageable.unpaged());
        for (PlatformNotification n : unread) {
            n.setRead(true);
            n.setReadAt(LocalDateTime.now());
            n.setStatus(NotificationStatus.READ);
        }
        notificationRepository.saveAll(unread);
    }

    @Transactional
    public NotificationResponse archiveNotification(Long id) {
        PlatformNotification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));
        notification.setArchived(true);
        notification.setStatus(NotificationStatus.ARCHIVED);
        notification = notificationRepository.save(notification);
        return notificationService.mapToResponse(notification);
    }

    @Transactional
    public void deleteNotification(Long id) {
        PlatformNotification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));
        notificationRepository.delete(notification);
    }
}
