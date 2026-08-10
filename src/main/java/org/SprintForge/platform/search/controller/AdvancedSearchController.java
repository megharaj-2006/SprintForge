package org.SprintForge.platform.search.controller;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.search.dto.*;
import org.SprintForge.platform.search.service.AdvancedSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("platformAdvancedSearchController")
@RequestMapping("/search")
@RequiredArgsConstructor
public class AdvancedSearchController {

    private final AdvancedSearchService advancedSearchService;

    @PostMapping("/advanced")
    public ResponseEntity<List<SearchResultResponse>> advancedSearch(@RequestBody AdvancedSearchRequest request) {
        return ResponseEntity.ok(advancedSearchService.advancedSearch(request));
    }

    @PostMapping("/filter")
    public ResponseEntity<List<SearchResultResponse>> filterSearch(@RequestBody AdvancedSearchRequest request) {
        return ResponseEntity.ok(advancedSearchService.advancedSearch(request));
    }

    @PostMapping("/save")
    public ResponseEntity<SavedSearchResponse> saveSearch(@RequestBody SaveSearchRequest request) {
        return ResponseEntity.ok(advancedSearchService.saveSearch(request));
    }

    @GetMapping("/saved")
    public ResponseEntity<List<SavedSearchResponse>> getSavedSearches(
            @RequestParam("userId") String userId,
            @RequestParam(value = "workspaceId", required = false) String workspaceId) {
        return ResponseEntity.ok(advancedSearchService.getSavedSearches(userId, workspaceId));
    }
}
