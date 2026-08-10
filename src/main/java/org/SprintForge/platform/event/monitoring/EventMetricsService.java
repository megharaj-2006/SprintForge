package org.SprintForge.platform.event.monitoring;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.event.dto.EventMetricsResponse;
import org.SprintForge.platform.event.logging.EventLog;
import org.SprintForge.platform.event.logging.EventLogRepository;
import org.SprintForge.platform.event.metadata.EventStatus;
import org.SprintForge.platform.event.registry.EventRegistry;
import org.SprintForge.platform.event.retry.DeadLetterEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventMetricsService {

    private final EventLogRepository eventLogRepository;
    private final DeadLetterEventRepository deadLetterEventRepository;
    private final EventRegistry eventRegistry;

    @Transactional(readOnly = true)
    public EventMetricsResponse getMetrics() {
        List<EventLog> allLogs = eventLogRepository.findAll();
        long totalLogs = allLogs.size();
        long failedLogs = allLogs.stream().filter(l -> l.getStatus() == EventStatus.FAILED).count();
        long completedLogs = allLogs.stream().filter(l -> l.getStatus() == EventStatus.COMPLETED).count();

        double avgTimeMs = allLogs.isEmpty() ? 0.0 :
                allLogs.stream().mapToLong(EventLog::getDurationMs).average().orElse(0.0);

        String slowestListener = allLogs.stream()
                .max((l1, l2) -> Long.compare(l1.getDurationMs(), l2.getDurationMs()))
                .map(EventLog::getListener)
                .orElse("None");

        long dlqCount = deadLetterEventRepository.count();

        Map<String, String> listenerHealth = new HashMap<>();
        eventRegistry.getRegisteredListeners().forEach(listener ->
                listenerHealth.put(listener.getName(), "UP")
        );

        return EventMetricsResponse.builder()
                .totalPublishedEvents(totalLogs)
                .totalProcessedEvents(completedLogs)
                .totalFailedEvents(failedLogs)
                .retryCount(failedLogs)
                .averageProcessingTimeMs(avgTimeMs)
                .slowestListener(slowestListener)
                .deadLetterCount(dlqCount)
                .listenerHealth(listenerHealth)
                .build();
    }
}
