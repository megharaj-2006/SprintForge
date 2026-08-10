package org.SprintForge.platform.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.platform.event.metadata.EventStatus;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventLogResponse {
    private Long id;
    private String eventId;
    private String eventType;
    private String aggregateType;
    private String aggregateId;
    private String listener;
    private EventStatus status;
    private long durationMs;
    private Instant occurredAt;
    private Instant processedAt;
    private String errorTrace;
}
