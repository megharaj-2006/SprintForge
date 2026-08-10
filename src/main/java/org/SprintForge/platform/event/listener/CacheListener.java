package org.SprintForge.platform.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component("platformCacheListener")
@Order(80)
public class CacheListener implements PlatformEventListener {

    @Override
    public void onEvent(BaseDomainEvent event) {
        log.info("[CacheListener] Invalidating cache for aggregate {} ({})", event.getAggregateType(), event.getAggregateId());
    }

    @Override
    public boolean supports(BaseDomainEvent event) {
        return true;
    }

    @Override
    public int getOrder() {
        return 80;
    }
}
