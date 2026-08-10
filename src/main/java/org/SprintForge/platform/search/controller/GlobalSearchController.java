package org.SprintForge.platform.search.controller;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.search.dto.*;
import org.SprintForge.platform.search.service.GlobalSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("platformGlobalSearchController")
@RequestMapping("/search")
@RequiredArgsConstructor
public class GlobalSearchController {

    private final GlobalSearchService globalSearchService;

    @GetMapping
    public ResponseEntity<List<SearchResultResponse>> search(
            @RequestParam("q") String query,
            @RequestParam(value = "workspaceId", required = false) String workspaceId,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        GlobalSearchRequest request = GlobalSearchRequest.builder()
                .query(query)
                .workspaceId(workspaceId)
                .userId(userId)
                .page(page)
                .size(size)
                .build();
        return ResponseEntity.ok(globalSearchService.search(request));
    }

    @PostMapping("/global")
    public ResponseEntity<List<SearchResultResponse>> searchGlobal(@RequestBody GlobalSearchRequest request) {
        return ResponseEntity.ok(globalSearchService.search(request));
    }

    @GetMapping("/suggestions")
    public ResponseEntity<SearchSuggestionResponse> getSuggestions(
            @RequestParam("prefix") String prefix,
            @RequestParam(value = "workspaceId", required = false) String workspaceId,
            @RequestParam(value = "limit", defaultValue = "5") int limit) {
        SearchSuggestionRequest request = SearchSuggestionRequest.builder()
                .prefix(prefix)
                .workspaceId(workspaceId)
                .limit(limit)
                .build();
        return ResponseEntity.ok(globalSearchService.getSuggestions(request));
    }

    @GetMapping("/history")
    public ResponseEntity<List<SearchHistoryResponse>> getHistory(
            @RequestParam("userId") String userId,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(globalSearchService.getUserHistory(userId, limit));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<String>> getPopularQueries(@RequestParam(value = "limit", defaultValue = "5") int limit) {
        return ResponseEntity.ok(globalSearchService.getPopularQueries(limit));
    }
}
