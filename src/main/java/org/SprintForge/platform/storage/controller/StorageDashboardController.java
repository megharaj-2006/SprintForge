package org.SprintForge.platform.storage.controller;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.storage.dto.StorageDashboardResponse;
import org.SprintForge.platform.storage.service.StorageDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("platformStorageDashboardController")
@RequestMapping("/platform/storage")
@RequiredArgsConstructor
public class StorageDashboardController {

    private final StorageDashboardService storageDashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<StorageDashboardResponse> getDashboard() {
        return ResponseEntity.ok(storageDashboardService.getStorageMetrics());
    }
}
