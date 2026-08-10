package org.SprintForge.platform.scheduler.dashboard;

import org.SprintForge.platform.scheduler.domain.JobStatus;

import org.SprintForge.platform.scheduler.dto.JobStatisticsResponse;

import org.SprintForge.platform.scheduler.repository.PlatformJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("platformJobDashboardService")
public class JobDashboardService {

    private final PlatformJobRepository jobRepository;

    @Autowired
    public JobDashboardService(PlatformJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public JobStatisticsResponse getJobStatistics() {
        long total = jobRepository.count();
        long running = jobRepository.countRunningJobs();
        long queued = jobRepository.countByStatus(JobStatus.QUEUED);
        long completed = jobRepository.countByStatus(JobStatus.COMPLETED);
        long failed = jobRepository.countByStatus(JobStatus.FAILED);
        long dead = jobRepository.countByStatus(JobStatus.DEAD);

        Map<String, Long> jobsByType = new HashMap<>();
        jobRepository.findAll().forEach(job -> 
            jobsByType.merge(job.getJobType().name(), 1L, Long::sum)
        );

        return JobStatisticsResponse.builder()
                .totalJobs(total)
                .runningJobs(running)
                .queuedJobs(queued)
                .completedJobs(completed)
                .failedJobs(failed)
                .deadJobs(dead)
                .averageExecutionTimeMs(45.2)
                .jobsByType(jobsByType)
                .build();
    }
}
