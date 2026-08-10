package org.SprintForge.platform;

import org.SprintForge.platform.scheduler.batch.BatchProcessingService;
import org.SprintForge.platform.scheduler.dashboard.JobDashboardService;
import org.SprintForge.platform.scheduler.domain.*;
import org.SprintForge.platform.scheduler.dto.*;
import org.SprintForge.platform.scheduler.maintenance.MaintenanceService;
import org.SprintForge.platform.scheduler.queue.JobQueueService;
import org.SprintForge.platform.scheduler.queue.QueueDispatcher;
import org.SprintForge.platform.scheduler.repository.JobRetryHistoryRepository;
import org.SprintForge.platform.scheduler.repository.PlatformJobRepository;
import org.SprintForge.platform.scheduler.retry.RecoveryService;
import org.SprintForge.platform.scheduler.scheduler.SchedulerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BackgroundProcessingPlatformIntegrationTest {

    @Autowired
    private JobQueueService jobQueueService;

    @Autowired
    private QueueDispatcher queueDispatcher;

    @Autowired
    private PlatformJobRepository jobRepository;

    @Autowired
    private JobRetryHistoryRepository retryHistoryRepository;

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private RecoveryService recoveryService;

    @Autowired
    private BatchProcessingService batchProcessingService;

    @Autowired
    private MaintenanceService maintenanceService;

    @Autowired
    private JobDashboardService jobDashboardService;

    @Test
    public void testJobSubmissionAndExecution() {
        CreateJobRequest request = CreateJobRequest.builder()
                .jobName("Index Search Rebuild")
                .jobType(JobType.SEARCH_REINDEX)
                .priority(JobPriority.HIGH)
                .payload("TASK")
                .createdBy("user-admin")
                .workspaceId("ws-100")
                .projectId("proj-200")
                .build();

        JobResponse submitted = jobQueueService.submitJob(request);
        assertNotNull(submitted.getId());
        assertEquals(JobStatus.QUEUED, submitted.getStatus());
        assertEquals(JobPriority.HIGH, submitted.getPriority());

        // Process queued job
        queueDispatcher.processNextQueuedJobs();

        JobResponse completed = jobQueueService.getJob(submitted.getId());
        assertEquals(JobStatus.COMPLETED, completed.getStatus());
        assertNotNull(completed.getResult());
        assertNotNull(completed.getCompletedAt());
    }

    @Test
    public void testSchedulerAndOneTimeJob() {
        ScheduleJobRequest scheduleReq = ScheduleJobRequest.builder()
                .name("Nightly Report Export")
                .cronExpression("0 0 0 * * ?")
                .jobType(JobType.REPORT)
                .payload("{ reportId: 101 }")
                .createdBy("user-admin")
                .scheduledAt(Instant.now().plusSeconds(3600))
                .build();

        ScheduledJob scheduledJob = schedulerService.createScheduledJob(scheduleReq);
        assertNotNull(scheduledJob.getId());
        assertTrue(scheduledJob.isEnabled());

        List<ScheduledJob> allJobs = schedulerService.getAllScheduledJobs();
        assertFalse(allJobs.isEmpty());

        JobResponse oneTimeJob = schedulerService.scheduleOneTimeJob(scheduleReq);
        assertNotNull(oneTimeJob.getId());
        assertEquals(JobStatus.SCHEDULED, oneTimeJob.getStatus());
    }

    @Test
    public void testRetryAndDeadQueueRecovery() {
        // Submit a failing job type or custom job with maxRetries = 1
        PlatformJob job = PlatformJob.builder()
                .jobType(JobType.CUSTOM)
                .jobName("Failing Webhook Job")
                .priority(JobPriority.NORMAL)
                .status(JobStatus.QUEUED)
                .payload("{ url: 'https://invalid-endpoint.local' }")
                .maxRetries(1)
                .build();

        PlatformJob savedJob = jobRepository.save(job);

        // Execute job - default custom job has no execution logic stub, so it completes
        queueDispatcher.executeJob(savedJob);
        PlatformJob executed = jobRepository.findById(savedJob.getId()).orElseThrow();
        assertEquals(JobStatus.COMPLETED, executed.getStatus());

        // Manually mark a job as DEAD to test recovery service
        executed.setStatus(JobStatus.DEAD);
        jobRepository.save(executed);

        List<JobResponse> deadJobs = recoveryService.getDeadQueueJobs(0, 10);
        assertFalse(deadJobs.isEmpty());

        JobResponse recovered = recoveryService.recoverDeadJob(executed.getId());
        assertEquals(JobStatus.QUEUED, recovered.getStatus());
        assertEquals(0, recovered.getRetryCount());
    }

    @Test
    public void testBatchProcessingAndMaintenance() {
        BatchJob batchJob = batchProcessingService.createBatchJob("Bulk Task Import", JobType.IMPORT, 100);
        assertNotNull(batchJob.getId());
        assertEquals(JobStatus.RUNNING, batchJob.getStatus());

        BatchJob updatedBatch = batchProcessingService.updateProgress(batchJob.getId(), 50, 5);
        assertEquals(50, updatedBatch.getProcessedItems());
        assertEquals(5, updatedBatch.getFailedItems());

        BatchJob completedBatch = batchProcessingService.updateProgress(batchJob.getId(), 45, 0);
        assertEquals(JobStatus.COMPLETED, completedBatch.getStatus());

        // Trigger maintenance
        JobResponse cleanupJob = maintenanceService.triggerCleanupJob();
        assertNotNull(cleanupJob.getId());

        JobStatisticsResponse stats = jobDashboardService.getJobStatistics();
        assertNotNull(stats);
        assertTrue(stats.getTotalJobs() > 0);
    }
}
