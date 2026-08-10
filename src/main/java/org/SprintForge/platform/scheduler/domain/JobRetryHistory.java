package org.SprintForge.platform.scheduler.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "PlatformJobRetryHistory")
@Table(name = "platform_job_retry_history")
public class JobRetryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private PlatformJob job;

    @Column(name = "attempt", nullable = false)
    private int attempt;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @Column(name = "error", columnDefinition = "TEXT")
    private String error;

    @Column(name = "duration_ms")
    private Long durationMs;

    public JobRetryHistory() {}

    public JobRetryHistory(Long id, PlatformJob job, int attempt, Instant startedAt, Instant finishedAt, String error, Long durationMs) {
        this.id = id;
        this.job = job;
        this.attempt = attempt;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.error = error;
        this.durationMs = durationMs;
    }

    public static JobRetryHistoryBuilder builder() {
        return new JobRetryHistoryBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PlatformJob getJob() { return job; }
    public void setJob(PlatformJob job) { this.job = job; }

    public int getAttempt() { return attempt; }
    public void setAttempt(int attempt) { this.attempt = attempt; }

    public Instant getStartedAt() { return startedAt; }
    public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }

    public Instant getFinishedAt() { return finishedAt; }
    public void setFinishedAt(Instant finishedAt) { this.finishedAt = finishedAt; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public Long getDurationMs() { return durationMs; }
    public void setDurationMs(Long durationMs) { this.durationMs = durationMs; }

    public static class JobRetryHistoryBuilder {
        private Long id;
        private PlatformJob job;
        private int attempt;
        private Instant startedAt;
        private Instant finishedAt;
        private String error;
        private Long durationMs;

        public JobRetryHistoryBuilder id(Long id) { this.id = id; return this; }
        public JobRetryHistoryBuilder job(PlatformJob job) { this.job = job; return this; }
        public JobRetryHistoryBuilder attempt(int attempt) { this.attempt = attempt; return this; }
        public JobRetryHistoryBuilder startedAt(Instant startedAt) { this.startedAt = startedAt; return this; }
        public JobRetryHistoryBuilder finishedAt(Instant finishedAt) { this.finishedAt = finishedAt; return this; }
        public JobRetryHistoryBuilder error(String error) { this.error = error; return this; }
        public JobRetryHistoryBuilder durationMs(Long durationMs) { this.durationMs = durationMs; return this; }

        public JobRetryHistory build() {
            return new JobRetryHistory(id, job, attempt, startedAt, finishedAt, error, durationMs);
        }
    }
}
