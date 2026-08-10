package org.SprintForge.platform.scheduler.dto;

import org.SprintForge.platform.scheduler.domain.JobType;

import java.time.Instant;

public class ScheduleJobRequest {
    private String name;
    private String cronExpression;
    private JobType jobType;
    private String payload;
    private String createdBy;
    private Instant scheduledAt;

    public ScheduleJobRequest() {}

    public ScheduleJobRequest(String name, String cronExpression, JobType jobType, String payload, String createdBy, Instant scheduledAt) {
        this.name = name;
        this.cronExpression = cronExpression;
        this.jobType = jobType;
        this.payload = payload;
        this.createdBy = createdBy;
        this.scheduledAt = scheduledAt;
    }

    public static ScheduleJobRequestBuilder builder() {
        return new ScheduleJobRequestBuilder();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCronExpression() { return cronExpression; }
    public void setCronExpression(String cronExpression) { this.cronExpression = cronExpression; }

    public JobType getJobType() { return jobType; }
    public void setJobType(JobType jobType) { this.jobType = jobType; }

    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public Instant getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(Instant scheduledAt) { this.scheduledAt = scheduledAt; }

    public static class ScheduleJobRequestBuilder {
        private String name;
        private String cronExpression;
        private JobType jobType;
        private String payload;
        private String createdBy;
        private Instant scheduledAt;

        public ScheduleJobRequestBuilder name(String name) { this.name = name; return this; }
        public ScheduleJobRequestBuilder cronExpression(String cronExpression) { this.cronExpression = cronExpression; return this; }
        public ScheduleJobRequestBuilder jobType(JobType jobType) { this.jobType = jobType; return this; }
        public ScheduleJobRequestBuilder payload(String payload) { this.payload = payload; return this; }
        public ScheduleJobRequestBuilder createdBy(String createdBy) { this.createdBy = createdBy; return this; }
        public ScheduleJobRequestBuilder scheduledAt(Instant scheduledAt) { this.scheduledAt = scheduledAt; return this; }

        public ScheduleJobRequest build() {
            return new ScheduleJobRequest(name, cronExpression, jobType, payload, createdBy, scheduledAt);
        }
    }
}
