package org.SprintForge.platform.event.monitoring;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.dto.ApiResponse;
import org.SprintForge.platform.event.dto.EventHealthResponse;
import org.SprintForge.platform.event.dto.EventMetricsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platform/events")
@RequiredArgsConstructor
public class EventMonitoringController {

    private final EventMetricsService eventMetricsService;
    private final EventMonitoringService eventMonitoringService;

    @GetMapping("/metrics")
    public ResponseEntity<ApiResponse<EventMetricsResponse>> getMetrics() {
        EventMetricsResponse metrics = eventMetricsService.getMetrics();
        return ResponseEntity.ok(ApiResponse.success(metrics));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<EventHealthResponse>> getHealth() {
        EventHealthResponse health = eventMonitoringService.getHealthStatus();
        return ResponseEntity.ok(ApiResponse.success(health));
    }
}
