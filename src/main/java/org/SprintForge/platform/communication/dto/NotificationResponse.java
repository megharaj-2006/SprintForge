package org.SprintForge.platform.communication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.platform.communication.notification.NotificationChannel;
import org.SprintForge.platform.communication.notification.NotificationStatus;
import org.SprintForge.platform.communication.notification.NotificationType;
import org.SprintForge.platform.event.metadata.EventPriority;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Long id;
    private String recipientId;
    private String actorId;
    private String workspaceId;
    private String projectId;
    private String entityType;
    private String entityId;
    private NotificationType notificationType;
    private EventPriority priority;
    private String title;
    private String message;
    private NotificationChannel channel;
    private NotificationStatus status;
    private String actionUrl;
    private boolean isRead;
    private LocalDateTime readAt;
    private boolean isArchived;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}
