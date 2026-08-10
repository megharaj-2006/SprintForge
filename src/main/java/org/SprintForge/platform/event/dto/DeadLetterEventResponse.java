package org.SprintForge.platform.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeadLetterEventResponse {
    private Long id;
    private String originalEventId;
    private String eventType;
    private String aggregateType;
    private String aggregateId;
    private String listener;
    private String reason;
    private int attempts;
    private Instant failedAt;
    private String payload;
}
