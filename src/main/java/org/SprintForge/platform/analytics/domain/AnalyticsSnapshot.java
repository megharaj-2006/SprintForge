package org.SprintForge.platform.analytics.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "PlatformAnalyticsSnapshot")
@Table(name = "platform_analytics_snapshots")
public class AnalyticsSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "snapshot_type", nullable = false)
    private String snapshotType;

    @Column(name = "metrics", columnDefinition = "TEXT")
    private String metrics;

    @Column(name = "captured_at", nullable = false)
    private Instant capturedAt = Instant.now();

    public AnalyticsSnapshot() {}

    public AnalyticsSnapshot(Long id, String workspaceId, String projectId, String snapshotType, String metrics, Instant capturedAt) {
        this.id = id;
        this.workspaceId = workspaceId;
        this.projectId = projectId;
        this.snapshotType = snapshotType;
        this.metrics = metrics;
        this.capturedAt = capturedAt != null ? capturedAt : Instant.now();
    }

    public static AnalyticsSnapshotBuilder builder() {
        return new AnalyticsSnapshotBuilder();
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

    public static class AnalyticsSnapshotBuilder {
        private Long id;
        private String workspaceId;
        private String projectId;
        private String snapshotType;
        private String metrics;
        private Instant capturedAt = Instant.now();

        public AnalyticsSnapshotBuilder id(Long id) { this.id = id; return this; }
        public AnalyticsSnapshotBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public AnalyticsSnapshotBuilder projectId(String projectId) { this.projectId = projectId; return this; }
        public AnalyticsSnapshotBuilder snapshotType(String snapshotType) { this.snapshotType = snapshotType; return this; }
        public AnalyticsSnapshotBuilder metrics(String metrics) { this.metrics = metrics; return this; }
        public AnalyticsSnapshotBuilder capturedAt(Instant capturedAt) { this.capturedAt = capturedAt; return this; }

        public AnalyticsSnapshot build() {
            return new AnalyticsSnapshot(id, workspaceId, projectId, snapshotType, metrics, capturedAt);
        }
    }
}
