package org.SprintForge.platform.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.SprintForge.platform.event.metadata.EventCategory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component("platformAutomationListener")
@Order(70)
public class AutomationListener implements PlatformEventListener {

    @Override
    public void onEvent(BaseDomainEvent event) {
        log.info("[AutomationListener] Evaluating automation rules for event: {}", event.getEventType());
    }

    @Override
    public boolean supports(BaseDomainEvent event) {
        return event.getCategory() == EventCategory.AUTOMATION 
            || event.getCategory() == EventCategory.TASK 
            || event.getCategory() == EventCategory.PROJECT;
    }

    @Override
    public int getOrder() {
        return 70;
    }
}
