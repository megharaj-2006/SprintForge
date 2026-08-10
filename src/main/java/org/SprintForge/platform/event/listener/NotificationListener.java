package org.SprintForge.platform.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.SprintForge.platform.event.metadata.EventCategory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component("platformNotificationListener")
@Order(10)
public class NotificationListener implements PlatformEventListener {

    @Override
    public void onEvent(BaseDomainEvent event) {
        log.info("[NotificationListener] Processing notification for event: {} (ID: {})", event.getEventType(), event.getEventId());
    }

    @Override
    public boolean supports(BaseDomainEvent event) {
        return event.getCategory() == EventCategory.TASK 
            || event.getCategory() == EventCategory.PROJECT 
            || event.getCategory() == EventCategory.WORKSPACE 
            || event.getCategory() == EventCategory.NOTIFICATION;
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
