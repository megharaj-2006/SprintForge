package org.SprintForge.platform.scheduler.dto;

import org.SprintForge.platform.scheduler.domain.JobPriority;
import org.SprintForge.platform.scheduler.domain.JobStatus;
import org.SprintForge.platform.scheduler.domain.JobType;

import java.time.Instant;

public class JobResponse {
    private Long id;
    private JobType jobType;
    private String jobName;
    private JobPriority priority;
    private JobStatus status;
    private String payload;
    private String result;
    private String createdBy;
    private String workspaceId;
    private String projectId;
    private Instant scheduledAt;
    private Instant startedAt;
    private Instant completedAt;
    private Long durationMs;
    private int retryCount;
    private int maxRetries;
    private String errorMessage;
    private String correlationId;
    private Instant createdAt;

    public JobResponse() {}

    public JobResponse(Long id, JobType jobType, String jobName, JobPriority priority, JobStatus status, String payload, String result, String createdBy, String workspaceId, String projectId, Instant scheduledAt, Instant startedAt, Instant completedAt, Long durationMs, int retryCount, int maxRetries, String errorMessage, String correlationId, Instant createdAt) {
        this.id = id;
        this.jobType = jobType;
        this.jobName = jobName;
        this.priority = priority;
        this.status = status;
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
        this.createdAt = createdAt;
    }

    public static JobResponseBuilder builder() {
        return new JobResponseBuilder();
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

    public static class JobResponseBuilder {
        private Long id;
        private JobType jobType;
        private String jobName;
        private JobPriority priority;
        private JobStatus status;
        private String payload;
        private String result;
        private String createdBy;
        private String workspaceId;
        private String projectId;
        private Instant scheduledAt;
        private Instant startedAt;
        private Instant completedAt;
        private Long durationMs;
        private int retryCount;
        private int maxRetries;
        private String errorMessage;
        private String correlationId;
        private Instant createdAt;

        public JobResponseBuilder id(Long id) { this.id = id; return this; }
        public JobResponseBuilder jobType(JobType jobType) { this.jobType = jobType; return this; }
        public JobResponseBuilder jobName(String jobName) { this.jobName = jobName; return this; }
        public JobResponseBuilder priority(JobPriority priority) { this.priority = priority; return this; }
        public JobResponseBuilder status(JobStatus status) { this.status = status; return this; }
        public JobResponseBuilder payload(String payload) { this.payload = payload; return this; }
        public JobResponseBuilder result(String result) { this.result = result; return this; }
        public JobResponseBuilder createdBy(String createdBy) { this.createdBy = createdBy; return this; }
        public JobResponseBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public JobResponseBuilder projectId(String projectId) { this.projectId = projectId; return this; }
        public JobResponseBuilder scheduledAt(Instant scheduledAt) { this.scheduledAt = scheduledAt; return this; }
        public JobResponseBuilder startedAt(Instant startedAt) { this.startedAt = startedAt; return this; }
        public JobResponseBuilder completedAt(Instant completedAt) { this.completedAt = completedAt; return this; }
        public JobResponseBuilder durationMs(Long durationMs) { this.durationMs = durationMs; return this; }
        public JobResponseBuilder retryCount(int retryCount) { this.retryCount = retryCount; return this; }
        public JobResponseBuilder maxRetries(int maxRetries) { this.maxRetries = maxRetries; return this; }
        public JobResponseBuilder errorMessage(String errorMessage) { this.errorMessage = errorMessage; return this; }
        public JobResponseBuilder correlationId(String correlationId) { this.correlationId = correlationId; return this; }
        public JobResponseBuilder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }

        public JobResponse build() {
            return new JobResponse(id, jobType, jobName, priority, status, payload, result, createdBy, workspaceId, projectId, scheduledAt, startedAt, completedAt, durationMs, retryCount, maxRetries, errorMessage, correlationId, createdAt);
        }
    }
}
