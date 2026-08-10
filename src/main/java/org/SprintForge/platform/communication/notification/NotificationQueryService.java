package org.SprintForge.platform.communication.notification;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.communication.dto.NotificationResponse;
import org.SprintForge.platform.communication.dto.UnreadCountResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("platformNotificationQueryService")
@RequiredArgsConstructor
public class NotificationQueryService {

    private final PlatformNotificationRepository notificationRepository;
    private final NotificationService notificationService;

    @Transactional(readOnly = true)
    public List<NotificationResponse> getUnreadNotifications(String recipientId, int limit) {
        return notificationRepository
                .findByRecipientIdAndIsReadFalseAndIsArchivedFalseOrderByCreatedAtDesc(recipientId, PageRequest.of(0, limit))
                .stream()
                .map(notificationService::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotifications(String recipientId, int limit) {
        return notificationRepository
                .findByRecipientIdAndIsArchivedFalseOrderByCreatedAtDesc(recipientId, PageRequest.of(0, limit))
                .stream()
                .map(notificationService::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UnreadCountResponse getUnreadCount(String recipientId) {
        long count = notificationRepository.countByRecipientIdAndIsReadFalseAndIsArchivedFalse(recipientId);
        return UnreadCountResponse.builder()
                .recipientId(recipientId)
                .unreadCount(count)
                .build();
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> searchNotifications(String recipientId, String query, int limit) {
        return notificationRepository
                .searchNotifications(recipientId, query, PageRequest.of(0, limit))
                .stream()
                .map(notificationService::mapToResponse)
                .toList();
    }
}
