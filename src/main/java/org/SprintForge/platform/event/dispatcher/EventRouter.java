package org.SprintForge.platform.event.dispatcher;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.SprintForge.platform.event.listener.PlatformEventListener;
import org.SprintForge.platform.event.registry.EventRegistry;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventRouter {

    private final EventRegistry eventRegistry;

    public List<PlatformEventListener> route(BaseDomainEvent event) {
        return eventRegistry.getListenersForEvent(event);
    }
}
