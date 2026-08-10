package org.SprintForge.platform.communication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.platform.communication.notification.NotificationChannel;
import org.SprintForge.platform.communication.notification.NotificationType;
import org.SprintForge.platform.communication.preference.DigestFrequency;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreferenceResponse {
    private Long id;
    private String userId;
    private String workspaceId;
    private NotificationChannel channel;
    private NotificationType notificationType;
    private boolean enabled;
    private Instant muteUntil;
    private DigestFrequency digestFrequency;
}
