package org.SprintForge.platform.search.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.search.domain.PlatformSearchHistory;
import org.SprintForge.platform.search.domain.PlatformSearchIndex;
import org.SprintForge.platform.search.dto.*;
import org.SprintForge.platform.search.repository.PlatformSearchHistoryRepository;
import org.SprintForge.platform.search.repository.PlatformSearchIndexRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("platformGlobalSearchService")
@RequiredArgsConstructor
public class GlobalSearchService {

    private final PlatformSearchIndexRepository indexRepository;
    private final PlatformSearchHistoryRepository historyRepository;

    @Transactional
    public List<SearchResultResponse> search(GlobalSearchRequest request) {
        long startTime = System.currentTimeMillis();
        List<PlatformSearchIndex> matches = indexRepository.searchGlobal(
                request.getQuery(),
                request.getWorkspaceId(),
                PageRequest.of(request.getPage(), request.getSize())
        );
        long duration = System.currentTimeMillis() - startTime;

        if (request.getUserId() != null && request.getQuery() != null && !request.getQuery().isBlank()) {
            historyRepository.save(PlatformSearchHistory.builder()
                    .userId(request.getUserId())
                    .workspaceId(request.getWorkspaceId())
                    .queryText(request.getQuery())
                    .executionTimeMs(duration)
                    .resultCount(matches.size())
                    .build());
        }

        return matches.stream()
                .map(this::toSearchResultResponse)
                .collect(Collectors.toList());
    }

    public SearchSuggestionResponse getSuggestions(SearchSuggestionRequest request) {
        List<String> suggestions = indexRepository.findSuggestions(
                request.getPrefix(),
                PageRequest.of(0, request.getLimit())
        );
        return SearchSuggestionResponse.builder()
                .prefix(request.getPrefix())
                .suggestions(suggestions)
                .build();
    }

    public List<SearchHistoryResponse> getUserHistory(String userId, int limit) {
        return historyRepository.findByUserIdOrderBySearchedAtDesc(userId, PageRequest.of(0, limit))
                .stream()
                .map(h -> SearchHistoryResponse.builder()
                        .id(h.getId())
                        .userId(h.getUserId())
                        .workspaceId(h.getWorkspaceId())
                        .queryText(h.getQueryText())
                        .resultCount(h.getResultCount())
                        .executionTimeMs(h.getExecutionTimeMs())
                        .searchedAt(h.getSearchedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public List<String> getPopularQueries(int limit) {
        return historyRepository.findPopularQueries(PageRequest.of(0, limit));
    }

    private SearchResultResponse toSearchResultResponse(PlatformSearchIndex index) {
        return SearchResultResponse.builder()
                .id(index.getId())
                .entityType(index.getEntityType())
                .entityId(index.getEntityId())
                .workspaceId(index.getWorkspaceId())
                .projectId(index.getProjectId())
                .title(index.getTitle())
                .contentSnippet(index.getContent() != null && index.getContent().length() > 100 ? index.getContent().substring(0, 100) + "..." : index.getContent())
                .highlightedTitle("<em>" + index.getTitle() + "</em>")
                .relevanceScore(1.0)
                .lastIndexedAt(index.getLastIndexedAt())
                .build();
    }
}
