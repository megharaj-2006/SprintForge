package org.SprintForge.platform.event.monitoring;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.event.dto.EventHealthResponse;
import org.SprintForge.platform.event.metadata.EventStatus;
import org.SprintForge.platform.event.outbox.OutboxEventRepository;
import org.SprintForge.platform.event.registry.EventRegistry;
import org.SprintForge.platform.event.retry.DeadLetterEventRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventMonitoringService {

    private final EventRegistry eventRegistry;
    private final DeadLetterEventRepository deadLetterEventRepository;
    private final OutboxEventRepository outboxEventRepository;

    @Transactional(readOnly = true)
    public EventHealthResponse getHealthStatus() {
        long dlqSize = deadLetterEventRepository.count();
        long pendingOutbox = outboxEventRepository.findByStatusOrderByCreatedAtAsc(EventStatus.PENDING, Pageable.unpaged()).size();

        Map<String, String> listenersStatus = new HashMap<>();
        eventRegistry.getRegisteredListeners().forEach(listener ->
                listenersStatus.put(listener.getName(), "HEALTHY")
        );

        String overallStatus = (dlqSize > 10 || pendingOutbox > 50) ? "DEGRADED" : "UP";

        return EventHealthResponse.builder()
                .status(overallStatus)
                .registeredListenersCount(eventRegistry.getRegisteredListeners().size())
                .deadLetterQueueSize(dlqSize)
                .pendingOutboxEvents(pendingOutbox)
                .listenersStatus(listenersStatus)
                .build();
    }
}
