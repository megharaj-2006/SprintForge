package org.SprintForge.platform.scheduler.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "PlatformScheduledJob")
@Table(name = "platform_scheduled_jobs")
public class ScheduledJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cron_expression", nullable = false)
    private String cronExpression;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Column(name = "next_execution")
    private Instant nextExecution;

    @Column(name = "last_execution")
    private Instant lastExecution;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", nullable = false)
    private JobType jobType;

    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    public ScheduledJob() {}

    public ScheduledJob(Long id, String name, String cronExpression, boolean enabled, Instant nextExecution, Instant lastExecution, JobType jobType, String payload, String createdBy, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.cronExpression = cronExpression;
        this.enabled = enabled;
        this.nextExecution = nextExecution;
        this.lastExecution = lastExecution;
        this.jobType = jobType;
        this.payload = payload;
        this.createdBy = createdBy;
        this.createdAt = createdAt != null ? createdAt : Instant.now();
    }

    public static ScheduledJobBuilder builder() {
        return new ScheduledJobBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCronExpression() { return cronExpression; }
    public void setCronExpression(String cronExpression) { this.cronExpression = cronExpression; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public Instant getNextExecution() { return nextExecution; }
    public void setNextExecution(Instant nextExecution) { this.nextExecution = nextExecution; }

    public Instant getLastExecution() { return lastExecution; }
    public void setLastExecution(Instant lastExecution) { this.lastExecution = lastExecution; }

    public JobType getJobType() { return jobType; }
    public void setJobType(JobType jobType) { this.jobType = jobType; }

    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public static class ScheduledJobBuilder {
        private Long id;
        private String name;
        private String cronExpression;
        private boolean enabled = true;
        private Instant nextExecution;
        private Instant lastExecution;
        private JobType jobType;
        private String payload;
        private String createdBy;
        private Instant createdAt = Instant.now();

        public ScheduledJobBuilder id(Long id) { this.id = id; return this; }
        public ScheduledJobBuilder name(String name) { this.name = name; return this; }
        public ScheduledJobBuilder cronExpression(String cronExpression) { this.cronExpression = cronExpression; return this; }
        public ScheduledJobBuilder enabled(boolean enabled) { this.enabled = enabled; return this; }
        public ScheduledJobBuilder nextExecution(Instant nextExecution) { this.nextExecution = nextExecution; return this; }
        public ScheduledJobBuilder lastExecution(Instant lastExecution) { this.lastExecution = lastExecution; return this; }
        public ScheduledJobBuilder jobType(JobType jobType) { this.jobType = jobType; return this; }
        public ScheduledJobBuilder payload(String payload) { this.payload = payload; return this; }
        public ScheduledJobBuilder createdBy(String createdBy) { this.createdBy = createdBy; return this; }
        public ScheduledJobBuilder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }

        public ScheduledJob build() {
            return new ScheduledJob(id, name, cronExpression, enabled, nextExecution, lastExecution, jobType, payload, createdBy, createdAt);
        }
    }
}
