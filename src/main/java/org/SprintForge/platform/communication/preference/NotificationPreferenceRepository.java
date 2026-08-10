package org.SprintForge.platform.communication.preference;

import org.SprintForge.platform.communication.notification.NotificationChannel;
import org.SprintForge.platform.communication.notification.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository("platformNotificationPreferenceRepository")
public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreference, Long> {

    List<NotificationPreference> findByUserId(String userId);

    Optional<NotificationPreference> findByUserIdAndWorkspaceIdAndChannelAndNotificationType(
            String userId, String workspaceId, NotificationChannel channel, NotificationType notificationType);

    Optional<NotificationPreference> findByUserIdAndWorkspaceIdIsNullAndChannelAndNotificationType(
            String userId, NotificationChannel channel, NotificationType notificationType);
}
