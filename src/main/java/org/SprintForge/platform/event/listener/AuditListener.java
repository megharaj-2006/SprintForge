package org.SprintForge.platform.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component("platformAuditListener")
@Order(30)
public class AuditListener implements PlatformEventListener {

    @Override
    public void onEvent(BaseDomainEvent event) {
        log.info("[AuditListener] Auditing event: {} by user: {}", event.getEventType(), event.getTriggeredBy());
    }

    @Override
    public boolean supports(BaseDomainEvent event) {
        return true;
    }

    @Override
    public int getOrder() {
        return 30;
    }
}
