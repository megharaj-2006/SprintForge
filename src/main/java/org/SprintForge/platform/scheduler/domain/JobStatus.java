package org.SprintForge.platform.scheduler.domain;

public enum JobStatus {
    QUEUED,
    SCHEDULED,
    RUNNING,
    COMPLETED,
    FAILED,
    CANCELLED,
    RETRYING,
    DEAD
}
