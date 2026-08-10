package org.SprintForge.platform.event.dispatcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.SprintForge.platform.event.listener.PlatformEventListener;
import org.SprintForge.platform.event.logging.EventLogService;
import org.SprintForge.platform.event.metadata.EventStatus;
import org.SprintForge.platform.event.retry.RetryPolicy;
import org.SprintForge.platform.event.retry.RetryService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventDispatcher {

    private final EventRouter eventRouter;
    private final EventLogService eventLogService;
    private final RetryService retryService;

    public void dispatch(BaseDomainEvent event) {
        List<PlatformEventListener> listeners = eventRouter.route(event);
        log.info("[EventDispatcher] Routing event: {} (ID: {}) to {} listeners",
                event.getEventType(), event.getEventId(), listeners.size());

        RetryPolicy defaultPolicy = RetryPolicy.builder()
                .maxAttempts(3)
                .initialDelayMs(100)
                .backoffType(RetryPolicy.BackoffType.EXPONENTIAL)
                .build();

        for (PlatformEventListener listener : listeners) {
            long startTime = System.currentTimeMillis();
            try {
                retryService.executeWithRetry(event, listener, () -> listener.onEvent(event), defaultPolicy);
                long durationMs = System.currentTimeMillis() - startTime;
                eventLogService.logExecution(event, listener, EventStatus.COMPLETED, durationMs, null);
            } catch (Exception e) {
                long durationMs = System.currentTimeMillis() - startTime;
                log.error("[EventDispatcher] Failure delivering event {} to listener {}: {}",
                        event.getEventId(), listener.getName(), e.getMessage());
                eventLogService.logExecution(event, listener, EventStatus.FAILED, durationMs, e);
            }
        }
    }

    @Async
    public void dispatchAsync(BaseDomainEvent event) {
        dispatch(event);
    }
}
