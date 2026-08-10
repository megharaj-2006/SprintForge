package org.SprintForge.platform.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.SprintForge.platform.event.metadata.EventCategory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component("platformSearchListener")
@Order(40)
public class SearchListener implements PlatformEventListener {

    @Override
    public void onEvent(BaseDomainEvent event) {
        log.info("[SearchListener] Indexing aggregate {} ({}) for search", event.getAggregateType(), event.getAggregateId());
    }

    @Override
    public boolean supports(BaseDomainEvent event) {
        return event.getCategory() == EventCategory.TASK 
            || event.getCategory() == EventCategory.PROJECT 
            || event.getCategory() == EventCategory.WORKSPACE 
            || event.getCategory() == EventCategory.USER;
    }

    @Override
    public int getOrder() {
        return 40;
    }
}
