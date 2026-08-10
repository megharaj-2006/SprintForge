package org.SprintForge.platform.event.validator;

import org.SprintForge.platform.event.event.BaseDomainEvent;

public interface EventValidator {
    void validate(BaseDomainEvent event);
}
