package org.SprintForge.platform.event.validator;

import org.SprintForge.common.exception.BadRequestException;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DefaultEventValidator implements EventValidator {

    private final Set<String> processedEventIds = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Override
    public void validate(BaseDomainEvent event) {
        if (event == null) {
            throw new BadRequestException("Event cannot be null");
        }

        if (event.getEventId() == null || event.getEventId().isBlank()) {
            throw new BadRequestException("Event ID is required");
        }

        if (!processedEventIds.add(event.getEventId())) {
            throw new BadRequestException("Duplicate Event ID detected: " + event.getEventId());
        }

        if (event.getAggregateType() == null || event.getAggregateType().isBlank()) {
            throw new BadRequestException("Aggregate type is required");
        }

        if (event.getAggregateId() == null || event.getAggregateId().isBlank()) {
            throw new BadRequestException("Aggregate ID is required");
        }

        if (event.getEventType() == null || event.getEventType().isBlank()) {
            throw new BadRequestException("Event type is required");
        }
    }
}
