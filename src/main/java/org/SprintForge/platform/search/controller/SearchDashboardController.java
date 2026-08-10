package org.SprintForge.platform.search.controller;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.search.dto.SearchDashboardResponse;
import org.SprintForge.platform.search.service.SearchDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("platformSearchDashboardController")
@RequestMapping("/platform/search")
@RequiredArgsConstructor
public class SearchDashboardController {

    private final SearchDashboardService searchDashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<SearchDashboardResponse> getDashboard() {
        return ResponseEntity.ok(searchDashboardService.getSearchMetrics());
    }
}
