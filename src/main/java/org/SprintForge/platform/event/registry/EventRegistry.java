package org.SprintForge.platform.event.registry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.SprintForge.platform.event.listener.PlatformEventListener;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventRegistry implements SmartInitializingSingleton {

    private final ApplicationContext applicationContext;
    private final List<PlatformEventListener> registeredListeners = new CopyOnWriteArrayList<>();

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, PlatformEventListener> listenerBeans = applicationContext.getBeansOfType(PlatformEventListener.class);
        listenerBeans.values().forEach(this::registerListener);
        log.info("[EventRegistry] Total registered event listeners: {}", registeredListeners.size());
    }

    public void registerListener(PlatformEventListener listener) {
        if (!registeredListeners.contains(listener)) {
            registeredListeners.add(listener);
            registeredListeners.sort(Comparator.comparingInt(PlatformEventListener::getOrder));
            log.info("[EventRegistry] Registered listener: {} with order {}", listener.getName(), listener.getOrder());
        }
    }

    public List<PlatformEventListener> getRegisteredListeners() {
        return Collections.unmodifiableList(registeredListeners);
    }

    public List<PlatformEventListener> getListenersForEvent(BaseDomainEvent event) {
        List<PlatformEventListener> matching = new ArrayList<>();
        for (PlatformEventListener listener : registeredListeners) {
            if (listener.supports(event)) {
                matching.add(listener);
            }
        }
        matching.sort(Comparator.comparingInt(PlatformEventListener::getOrder));
        return matching;
    }
}
