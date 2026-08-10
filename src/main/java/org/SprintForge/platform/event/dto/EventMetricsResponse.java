package org.SprintForge.platform.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventMetricsResponse {
    private long totalPublishedEvents;
    private long totalProcessedEvents;
    private long totalFailedEvents;
    private long retryCount;
    private double averageProcessingTimeMs;
    private String slowestListener;
    private long deadLetterCount;
    private Map<String, String> listenerHealth;
}
