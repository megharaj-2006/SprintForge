package org.SprintForge.platform.scheduler.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "PlatformJob")
@Table(name = "platform_jobs")
public class PlatformJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", nullable = false)
    private JobType jobType;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private JobPriority priority = JobPriority.NORMAL;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobStatus status = JobStatus.QUEUED;

    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    @Column(name = "result", columnDefinition = "TEXT")
    private String result;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "scheduled_at")
    private Instant scheduledAt;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "duration_ms")
    private Long durationMs;

    @Column(name = "retry_count", nullable = false)
    private int retryCount = 0;

    @Column(name = "max_retries", nullable = false)
    private int maxRetries = 3;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "correlation_id")
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    public PlatformJob() {}

    public PlatformJob(Long id, JobType jobType, String jobName, JobPriority priority, JobStatus status, String payload, String result, String createdBy, String workspaceId, String projectId, Instant scheduledAt, Instant startedAt, Instant completedAt, Long durationMs, int retryCount, int maxRetries, String errorMessage, String correlationId, Instant createdAt) {
        this.id = id;
        this.jobType = jobType;
        this.jobName = jobName;
        this.priority = priority != null ? priority : JobPriority.NORMAL;
        this.status = status != null ? status : JobStatus.QUEUED;
        this.payload = payload;
        this.result = result;
        this.createdBy = createdBy;
        this.workspaceId = workspaceId;
        this.projectId = projectId;
        this.scheduledAt = scheduledAt;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.durationMs = durationMs;
        this.retryCount = retryCount;
        this.maxRetries = maxRetries;
        this.errorMessage = errorMessage;
        this.correlationId = correlationId;
        this.createdAt = createdAt != null ? createdAt : Instant.now();
    }

    public static PlatformJobBuilder builder() {
        return new PlatformJobBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public JobType getJobType() { return jobType; }
    public void setJobType(JobType jobType) { this.jobType = jobType; }

    public String getJobName() { return jobName; }
    public void setJobName(String jobName) { this.jobName = jobName; }

    public JobPriority getPriority() { return priority; }
    public void setPriority(JobPriority priority) { this.priority = priority; }

    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }

    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }

    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }

    public Instant getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(Instant scheduledAt) { this.scheduledAt = scheduledAt; }

    public Instant getStartedAt() { return startedAt; }
    public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }

    public Instant getCompletedAt() { return completedAt; }
    public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }

    public Long getDurationMs() { return durationMs; }
    public void setDurationMs(Long durationMs) { this.durationMs = durationMs; }

    public int getRetryCount() { return retryCount; }
    public void setRetryCount(int retryCount) { this.retryCount = retryCount; }

    public int getMaxRetries() { return maxRetries; }
    public void setMaxRetries(int maxRetries) { this.maxRetries = maxRetries; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public static class PlatformJobBuilder {
        private Long id;
        private JobType jobType;
        private String jobName;
        private JobPriority priority = JobPriority.NORMAL;
        private JobStatus status = JobStatus.QUEUED;
        private String payload;
        private String result;
        private String createdBy;
        private String workspaceId;
        private String projectId;
        private Instant scheduledAt;
        private Instant startedAt;
        private Instant completedAt;
        private Long durationMs;
        private int retryCount = 0;
        private int maxRetries = 3;
        private String errorMessage;
        private String correlationId;
        private Instant createdAt = Instant.now();

        public PlatformJobBuilder id(Long id) { this.id = id; return this; }
        public PlatformJobBuilder jobType(JobType jobType) { this.jobType = jobType; return this; }
        public PlatformJobBuilder jobName(String jobName) { this.jobName = jobName; return this; }
        public PlatformJobBuilder priority(JobPriority priority) { this.priority = priority; return this; }
        public PlatformJobBuilder status(JobStatus status) { this.status = status; return this; }
        public PlatformJobBuilder payload(String payload) { this.payload = payload; return this; }
        public PlatformJobBuilder result(String result) { this.result = result; return this; }
        public PlatformJobBuilder createdBy(String createdBy) { this.createdBy = createdBy; return this; }
        public PlatformJobBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public PlatformJobBuilder projectId(String projectId) { this.projectId = projectId; return this; }
        public PlatformJobBuilder scheduledAt(Instant scheduledAt) { this.scheduledAt = scheduledAt; return this; }
        public PlatformJobBuilder startedAt(Instant startedAt) { this.startedAt = startedAt; return this; }
        public PlatformJobBuilder completedAt(Instant completedAt) { this.completedAt = completedAt; return this; }
        public PlatformJobBuilder durationMs(Long durationMs) { this.durationMs = durationMs; return this; }
        public PlatformJobBuilder retryCount(int retryCount) { this.retryCount = retryCount; return this; }
        public PlatformJobBuilder maxRetries(int maxRetries) { this.maxRetries = maxRetries; return this; }
        public PlatformJobBuilder errorMessage(String errorMessage) { this.errorMessage = errorMessage; return this; }
        public PlatformJobBuilder correlationId(String correlationId) { this.correlationId = correlationId; return this; }
        public PlatformJobBuilder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }

        public PlatformJob build() {
            return new PlatformJob(id, jobType, jobName, priority, status, payload, result, createdBy, workspaceId, projectId, scheduledAt, startedAt, completedAt, durationMs, retryCount, maxRetries, errorMessage, correlationId, createdAt);
        }
    }
}
