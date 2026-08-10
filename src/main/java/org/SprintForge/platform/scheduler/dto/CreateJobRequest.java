package org.SprintForge.platform.scheduler.dto;

import org.SprintForge.platform.scheduler.domain.JobPriority;
import org.SprintForge.platform.scheduler.domain.JobType;

public class CreateJobRequest {
    private JobType jobType;
    private String jobName;
    private JobPriority priority = JobPriority.NORMAL;
    private String payload;
    private String createdBy;
    private String workspaceId;
    private String projectId;
    private int maxRetries = 3;
    private String correlationId;

    public CreateJobRequest() {}

    public CreateJobRequest(JobType jobType, String jobName, JobPriority priority, String payload, String createdBy, String workspaceId, String projectId, int maxRetries, String correlationId) {
        this.jobType = jobType;
        this.jobName = jobName;
        this.priority = priority != null ? priority : JobPriority.NORMAL;
        this.payload = payload;
        this.createdBy = createdBy;
        this.workspaceId = workspaceId;
        this.projectId = projectId;
        this.maxRetries = maxRetries;
        this.correlationId = correlationId;
    }

    public static CreateJobRequestBuilder builder() {
        return new CreateJobRequestBuilder();
    }

    public JobType getJobType() { return jobType; }
    public void setJobType(JobType jobType) { this.jobType = jobType; }

    public String getJobName() { return jobName; }
    public void setJobName(String jobName) { this.jobName = jobName; }

    public JobPriority getPriority() { return priority; }
    public void setPriority(JobPriority priority) { this.priority = priority; }

    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }

    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }

    public int getMaxRetries() { return maxRetries; }
    public void setMaxRetries(int maxRetries) { this.maxRetries = maxRetries; }

    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }

    public static class CreateJobRequestBuilder {
        private JobType jobType;
        private String jobName;
        private JobPriority priority = JobPriority.NORMAL;
        private String payload;
        private String createdBy;
        private String workspaceId;
        private String projectId;
        private int maxRetries = 3;
        private String correlationId;

        public CreateJobRequestBuilder jobType(JobType jobType) { this.jobType = jobType; return this; }
        public CreateJobRequestBuilder jobName(String jobName) { this.jobName = jobName; return this; }
        public CreateJobRequestBuilder priority(JobPriority priority) { this.priority = priority; return this; }
        public CreateJobRequestBuilder payload(String payload) { this.payload = payload; return this; }
        public CreateJobRequestBuilder createdBy(String createdBy) { this.createdBy = createdBy; return this; }
        public CreateJobRequestBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public CreateJobRequestBuilder projectId(String projectId) { this.projectId = projectId; return this; }
        public CreateJobRequestBuilder maxRetries(int maxRetries) { this.maxRetries = maxRetries; return this; }
        public CreateJobRequestBuilder correlationId(String correlationId) { this.correlationId = correlationId; return this; }

        public CreateJobRequest build() {
            return new CreateJobRequest(jobType, jobName, priority, payload, createdBy, workspaceId, projectId, maxRetries, correlationId);
        }
    }
}
