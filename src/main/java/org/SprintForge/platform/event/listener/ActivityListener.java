package org.SprintForge.platform.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component("platformActivityListener")
@Order(20)
public class ActivityListener implements PlatformEventListener {

    @Override
    public void onEvent(BaseDomainEvent event) {
        log.info("[ActivityListener] Recording activity log for event: {} (Aggregate: {})", event.getEventType(), event.getAggregateId());
    }

    @Override
    public boolean supports(BaseDomainEvent event) {
        return true;
    }

    @Override
    public int getOrder() {
        return 20;
    }
}
