package org.SprintForge.platform.communication.preference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.platform.communication.notification.NotificationChannel;
import org.SprintForge.platform.communication.notification.NotificationType;

import java.time.LocalDateTime;

@Data
@Entity(name = "PlatformNotificationPreference")
@Table(name = "communication_notification_preferences")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "workspace_id", length = 100)
    private String workspaceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false, length = 50)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false, length = 50)
    private NotificationType notificationType;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "mute_until")
    private LocalDateTime muteUntil;

    @Enumerated(EnumType.STRING)
    @Column(name = "digest_frequency", nullable = false, length = 30)
    private DigestFrequency digestFrequency;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (digestFrequency == null) {
            digestFrequency = DigestFrequency.IMMEDIATE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
