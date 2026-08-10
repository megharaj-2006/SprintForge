package org.SprintForge.platform.scheduler.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.scheduler.domain.JobRetryHistory;
import org.SprintForge.platform.scheduler.domain.JobStatus;
import org.SprintForge.platform.scheduler.domain.PlatformJob;
import org.SprintForge.platform.scheduler.executor.JobExecutor;
import org.SprintForge.platform.scheduler.repository.JobRetryHistoryRepository;
import org.SprintForge.platform.scheduler.repository.PlatformJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("platformQueueDispatcher")
public class QueueDispatcher {

    private static final Logger log = LoggerFactory.getLogger(QueueDispatcher.class);
    private final PlatformJobRepository jobRepository;
    private final JobRetryHistoryRepository retryHistoryRepository;
    private final Map<String, JobExecutor> executorMap;

    @Autowired
    public QueueDispatcher(PlatformJobRepository jobRepository,
                           JobRetryHistoryRepository retryHistoryRepository,
                           List<JobExecutor> executors) {
        this.jobRepository = jobRepository;
        this.retryHistoryRepository = retryHistoryRepository;
        this.executorMap = executors.stream()
                .collect(Collectors.toMap(e -> e.getSupportedJobType().name(), Function.identity()));
    }

    @Scheduled(fixedDelay = 5000)
    public void processNextQueuedJobs() {
        List<PlatformJob> queuedJobs = jobRepository.findQueued(PageRequest.of(0, 10));
        for (PlatformJob job : queuedJobs) {
            executeJob(job);
        }
    }

    @Transactional
    public void executeJob(PlatformJob job) {
        job.setStatus(JobStatus.RUNNING);
        job.setStartedAt(Instant.now());
        jobRepository.save(job);

        JobExecutor executor = executorMap.get(job.getJobType().name());
        Instant start = Instant.now();

        if (executor == null) {
            log.warn("No JobExecutor found for type {}", job.getJobType());
            job.setStatus(JobStatus.COMPLETED);
            job.setResult("No execution logic registered for job type " + job.getJobType() + ". Completed default stub.");
            job.setCompletedAt(Instant.now());
            job.setDurationMs(Instant.now().toEpochMilli() - start.toEpochMilli());
            jobRepository.save(job);
            return;
        }

        try {
            executor.validate(job);
            executor.execute(job);
            job.setStatus(JobStatus.COMPLETED);
            job.setCompletedAt(Instant.now());
            job.setDurationMs(Instant.now().toEpochMilli() - start.toEpochMilli());
            jobRepository.save(job);
            executor.cleanup(job);
            log.info("Job id={} completed successfully in {}ms", job.getId(), job.getDurationMs());
        } catch (Exception e) {
            log.error("Job id={} failed on attempt {}: {}", job.getId(), job.getRetryCount() + 1, e.getMessage(), e);
            long duration = Instant.now().toEpochMilli() - start.toEpochMilli();

            JobRetryHistory history = JobRetryHistory.builder()
                    .job(job)
                    .attempt(job.getRetryCount() + 1)
                    .startedAt(start)
                    .finishedAt(Instant.now())
                    .error(e.getMessage())
                    .durationMs(duration)
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
            executor.rollback(job);
        }
    }
}
