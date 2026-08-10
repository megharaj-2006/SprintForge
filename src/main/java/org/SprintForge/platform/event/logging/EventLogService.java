package org.SprintForge.platform.event.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.SprintForge.platform.event.listener.PlatformEventListener;
import org.SprintForge.platform.event.metadata.EventStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventLogService {

    private final EventLogRepository eventLogRepository;

    @Transactional
    public EventLog logExecution(BaseDomainEvent event, PlatformEventListener listener, EventStatus status, long durationMs, Throwable throwable) {
        EventLog eventLog = EventLog.builder()
                .eventId(event.getEventId())
                .eventType(event.getEventType())
                .aggregateType(event.getAggregateType())
                .aggregateId(event.getAggregateId())
                .listener(listener.getName())
                .status(status)
                .durationMs(durationMs)
                .occurredAt(event.getOccurredAt() != null ? event.getOccurredAt() : Instant.now())
                .processedAt(Instant.now())
                .errorTrace(throwable != null ? (throwable.getMessage() != null ? throwable.getMessage() : throwable.toString()) : null)
                .build();

        return eventLogRepository.save(eventLog);
    }

    @Transactional(readOnly = true)
    public List<EventLog> getFailedLogs() {
        return eventLogRepository.findByStatus(EventStatus.FAILED);
    }

    @Transactional(readOnly = true)
    public List<EventLog> getLogsByType(String eventType) {
        return eventLogRepository.findByEventType(eventType);
    }

    @Transactional(readOnly = true)
    public List<EventLog> getLogsByAggregate(String aggregateType, String aggregateId) {
        return eventLogRepository.findByAggregateTypeAndAggregateId(aggregateType, aggregateId);
    }

    @Transactional(readOnly = true)
    public List<EventLog> getSlowEvents(long minDurationMs, int limit) {
        return eventLogRepository.findSlowEvents(minDurationMs, PageRequest.of(0, limit));
    }

    @Transactional(readOnly = true)
    public List<EventLog> getRecentLogs(int limit) {
        return eventLogRepository.findRecent(PageRequest.of(0, limit));
    }

    @Transactional(readOnly = true)
    public EventLog getLogById(Long id) {
        return eventLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EventLog", "id", id));
    }
}
