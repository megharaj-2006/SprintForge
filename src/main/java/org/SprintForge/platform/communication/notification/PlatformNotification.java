package org.SprintForge.platform.communication.notification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.platform.event.metadata.EventPriority;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "communication_notifications")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient_id", nullable = false, length = 100)
    private String recipientId;

    @Column(name = "actor_id", length = 100)
    private String actorId;

    @Column(name = "workspace_id", length = 100)
    private String workspaceId;

    @Column(name = "project_id", length = 100)
    private String projectId;

    @Column(name = "entity_type", nullable = false, length = 100)
    private String entityType;

    @Column(name = "entity_id", nullable = false, length = 100)
    private String entityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false, length = 50)
    private NotificationType notificationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 30)
    private EventPriority priority;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false, length = 50)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private NotificationStatus status;

    @Column(name = "action_url", length = 500)
    private String actionUrl;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Column(name = "is_archived", nullable = false)
    private boolean isArchived;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (priority == null) {
            priority = EventPriority.NORMAL;
        }
        if (channel == null) {
            channel = NotificationChannel.IN_APP;
        }
        if (status == null) {
            status = NotificationStatus.PENDING;
        }
    }
}
