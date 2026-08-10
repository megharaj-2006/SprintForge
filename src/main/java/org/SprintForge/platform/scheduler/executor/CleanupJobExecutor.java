package org.SprintForge.platform.scheduler.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.scheduler.domain.JobType;
import org.SprintForge.platform.scheduler.domain.PlatformJob;
import org.SprintForge.platform.scheduler.repository.PlatformJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component("platformCleanupJobExecutor")
public class CleanupJobExecutor implements JobExecutor {

    private static final Logger log = LoggerFactory.getLogger(CleanupJobExecutor.class);
    private final PlatformJobRepository jobRepository;

    @Autowired
    public CleanupJobExecutor(PlatformJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public JobType getSupportedJobType() {
        return JobType.CLEANUP;
    }

    @Override
    public void execute(PlatformJob job) throws Exception {
        log.info("Executing maintenance cleanup job: id={}", job.getId());
        Instant cutoff = Instant.now().minus(7, ChronoUnit.DAYS);
        int deletedCount = jobRepository.deleteCompletedOlderThan(cutoff);
        log.info("Purged {} completed jobs older than 7 days", deletedCount);
        job.setResult("Purged " + deletedCount + " old jobs");
    }
}
