package org.SprintForge.platform.search.listener;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.SprintForge.platform.event.listener.PlatformEventListener;
import org.SprintForge.platform.search.service.SearchIndexer;
import org.springframework.stereotype.Component;

@Component("platformSearchIndexEventListener")
@RequiredArgsConstructor
public class SearchIndexEventListener implements PlatformEventListener {

    private static final Logger log = LoggerFactory.getLogger(SearchIndexEventListener.class);
    private final SearchIndexer searchIndexer;

    @Override
    public void onEvent(BaseDomainEvent event) {
        log.debug("Search index listener received event: {}", event.getEventType());
        try {
            searchIndexer.processEvent(event);
        } catch (Exception e) {
            log.error("Failed to process event for search indexing: {}", event.getEventId(), e);
        }
    }

    @Override
    public boolean supports(BaseDomainEvent event) {
        return true;
    }
}
