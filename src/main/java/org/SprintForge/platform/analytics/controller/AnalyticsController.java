package org.SprintForge.platform.analytics.controller;

import org.SprintForge.platform.analytics.dto.AnalyticsSnapshotResponse;
import org.SprintForge.platform.analytics.dto.AnalyticsTrendResponse;
import org.SprintForge.platform.analytics.service.AnalyticsPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("platformAnalyticsController")
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsPlatformService analyticsService;

    @Autowired
    public AnalyticsController(AnalyticsPlatformService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @PostMapping("/snapshots")
    public ResponseEntity<AnalyticsSnapshotResponse> createSnapshot(
            @RequestParam("workspaceId") String workspaceId,
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam("snapshotType") String snapshotType,
            @RequestBody String metricsPayload) {
        return ResponseEntity.ok(analyticsService.captureSnapshot(workspaceId, projectId, snapshotType, metricsPayload));
    }

    @GetMapping("/snapshots")
    public ResponseEntity<List<AnalyticsSnapshotResponse>> getSnapshots(
            @RequestParam("workspaceId") String workspaceId,
            @RequestParam("snapshotType") String snapshotType,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return ResponseEntity.ok(analyticsService.getSnapshots(workspaceId, snapshotType, page, size));
    }

    @GetMapping("/trends")
    public ResponseEntity<AnalyticsTrendResponse> getTrend(
            @RequestParam("metricName") String metricName,
            @RequestParam("currentValue") double currentValue,
            @RequestParam("previousValue") double previousValue) {
        return ResponseEntity.ok(analyticsService.calculateTrend(metricName, currentValue, previousValue));
    }
}
