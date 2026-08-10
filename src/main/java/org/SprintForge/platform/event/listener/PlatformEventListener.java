package org.SprintForge.platform.event.listener;

import org.SprintForge.platform.event.event.BaseDomainEvent;

public interface PlatformEventListener {

    void onEvent(BaseDomainEvent event);

    boolean supports(BaseDomainEvent event);

    default int getOrder() {
        return 0;
    }

    default String getName() {
        return getClass().getSimpleName();
    }
}
