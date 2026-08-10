package org.SprintForge.platform.scheduler.maintenance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.scheduler.domain.JobPriority;
import org.SprintForge.platform.scheduler.domain.JobType;
import org.SprintForge.platform.scheduler.dto.CreateJobRequest;
import org.SprintForge.platform.scheduler.dto.JobResponse;
import org.SprintForge.platform.scheduler.queue.JobQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service("platformMaintenanceService")
public class MaintenanceService {

    private static final Logger log = LoggerFactory.getLogger(MaintenanceService.class);
    private final JobQueueService jobQueueService;

    @Autowired
    public MaintenanceService(JobQueueService jobQueueService) {
        this.jobQueueService = jobQueueService;
    }

    @Scheduled(cron = "0 0 2 * * ?") // Daily at 2 AM
    public void triggerDailyMaintenance() {
        log.info("Triggering automatic daily platform maintenance job...");
        triggerCleanupJob();
        triggerSearchReindexJob();
    }

    public JobResponse triggerCleanupJob() {
        CreateJobRequest request = CreateJobRequest.builder()
                .jobName("Daily Platform Maintenance Cleanup")
                .jobType(JobType.CLEANUP)
                .priority(JobPriority.LOW)
                .createdBy("system-maintenance")
                .build();
        return jobQueueService.submitJob(request);
    }

    public JobResponse triggerSearchReindexJob() {
        CreateJobRequest request = CreateJobRequest.builder()
                .jobName("System Search Index Refresh")
                .jobType(JobType.SEARCH_REINDEX)
                .priority(JobPriority.LOW)
                .createdBy("system-maintenance")
                .build();
        return jobQueueService.submitJob(request);
    }
}
