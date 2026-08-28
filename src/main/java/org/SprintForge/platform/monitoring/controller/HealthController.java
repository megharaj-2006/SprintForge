package org.SprintForge.platform.monitoring.controller;

import org.SprintForge.platform.monitoring.dto.HealthCheckResponse;
import org.SprintForge.platform.monitoring.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("platformHealthController")
@RequestMapping
public class HealthController {

    private final HealthService healthService;

    @Autowired
    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/health")
    public ResponseEntity<HealthCheckResponse> getHealth() {
        return ResponseEntity.ok(healthService.checkHealth());
    }

    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/health/details")
    public ResponseEntity<HealthCheckResponse> getHealthDetails() {
        return ResponseEntity.ok(healthService.checkHealth());
    }

    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Object>> getMetrics() {
        return ResponseEntity.ok(Map.of(
                "jvmMemoryUsed", 128450112L,
                "activeThreads", 16,
                "uptimeMs", 845200L
        ));
    }
}
