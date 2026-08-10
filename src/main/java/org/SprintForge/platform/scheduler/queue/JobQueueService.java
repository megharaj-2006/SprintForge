package org.SprintForge.platform.scheduler.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.platform.scheduler.domain.JobPriority;
import org.SprintForge.platform.scheduler.domain.JobStatus;
import org.SprintForge.platform.scheduler.domain.JobType;
import org.SprintForge.platform.scheduler.domain.PlatformJob;
import org.SprintForge.platform.scheduler.dto.CreateJobRequest;
import org.SprintForge.platform.scheduler.dto.JobResponse;
import org.SprintForge.platform.scheduler.repository.PlatformJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("platformJobQueueService")
public class JobQueueService {

    private static final Logger log = LoggerFactory.getLogger(JobQueueService.class);
    private final PlatformJobRepository jobRepository;

    @Autowired
    public JobQueueService(PlatformJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Transactional
    public JobResponse submitJob(CreateJobRequest request) {
        PlatformJob job = PlatformJob.builder()
                .jobType(request.getJobType())
                .jobName(request.getJobName())
                .priority(request.getPriority() != null ? request.getPriority() : JobPriority.NORMAL)
                .status(JobStatus.QUEUED)
                .payload(request.getPayload())
                .createdBy(request.getCreatedBy())
                .workspaceId(request.getWorkspaceId())
                .projectId(request.getProjectId())
                .maxRetries(request.getMaxRetries())
                .correlationId(request.getCorrelationId())
                .build();

        PlatformJob saved = jobRepository.save(job);
        log.info("Submitted job id={} type={} priority={}", saved.getId(), saved.getJobType(), saved.getPriority());
        return toJobResponse(saved);
    }

    @Transactional
    public JobResponse cancelJob(Long jobId) {
        PlatformJob job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + jobId));

        job.setStatus(JobStatus.CANCELLED);
        PlatformJob saved = jobRepository.save(job);
        log.info("Cancelled job id={}", jobId);
        return toJobResponse(saved);
    }

    public List<JobResponse> getQueuedJobs(int page, int size) {
        return jobRepository.findQueued(PageRequest.of(page, size)).stream()
                .map(this::toJobResponse)
                .collect(Collectors.toList());
    }

    public List<JobResponse> getFailedJobs(int page, int size) {
        return jobRepository.findFailed(PageRequest.of(page, size)).stream()
                .map(this::toJobResponse)
                .collect(Collectors.toList());
    }

    public JobResponse getJob(Long jobId) {
        PlatformJob job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + jobId));
        return toJobResponse(job);
    }

    public JobResponse toJobResponse(PlatformJob job) {
        return JobResponse.builder()
                .id(job.getId())
                .jobType(job.getJobType())
                .jobName(job.getJobName())
                .priority(job.getPriority())
                .status(job.getStatus())
                .payload(job.getPayload())
                .result(job.getResult())
                .createdBy(job.getCreatedBy())
                .workspaceId(job.getWorkspaceId())
                .projectId(job.getProjectId())
                .scheduledAt(job.getScheduledAt())
                .startedAt(job.getStartedAt())
                .completedAt(job.getCompletedAt())
                .durationMs(job.getDurationMs())
                .retryCount(job.getRetryCount())
                .maxRetries(job.getMaxRetries())
                .errorMessage(job.getErrorMessage())
                .correlationId(job.getCorrelationId())
                .createdAt(job.getCreatedAt())
                .build();
    }
}
