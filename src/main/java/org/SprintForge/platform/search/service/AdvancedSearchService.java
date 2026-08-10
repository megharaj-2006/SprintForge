package org.SprintForge.platform.search.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.search.domain.PlatformSavedSearch;
import org.SprintForge.platform.search.domain.PlatformSearchIndex;
import org.SprintForge.platform.search.dto.*;
import org.SprintForge.platform.search.repository.PlatformSavedSearchRepository;
import org.SprintForge.platform.search.repository.PlatformSearchIndexRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("platformAdvancedSearchService")
@RequiredArgsConstructor
public class AdvancedSearchService {

    private final PlatformSearchIndexRepository indexRepository;
    private final PlatformSavedSearchRepository savedSearchRepository;
    private final SpecificationBuilderService specificationBuilderService;

    public List<SearchResultResponse> advancedSearch(AdvancedSearchRequest request) {
        Specification<PlatformSearchIndex> spec = specificationBuilderService.buildSpecification(
                request.getQuery(),
                request.getWorkspaceId(),
                request.getProjectId(),
                request.getFilters(),
                request.getMatchMode()
        );

        Sort sort = Sort.by(Sort.Direction.DESC, "lastIndexedAt");
        if ("ALPHABETICAL".equalsIgnoreCase(request.getSortBy())) {
            sort = Sort.by(Sort.Direction.ASC, "title");
        } else if ("OLDEST".equalsIgnoreCase(request.getSortBy())) {
            sort = Sort.by(Sort.Direction.ASC, "lastIndexedAt");
        }

        List<PlatformSearchIndex> matches = indexRepository.findAll(spec, PageRequest.of(request.getPage(), request.getSize(), sort)).getContent();

        return matches.stream()
                .map(this::toSearchResultResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public SavedSearchResponse saveSearch(SaveSearchRequest request) {
        PlatformSavedSearch saved = PlatformSavedSearch.builder()
                .name(request.getName())
                .queryText(request.getQueryText())
                .workspaceId(request.getWorkspaceId())
                .userId(request.getUserId())
                .isShared(request.isShared())
                .filters(request.getFilters() != null ? request.getFilters().toString() : null)
                .build();

        PlatformSavedSearch savedEntity = savedSearchRepository.save(saved);
        return SavedSearchResponse.builder()
                .id(savedEntity.getId())
                .name(savedEntity.getName())
                .queryText(savedEntity.getQueryText())
                .filters(savedEntity.getFilters())
                .isShared(savedEntity.isShared())
                .createdAt(savedEntity.getCreatedAt())
                .build();
    }

    public List<SavedSearchResponse> getSavedSearches(String userId, String workspaceId) {
        return savedSearchRepository.findUserAndSharedSearches(userId, workspaceId).stream()
                .map(s -> SavedSearchResponse.builder()
                        .id(s.getId())
                        .name(s.getName())
                        .queryText(s.getQueryText())
                        .filters(s.getFilters())
                        .isShared(s.isShared())
                        .createdAt(s.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    private SearchResultResponse toSearchResultResponse(PlatformSearchIndex index) {
        return SearchResultResponse.builder()
                .id(index.getId())
                .entityType(index.getEntityType())
                .entityId(index.getEntityId())
                .workspaceId(index.getWorkspaceId())
                .projectId(index.getProjectId())
                .title(index.getTitle())
                .contentSnippet(index.getContent())
                .relevanceScore(1.0)
                .lastIndexedAt(index.getLastIndexedAt())
                .build();
    }
}
