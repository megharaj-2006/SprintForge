package org.SprintForge.platform.scheduler.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "PlatformBatchJob")
@Table(name = "platform_batch_jobs")
public class BatchJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", nullable = false)
    private JobType jobType;

    @Column(name = "total_items", nullable = false)
    private int totalItems = 0;

    @Column(name = "processed_items", nullable = false)
    private int processedItems = 0;

    @Column(name = "failed_items", nullable = false)
    private int failedItems = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobStatus status = JobStatus.QUEUED;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    public BatchJob() {}

    public BatchJob(Long id, String name, JobType jobType, int totalItems, int processedItems, int failedItems, JobStatus status, Instant startedAt, Instant completedAt, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.jobType = jobType;
        this.totalItems = totalItems;
        this.processedItems = processedItems;
        this.failedItems = failedItems;
        this.status = status != null ? status : JobStatus.QUEUED;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.createdAt = createdAt != null ? createdAt : Instant.now();
    }

    public static BatchJobBuilder builder() {
        return new BatchJobBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public JobType getJobType() { return jobType; }
    public void setJobType(JobType jobType) { this.jobType = jobType; }

    public int getTotalItems() { return totalItems; }
    public void setTotalItems(int totalItems) { this.totalItems = totalItems; }

    public int getProcessedItems() { return processedItems; }
    public void setProcessedItems(int processedItems) { this.processedItems = processedItems; }

    public int getFailedItems() { return failedItems; }
    public void setFailedItems(int failedItems) { this.failedItems = failedItems; }

    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }

    public Instant getStartedAt() { return startedAt; }
    public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }

    public Instant getCompletedAt() { return completedAt; }
    public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public static class BatchJobBuilder {
        private Long id;
        private String name;
        private JobType jobType;
        private int totalItems = 0;
        private int processedItems = 0;
        private int failedItems = 0;
        private JobStatus status = JobStatus.QUEUED;
        private Instant startedAt;
        private Instant completedAt;
        private Instant createdAt = Instant.now();

        public BatchJobBuilder id(Long id) { this.id = id; return this; }
        public BatchJobBuilder name(String name) { this.name = name; return this; }
        public BatchJobBuilder jobType(JobType jobType) { this.jobType = jobType; return this; }
        public BatchJobBuilder totalItems(int totalItems) { this.totalItems = totalItems; return this; }
        public BatchJobBuilder processedItems(int processedItems) { this.processedItems = processedItems; return this; }
        public BatchJobBuilder failedItems(int failedItems) { this.failedItems = failedItems; return this; }
        public BatchJobBuilder status(JobStatus status) { this.status = status; return this; }
        public BatchJobBuilder startedAt(Instant startedAt) { this.startedAt = startedAt; return this; }
        public BatchJobBuilder completedAt(Instant completedAt) { this.completedAt = completedAt; return this; }
        public BatchJobBuilder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }

        public BatchJob build() {
            return new BatchJob(id, name, jobType, totalItems, processedItems, failedItems, status, startedAt, completedAt, createdAt);
        }
    }
}
