package org.SprintForge.platform.scheduler.dto;

import java.util.Map;

public class JobStatisticsResponse {
    private long totalJobs;
    private long runningJobs;
    private long queuedJobs;
    private long completedJobs;
    private long failedJobs;
    private long deadJobs;
    private double averageExecutionTimeMs;
    private Map<String, Long> jobsByType;

    public JobStatisticsResponse() {}

    public JobStatisticsResponse(long totalJobs, long runningJobs, long queuedJobs, long completedJobs, long failedJobs, long deadJobs, double averageExecutionTimeMs, Map<String, Long> jobsByType) {
        this.totalJobs = totalJobs;
        this.runningJobs = runningJobs;
        this.queuedJobs = queuedJobs;
        this.completedJobs = completedJobs;
        this.failedJobs = failedJobs;
        this.deadJobs = deadJobs;
        this.averageExecutionTimeMs = averageExecutionTimeMs;
        this.jobsByType = jobsByType;
    }

    public static JobStatisticsResponseBuilder builder() {
        return new JobStatisticsResponseBuilder();
    }

    public long getTotalJobs() { return totalJobs; }
    public void setTotalJobs(long totalJobs) { this.totalJobs = totalJobs; }

    public long getRunningJobs() { return runningJobs; }
    public void setRunningJobs(long runningJobs) { this.runningJobs = runningJobs; }

    public long getQueuedJobs() { return queuedJobs; }
    public void setQueuedJobs(long queuedJobs) { this.queuedJobs = queuedJobs; }

    public long getCompletedJobs() { return completedJobs; }
    public void setCompletedJobs(long completedJobs) { this.completedJobs = completedJobs; }

    public long getFailedJobs() { return failedJobs; }
    public void setFailedJobs(long failedJobs) { this.failedJobs = failedJobs; }

    public long getDeadJobs() { return deadJobs; }
    public void setDeadJobs(long deadJobs) { this.deadJobs = deadJobs; }

    public double getAverageExecutionTimeMs() { return averageExecutionTimeMs; }
    public void setAverageExecutionTimeMs(double averageExecutionTimeMs) { this.averageExecutionTimeMs = averageExecutionTimeMs; }

    public Map<String, Long> getJobsByType() { return jobsByType; }
    public void setJobsByType(Map<String, Long> jobsByType) { this.jobsByType = jobsByType; }

    public static class JobStatisticsResponseBuilder {
        private long totalJobs;
        private long runningJobs;
        private long queuedJobs;
        private long completedJobs;
        private long failedJobs;
        private long deadJobs;
        private double averageExecutionTimeMs;
        private Map<String, Long> jobsByType;

        public JobStatisticsResponseBuilder totalJobs(long totalJobs) { this.totalJobs = totalJobs; return this; }
        public JobStatisticsResponseBuilder runningJobs(long runningJobs) { this.runningJobs = runningJobs; return this; }
        public JobStatisticsResponseBuilder queuedJobs(long queuedJobs) { this.queuedJobs = queuedJobs; return this; }
        public JobStatisticsResponseBuilder completedJobs(long completedJobs) { this.completedJobs = completedJobs; return this; }
        public JobStatisticsResponseBuilder failedJobs(long failedJobs) { this.failedJobs = failedJobs; return this; }
        public JobStatisticsResponseBuilder deadJobs(long deadJobs) { this.deadJobs = deadJobs; return this; }
        public JobStatisticsResponseBuilder averageExecutionTimeMs(double averageExecutionTimeMs) { this.averageExecutionTimeMs = averageExecutionTimeMs; return this; }
        public JobStatisticsResponseBuilder jobsByType(Map<String, Long> jobsByType) { this.jobsByType = jobsByType; return this; }

        public JobStatisticsResponse build() {
            return new JobStatisticsResponse(totalJobs, runningJobs, queuedJobs, completedJobs, failedJobs, deadJobs, averageExecutionTimeMs, jobsByType);
        }
    }
}
