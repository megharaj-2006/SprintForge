package org.SprintForge.platform.scheduler;

import org.SprintForge.platform.scheduler.domain.JobPriority;
import org.SprintForge.platform.scheduler.domain.JobStatus;
import org.SprintForge.platform.scheduler.domain.JobType;
import org.SprintForge.platform.scheduler.domain.PlatformJob;
import org.SprintForge.platform.scheduler.queue.QueueClaimService;
import org.SprintForge.platform.scheduler.repository.PlatformJobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class QueueDispatcherReliabilityTest {

    @Autowired
    private QueueClaimService queueClaimService;

    @Autowired
    private PlatformJobRepository jobRepository;

    @Test
    @DisplayName("Claimed jobs enter RUNNING status upon claiming")
    public void testAtomicJobClaiming() {
        PlatformJob job = PlatformJob.builder()
                .jobName("Test Atomic Claim Job")
                .jobType(JobType.CLEANUP)
                .priority(JobPriority.HIGH)
                .status(JobStatus.QUEUED)
                .build();

        PlatformJob saved = jobRepository.save(job);

        List<PlatformJob> claimed = queueClaimService.claimQueuedJobs(10);

        assertThat(claimed).isNotEmpty();
        PlatformJob claimedJob = jobRepository.findById(saved.getId()).orElse(null);
        assertThat(claimedJob).isNotNull();
        assertThat(claimedJob.getStatus()).isEqualTo(JobStatus.RUNNING);
        assertThat(claimedJob.getStartedAt()).isNotNull();

        // Cleanup test entity
        jobRepository.deleteById(saved.getId());
    }
}
