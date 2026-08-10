package org.SprintForge.platform.communication.preference;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.communication.dto.CreateNotificationRequest;
import org.SprintForge.platform.communication.notification.NotificationChannel;
import org.SprintForge.platform.communication.notification.NotificationType;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service("platformNotificationFilterService")
@RequiredArgsConstructor
public class NotificationFilterService {

    private final NotificationPreferenceRepository preferenceRepository;

    public boolean shouldDeliver(CreateNotificationRequest request) {
        if (request.getRecipientId() == null || request.getRecipientId().isBlank()) {
            log.warn("[NotificationFilter] Notification rejected: recipientId is missing");
            return false;
        }

        // Rule: Mention notifications ignore mute rules
        if (request.getNotificationType() == NotificationType.MENTION) {
            return true;
        }

        // Check workspace / global user preferences
        Optional<NotificationPreference> prefOpt = preferenceRepository
                .findByUserIdAndWorkspaceIdAndChannelAndNotificationType(
                        request.getRecipientId(),
                        request.getWorkspaceId(),
                        request.getChannel() != null ? request.getChannel() : NotificationChannel.IN_APP,
                        request.getNotificationType()
                );

        if (prefOpt.isEmpty()) {
            prefOpt = preferenceRepository.findByUserIdAndWorkspaceIdIsNullAndChannelAndNotificationType(
                    request.getRecipientId(),
                    request.getChannel() != null ? request.getChannel() : NotificationChannel.IN_APP,
                    request.getNotificationType()
            );
        }

        if (prefOpt.isPresent()) {
            NotificationPreference pref = prefOpt.get();

            if (!pref.isEnabled()) {
                log.info("[NotificationFilter] Notification suppressed for user {} (channel/type disabled)", request.getRecipientId());
                return false;
            }

            if (pref.getMuteUntil() != null && pref.getMuteUntil().isAfter(Instant.now())) {
                log.info("[NotificationFilter] Notification suppressed for user {} (workspace muted until {})", request.getRecipientId(), pref.getMuteUntil());
                return false;
            }
        }

        return true;
    }
}
