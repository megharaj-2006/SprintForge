package org.SprintForge.platform.event.publisher;

import org.SprintForge.platform.event.event.BaseDomainEvent;

import java.time.Duration;
import java.util.List;

public interface PlatformEventPublisher {

    void publish(BaseDomainEvent event);

    void publishAsync(BaseDomainEvent event);

    void publishBatch(List<BaseDomainEvent> events);

    void publishDelayed(BaseDomainEvent event, Duration delay);

    void publishTransactional(BaseDomainEvent event);
}
