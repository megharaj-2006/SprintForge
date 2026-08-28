package org.SprintForge.platform.scheduler.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.scheduler.domain.JobRetryHistory;
import org.SprintForge.platform.scheduler.domain.JobStatus;
import org.SprintForge.platform.scheduler.domain.PlatformJob;
import org.SprintForge.platform.scheduler.repository.JobRetryHistoryRepository;
import org.SprintForge.platform.scheduler.repository.PlatformJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service("platformQueueClaimService")
public class QueueClaimService {

    private static final Logger log = LoggerFactory.getLogger(QueueClaimService.class);
    private final PlatformJobRepository jobRepository;
    private final JobRetryHistoryRepository retryHistoryRepository;

    @Autowired
    public QueueClaimService(PlatformJobRepository jobRepository,
                             JobRetryHistoryRepository retryHistoryRepository) {
        this.jobRepository = jobRepository;
        this.retryHistoryRepository = retryHistoryRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PlatformJob> claimQueuedJobs(int batchSize) {
        List<PlatformJob> jobs = jobRepository.claimQueuedJobs(batchSize);
        List<PlatformJob> claimed = new ArrayList<>();
        Instant now = Instant.now();

        for (PlatformJob job : jobs) {
            job.setStatus(JobStatus.RUNNING);
            job.setStartedAt(now);
            claimed.add(jobRepository.save(job));
        }
        jobRepository.flush();
        return claimed;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeJobSuccess(Long jobId, String result, long durationMs) {
        PlatformJob job = jobRepository.findById(jobId).orElse(null);
        if (job == null) return;

        job.setStatus(JobStatus.COMPLETED);
        job.setResult(result);
        job.setCompletedAt(Instant.now());
        job.setDurationMs(durationMs);
        jobRepository.save(job);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeJobFailure(Long jobId, Exception e, Instant start, long durationMs) {
        PlatformJob job = jobRepository.findById(jobId).orElse(null);
        if (job == null) return;

        JobRetryHistory history = JobRetryHistory.builder()
                .job(job)
                .attempt(job.getRetryCount() + 1)
                .startedAt(start)
                .finishedAt(Instant.now())
                .error(e.getMessage())
                .durationMs(durationMs)
                .build();
        retryHistoryRepository.save(history);

        job.setRetryCount(job.getRetryCount() + 1);
        job.setErrorMessage(e.getMessage());

        if (job.getRetryCount() >= job.getMaxRetries()) {
            job.setStatus(JobStatus.DEAD);
        } else {
            job.setStatus(JobStatus.FAILED);
        }
        jobRepository.save(job);
    }
}
