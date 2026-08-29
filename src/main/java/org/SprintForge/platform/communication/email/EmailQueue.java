package org.SprintForge.platform.communication.email;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity(name = "PlatformEmailQueue")
@Table(name = "email_queue")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @Column(name = "template_name", nullable = false, length = 100)
    private String templateName;

    @Column(name = "payload", nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Column(name = "attempts", nullable = false)
    private int attempts;

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "error", columnDefinition = "TEXT")
    private String error;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (scheduledAt == null) {
            scheduledAt = LocalDateTime.now();
        }
        if (status == null) {
            status = "PENDING";
        }
    }
}
