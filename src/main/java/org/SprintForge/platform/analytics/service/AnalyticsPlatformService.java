package org.SprintForge.platform.analytics.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.analytics.domain.AnalyticsSnapshot;
import org.SprintForge.platform.analytics.dto.AnalyticsSnapshotResponse;
import org.SprintForge.platform.analytics.dto.AnalyticsTrendResponse;
import org.SprintForge.platform.analytics.repository.PlatformAnalyticsSnapshotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("platformAnalyticsPlatformService")
public class AnalyticsPlatformService {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsPlatformService.class);
    private final PlatformAnalyticsSnapshotRepository snapshotRepository;

    @Autowired
    public AnalyticsPlatformService(PlatformAnalyticsSnapshotRepository snapshotRepository) {
        this.snapshotRepository = snapshotRepository;
    }

    @Transactional
    public AnalyticsSnapshotResponse captureSnapshot(String workspaceId, String projectId, String snapshotType, String metricsPayload) {
        AnalyticsSnapshot snapshot = AnalyticsSnapshot.builder()
                .workspaceId(workspaceId)
                .projectId(projectId)
                .snapshotType(snapshotType)
                .metrics(metricsPayload)
                .build();

        AnalyticsSnapshot saved = snapshotRepository.save(snapshot);
        log.info("Captured analytics snapshot id={} type={} workspaceId={}", saved.getId(), snapshotType, workspaceId);
        return toSnapshotResponse(saved);
    }

    public List<AnalyticsSnapshotResponse> getSnapshots(String workspaceId, String snapshotType, int page, int size) {
        return snapshotRepository.findByWorkspaceIdAndSnapshotType(workspaceId, snapshotType, PageRequest.of(page, size)).stream()
                .map(this::toSnapshotResponse)
                .collect(Collectors.toList());
    }

    public AnalyticsTrendResponse calculateTrend(String metricName, double currentValue, double previousValue) {
        double diff = currentValue - previousValue;
        double pctChange = previousValue != 0 ? (diff / previousValue) * 100.0 : 0.0;
        String direction = diff > 0 ? "UP" : (diff < 0 ? "DOWN" : "STABLE");

        return AnalyticsTrendResponse.builder()
                .metricName(metricName)
                .currentValue(currentValue)
                .previousValue(previousValue)
                .percentageChange(pctChange)
                .trendDirection(direction)
                .details(Map.of("difference", diff))
                .build();
    }

    private AnalyticsSnapshotResponse toSnapshotResponse(AnalyticsSnapshot snapshot) {
        return AnalyticsSnapshotResponse.builder()
                .id(snapshot.getId())
                .workspaceId(snapshot.getWorkspaceId())
                .projectId(snapshot.getProjectId())
                .snapshotType(snapshot.getSnapshotType())
                .metrics(snapshot.getMetrics())
                .capturedAt(snapshot.getCapturedAt())
                .build();
    }
}
