package org.SprintForge.platform.event.retry;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Table(name = "dead_letter_events")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeadLetterEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_event_id", nullable = false, length = 100)
    private String originalEventId;

    @Column(name = "event_type", nullable = false, length = 100)
    private String eventType;

    @Column(name = "aggregate_type", nullable = false, length = 100)
    private String aggregateType;

    @Column(name = "aggregate_id", nullable = false, length = 100)
    private String aggregateId;

    @Column(name = "listener", nullable = false, length = 150)
    private String listener;

    @Column(name = "reason", nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Column(name = "attempts", nullable = false)
    private int attempts;

    @Column(name = "failed_at", nullable = false)
    private Instant failedAt;

    @Column(name = "payload", nullable = false, columnDefinition = "TEXT")
    private String payload;

    @PrePersist
    protected void onCreate() {
        if (failedAt == null) {
            failedAt = Instant.now();
        }
    }
}
