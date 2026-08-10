package org.SprintForge.platform.scheduler.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.platform.scheduler.domain.JobPriority;
import org.SprintForge.platform.scheduler.domain.JobStatus;
import org.SprintForge.platform.scheduler.domain.PlatformJob;
import org.SprintForge.platform.scheduler.domain.ScheduledJob;
import org.SprintForge.platform.scheduler.dto.JobResponse;
import org.SprintForge.platform.scheduler.dto.ScheduleJobRequest;
import org.SprintForge.platform.scheduler.queue.JobQueueService;
import org.SprintForge.platform.scheduler.repository.PlatformJobRepository;
import org.SprintForge.platform.scheduler.repository.ScheduledJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service("platformSchedulerService")
public class SchedulerService {

    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);
    private final ScheduledJobRepository scheduledJobRepository;
    private final PlatformJobRepository jobRepository;
    private final JobQueueService jobQueueService;

    @Autowired
    public SchedulerService(ScheduledJobRepository scheduledJobRepository,
                             PlatformJobRepository jobRepository,
                             JobQueueService jobQueueService) {
        this.scheduledJobRepository = scheduledJobRepository;
        this.jobRepository = jobRepository;
        this.jobQueueService = jobQueueService;
    }

    @Transactional
    public ScheduledJob createScheduledJob(ScheduleJobRequest request) {
        ScheduledJob job = ScheduledJob.builder()
                .name(request.getName())
                .cronExpression(request.getCronExpression())
                .jobType(request.getJobType())
                .payload(request.getPayload())
                .createdBy(request.getCreatedBy())
                .enabled(true)
                .nextExecution(request.getScheduledAt() != null ? request.getScheduledAt() : Instant.now())
                .build();

        return scheduledJobRepository.save(job);
    }

    @Transactional
    public JobResponse scheduleOneTimeJob(ScheduleJobRequest request) {
        PlatformJob job = PlatformJob.builder()
                .jobType(request.getJobType())
                .jobName(request.getName())
                .priority(JobPriority.NORMAL)
                .status(JobStatus.SCHEDULED)
                .payload(request.getPayload())
                .createdBy(request.getCreatedBy())
                .scheduledAt(request.getScheduledAt() != null ? request.getScheduledAt() : Instant.now())
                .build();

        PlatformJob saved = jobRepository.save(job);
        log.info("Scheduled one-time job id={} for execution at {}", saved.getId(), saved.getScheduledAt());
        return jobQueueService.toJobResponse(saved);
    }

    public List<ScheduledJob> getAllScheduledJobs() {
        return scheduledJobRepository.findAll();
    }

    @Transactional
    public void deleteScheduledJob(Long id) {
        scheduledJobRepository.deleteById(id);
    }

    @Scheduled(fixedDelay = 10000)
    public void triggerScheduledDueJobs() {
        List<PlatformJob> dueJobs = jobRepository.findScheduledDue(Instant.now());
        for (PlatformJob job : dueJobs) {
            job.setStatus(JobStatus.QUEUED);
            jobRepository.save(job);
            log.info("Promoted scheduled job id={} to QUEUED", job.getId());
        }
    }
}
