package org.SprintForge.platform.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component("platformAnalyticsListener")
@Order(50)
public class AnalyticsListener implements PlatformEventListener {

    @Override
    public void onEvent(BaseDomainEvent event) {
        log.info("[AnalyticsListener] Tracking analytics metric for event: {}", event.getEventType());
    }

    @Override
    public boolean supports(BaseDomainEvent event) {
        return true;
    }

    @Override
    public int getOrder() {
        return 50;
    }
}
