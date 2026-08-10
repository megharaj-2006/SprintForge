package org.SprintForge.platform.event.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.event.dispatcher.EventDispatcher;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.SprintForge.platform.event.metadata.EventStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxProcessor {

    private final OutboxEventRepository outboxEventRepository;
    private final EventDispatcher eventDispatcher;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 2000)
    @Transactional
    public void processOutbox() {
        List<OutboxEvent> pendingEvents = outboxEventRepository.findByStatusOrderByCreatedAtAsc(
                EventStatus.PENDING, PageRequest.of(0, 50));

        if (pendingEvents.isEmpty()) {
            return;
        }

        log.info("[OutboxProcessor] Found {} pending outbox events to process", pendingEvents.size());

        for (OutboxEvent outbox : pendingEvents) {
            try {
                Class<?> eventClass = Class.forName(outbox.getEventType());
                BaseDomainEvent event = (BaseDomainEvent) objectMapper.readValue(outbox.getPayload(), eventClass);

                eventDispatcher.dispatch(event);

                outbox.setStatus(EventStatus.COMPLETED);
                outbox.setProcessedAt(Instant.now());
            } catch (ClassNotFoundException cnfe) {
                log.error("[OutboxProcessor] Event class not found for eventType: {}", outbox.getEventType(), cnfe);
                outbox.setStatus(EventStatus.FAILED);
                outbox.setLastError("Class not found: " + cnfe.getMessage());
            } catch (Exception e) {
                log.error("[OutboxProcessor] Error processing outbox event ID {}: {}", outbox.getId(), e.getMessage());
                outbox.setRetryCount(outbox.getRetryCount() + 1);
                outbox.setLastError(e.getMessage());
                if (outbox.getRetryCount() >= 3) {
                    outbox.setStatus(EventStatus.FAILED);
                }
            }
            outboxEventRepository.save(outbox);
        }
    }
}
