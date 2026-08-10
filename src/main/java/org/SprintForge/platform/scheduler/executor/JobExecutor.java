package org.SprintForge.platform.scheduler.executor;

import org.SprintForge.platform.scheduler.domain.JobType;
import org.SprintForge.platform.scheduler.domain.PlatformJob;

public interface JobExecutor {

    JobType getSupportedJobType();

    void execute(PlatformJob job) throws Exception;

    default void validate(PlatformJob job) {
        if (job == null) {
            throw new IllegalArgumentException("Job cannot be null");
        }
    }

    default void rollback(PlatformJob job) {}

    default void cleanup(PlatformJob job) {}
}
