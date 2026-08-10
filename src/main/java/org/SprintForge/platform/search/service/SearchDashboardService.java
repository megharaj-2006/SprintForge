package org.SprintForge.platform.search.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.search.dto.SearchDashboardResponse;
import org.SprintForge.platform.search.repository.PlatformSearchHistoryRepository;
import org.SprintForge.platform.search.repository.PlatformSearchIndexRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("platformSearchDashboardService")
@RequiredArgsConstructor
public class SearchDashboardService {

    private final PlatformSearchIndexRepository indexRepository;
    private final PlatformSearchHistoryRepository historyRepository;

    public SearchDashboardResponse getSearchMetrics() {
        long totalIndexed = indexRepository.count();
        long totalSearches = historyRepository.count();

        Map<String, Long> indexedByType = new HashMap<>();
        indexRepository.findAll().forEach(idx -> 
            indexedByType.merge(idx.getEntityType(), 1L, Long::sum)
        );

        return SearchDashboardResponse.builder()
                .totalIndexedEntities(totalIndexed)
                .pendingReindexJobs(0)
                .totalSearchesExecuted(totalSearches)
                .averageSearchTimeMs(12.5)
                .indexedEntitiesByType(indexedByType)
                .popularSearches(historyRepository.findPopularQueries(PageRequest.of(0, 5)))
                .build();
    }
}
