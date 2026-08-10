package org.SprintForge.platform.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.SprintForge.platform.event.metadata.EventCategory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component("platformWebhookListener")
@Order(60)
public class WebhookListener implements PlatformEventListener {

    @Override
    public void onEvent(BaseDomainEvent event) {
        log.info("[WebhookListener] Triggering outbound webhook for event: {}", event.getEventType());
    }

    @Override
    public boolean supports(BaseDomainEvent event) {
        return event.getCategory() == EventCategory.INTEGRATION 
            || event.getCategory() == EventCategory.TASK 
            || event.getCategory() == EventCategory.PROJECT;
    }

    @Override
    public int getOrder() {
        return 60;
    }
}
