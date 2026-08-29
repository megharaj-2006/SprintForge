package org.SprintForge.platform.communication.template;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.platform.communication.notification.NotificationChannel;
import org.SprintForge.platform.communication.notification.NotificationType;

import java.time.LocalDateTime;

@Data
@Entity(name = "PlatformCommunicationTemplate")
@Table(name = "communication_templates")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunicationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false, length = 50)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private NotificationType type;

    @Column(name = "subject")
    private String subject;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "variables", columnDefinition = "TEXT")
    private String variables;

    @Column(name = "version", nullable = false)
    private int version;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (version == 0) {
            version = 1;
        }
        active = true;
    }
}
