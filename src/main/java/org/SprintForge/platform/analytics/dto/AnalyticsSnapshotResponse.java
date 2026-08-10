package org.SprintForge.platform.analytics.dto;

import java.time.Instant;

public class AnalyticsSnapshotResponse {
    private Long id;
    private String workspaceId;
    private String projectId;
    private String snapshotType;
    private String metrics;
    private Instant capturedAt;

    public AnalyticsSnapshotResponse() {}

    public AnalyticsSnapshotResponse(Long id, String workspaceId, String projectId, String snapshotType, String metrics, Instant capturedAt) {
        this.id = id;
        this.workspaceId = workspaceId;
        this.projectId = projectId;
        this.snapshotType = snapshotType;
        this.metrics = metrics;
        this.capturedAt = capturedAt;
    }

    public static AnalyticsSnapshotResponseBuilder builder() {
        return new AnalyticsSnapshotResponseBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }

    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }

    public String getSnapshotType() { return snapshotType; }
    public void setSnapshotType(String snapshotType) { this.snapshotType = snapshotType; }

    public String getMetrics() { return metrics; }
    public void setMetrics(String metrics) { this.metrics = metrics; }

    public Instant getCapturedAt() { return capturedAt; }
    public void setCapturedAt(Instant capturedAt) { this.capturedAt = capturedAt; }

    public static class AnalyticsSnapshotResponseBuilder {
        private Long id;
        private String workspaceId;
        private String projectId;
        private String snapshotType;
        private String metrics;
        private Instant capturedAt;

        public AnalyticsSnapshotResponseBuilder id(Long id) { this.id = id; return this; }
        public AnalyticsSnapshotResponseBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public AnalyticsSnapshotResponseBuilder projectId(String projectId) { this.projectId = projectId; return this; }
        public AnalyticsSnapshotResponseBuilder snapshotType(String snapshotType) { this.snapshotType = snapshotType; return this; }
        public AnalyticsSnapshotResponseBuilder metrics(String metrics) { this.metrics = metrics; return this; }
        public AnalyticsSnapshotResponseBuilder capturedAt(Instant capturedAt) { this.capturedAt = capturedAt; return this; }

        public AnalyticsSnapshotResponse build() {
            return new AnalyticsSnapshotResponse(id, workspaceId, projectId, snapshotType, metrics, capturedAt);
        }
    }
}
