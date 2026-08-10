package org.SprintForge.platform.event.retry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.SprintForge.platform.event.listener.PlatformEventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetryService {

    private final DeadLetterService deadLetterService;

    public void executeWithRetry(BaseDomainEvent event, PlatformEventListener listener, Runnable task, RetryPolicy policy) {
        int attempt = 0;
        Throwable lastException = null;

        while (attempt < policy.getMaxAttempts()) {
            attempt++;
            try {
                task.run();
                if (attempt > 1) {
                    log.info("[RetryService] Successfully executed listener {} for event {} on attempt {}",
                            listener.getName(), event.getEventId(), attempt);
                }
                return;
            } catch (Throwable t) {
                lastException = t;
                log.warn("[RetryService] Attempt {}/{} failed for listener {} on event {}: {}",
                        attempt, policy.getMaxAttempts(), listener.getName(), event.getEventId(), t.getMessage());

                if (attempt < policy.getMaxAttempts()) {
                    long delay = policy.calculateDelayMs(attempt);
                    if (delay > 0) {
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
            }
        }

        // All attempts failed, send to Dead Letter Queue
        deadLetterService.recordDeadLetter(event, listener, lastException, attempt);
    }
}
