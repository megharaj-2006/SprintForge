package org.SprintForge.platform.search.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.search.repository.PlatformSearchIndexRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("platformSearchRebuildService")
@RequiredArgsConstructor
public class SearchRebuildService {

    private static final Logger log = LoggerFactory.getLogger(SearchRebuildService.class);
    private final PlatformSearchIndexRepository indexRepository;

    @Transactional
    public long rebuildFullIndex() {
        log.info("Starting full search index rebuild...");
        indexRepository.deleteAll();
        log.info("Cleared existing search index entries");
        return 0;
    }

    @Transactional
    public long rebuildEntityIndex(String entityType) {
        log.info("Rebuilding search index for entity type: {}", entityType);
        indexRepository.deleteByEntityType(entityType);
        return 0;
    }

    public String getIndexStatus() {
        return "INDEXED (" + indexRepository.count() + " items)";
    }
}
