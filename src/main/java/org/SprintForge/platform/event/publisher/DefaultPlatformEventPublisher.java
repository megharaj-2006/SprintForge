package org.SprintForge.platform.event.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.event.dispatcher.EventDispatcher;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.SprintForge.platform.event.metadata.EventStatus;
import org.SprintForge.platform.event.outbox.OutboxEvent;
import org.SprintForge.platform.event.outbox.OutboxEventRepository;
import org.SprintForge.platform.event.validator.EventValidator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultPlatformEventPublisher implements PlatformEventPublisher {

    private final EventValidator eventValidator;
    private final EventDispatcher eventDispatcher;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    @Override
    public void publish(BaseDomainEvent event) {
        eventValidator.validate(event);
        log.info("[PlatformEventPublisher] Synchronously publishing event: {} (ID: {})",
                event.getEventType(), event.getEventId());
        eventDispatcher.dispatch(event);
    }

    @Override
    public void publishAsync(BaseDomainEvent event) {
        eventValidator.validate(event);
        log.info("[PlatformEventPublisher] Asynchronously publishing event: {} (ID: {})",
                event.getEventType(), event.getEventId());
        eventDispatcher.dispatchAsync(event);
    }

    @Override
    public void publishBatch(List<BaseDomainEvent> events) {
        if (events == null || events.isEmpty()) {
            return;
        }
        log.info("[PlatformEventPublisher] Publishing batch of {} events", events.size());
        for (BaseDomainEvent event : events) {
            publish(event);
        }
    }

    @Override
    public void publishDelayed(BaseDomainEvent event, Duration delay) {
        eventValidator.validate(event);
        log.info("[PlatformEventPublisher] Scheduling delayed event: {} (ID: {}) in {} ms",
                event.getEventType(), event.getEventId(), delay.toMillis());
        scheduler.schedule(() -> dispatchSafely(event), delay.toMillis(), TimeUnit.MILLISECONDS);
    }

    @SneakyThrows
    @Override
    @Transactional
    public void publishTransactional(BaseDomainEvent event) {
        eventValidator.validate(event);
        log.info("[PlatformEventPublisher] Saving transactional outbox event: {} (ID: {})",
                event.getEventType(), event.getEventId());

        String payload = objectMapper.writeValueAsString(event);
        String metadataStr = event.getMetadata() != null ? objectMapper.writeValueAsString(event.getMetadata()) : null;

        OutboxEvent outbox = OutboxEvent.builder()
                .eventId(event.getEventId())
                .eventType(event.getClass().getName())
                .aggregateType(event.getAggregateType())
                .aggregateId(event.getAggregateId())
                .payload(payload)
                .metadata(metadataStr)
                .status(EventStatus.PENDING)
                .build();

        outboxEventRepository.save(outbox);
    }

    private void dispatchSafely(BaseDomainEvent event) {
        try {
            eventDispatcher.dispatch(event);
        } catch (Exception e) {
            log.error("[PlatformEventPublisher] Error executing delayed dispatch for event {}: {}",
                    event.getEventId(), e.getMessage(), e);
        }
    }
}
