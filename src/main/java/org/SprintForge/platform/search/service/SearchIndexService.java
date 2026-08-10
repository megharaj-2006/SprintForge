package org.SprintForge.platform.search.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.search.domain.PlatformSearchIndex;
import org.SprintForge.platform.search.repository.PlatformSearchIndexRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service("platformSearchIndexService")
@RequiredArgsConstructor
public class SearchIndexService {

    private static final Logger log = LoggerFactory.getLogger(SearchIndexService.class);
    private final PlatformSearchIndexRepository indexRepository;

    @Transactional
    public PlatformSearchIndex indexEntity(String entityType, String entityId, String workspaceId, String projectId, String title, String content, String keywords) {
        Optional<PlatformSearchIndex> existing = indexRepository.findByEntityTypeAndEntityId(entityType, entityId);
        PlatformSearchIndex index = existing.orElseGet(() -> PlatformSearchIndex.builder()
                .entityType(entityType)
                .entityId(entityId)
                .build());

        index.setWorkspaceId(workspaceId);
        index.setProjectId(projectId);
        index.setTitle(title);
        index.setContent(content);
        index.setKeywords(keywords);
        index.setLastIndexedAt(Instant.now());

        log.debug("Indexing entity: type={}, id={}, title={}", entityType, entityId, title);
        return indexRepository.save(index);
    }

    @Transactional
    public void removeEntityIndex(String entityType, String entityId) {
        indexRepository.deleteByEntityTypeAndEntityId(entityType, entityId);
        log.debug("Removed index for entity: type={}, id={}", entityType, entityId);
    }

    @Transactional
    public void archiveEntityIndex(String entityType, String entityId) {
        indexRepository.deleteByEntityTypeAndEntityId(entityType, entityId);
    }
}
