package org.SprintForge.platform.scheduler.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.platform.scheduler.domain.JobStatus;
import org.SprintForge.platform.scheduler.domain.PlatformJob;
import org.SprintForge.platform.scheduler.dto.JobResponse;
import org.SprintForge.platform.scheduler.queue.JobQueueService;
import org.SprintForge.platform.scheduler.queue.QueueDispatcher;
import org.SprintForge.platform.scheduler.repository.PlatformJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("platformRecoveryService")
public class RecoveryService {

    private static final Logger log = LoggerFactory.getLogger(RecoveryService.class);
    private final PlatformJobRepository jobRepository;
    private final QueueDispatcher queueDispatcher;
    private final JobQueueService jobQueueService;

    @Autowired
    public RecoveryService(PlatformJobRepository jobRepository,
                           QueueDispatcher queueDispatcher,
                           JobQueueService jobQueueService) {
        this.jobRepository = jobRepository;
        this.queueDispatcher = queueDispatcher;
        this.jobQueueService = jobQueueService;
    }

    @Transactional
    public JobResponse retryJob(Long jobId) {
        PlatformJob job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + jobId));

        job.setStatus(JobStatus.QUEUED);
        job.setErrorMessage(null);
        PlatformJob saved = jobRepository.save(job);
        log.info("Retrying job id={}", jobId);
        queueDispatcher.executeJob(saved);
        return jobQueueService.toJobResponse(saved);
    }

    @Transactional
    public JobResponse recoverDeadJob(Long jobId) {
        PlatformJob job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + jobId));

        job.setStatus(JobStatus.QUEUED);
        job.setRetryCount(0);
        job.setErrorMessage(null);
        PlatformJob saved = jobRepository.save(job);
        log.info("Recovered dead job id={}", jobId);
        return jobQueueService.toJobResponse(saved);
    }

    public List<JobResponse> getDeadQueueJobs(int page, int size) {
        return jobRepository.findByStatus(JobStatus.DEAD, org.springframework.data.domain.PageRequest.of(page, size)).stream()
                .map(jobQueueService::toJobResponse)
                .collect(Collectors.toList());
    }
}
