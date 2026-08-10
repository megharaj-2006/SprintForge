package org.SprintForge.platform.scheduler.repository;

import org.SprintForge.platform.scheduler.domain.JobPriority;
import org.SprintForge.platform.scheduler.domain.JobStatus;
import org.SprintForge.platform.scheduler.domain.JobType;
import org.SprintForge.platform.scheduler.domain.PlatformJob;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository("platformJobRepository")
public interface PlatformJobRepository extends JpaRepository<PlatformJob, Long> {

    @Query("SELECT j FROM PlatformJob j WHERE j.status = org.SprintForge.platform.scheduler.domain.JobStatus.QUEUED ORDER BY j.priority DESC, j.createdAt ASC")
    List<PlatformJob> findQueued(Pageable pageable);

    @Query("SELECT j FROM PlatformJob j WHERE j.status = org.SprintForge.platform.scheduler.domain.JobStatus.RUNNING")
    List<PlatformJob> findRunning();

    @Query("SELECT j FROM PlatformJob j WHERE j.status = org.SprintForge.platform.scheduler.domain.JobStatus.FAILED OR j.status = org.SprintForge.platform.scheduler.domain.JobStatus.DEAD")
    List<PlatformJob> findFailed(Pageable pageable);

    List<PlatformJob> findByJobType(JobType jobType, Pageable pageable);

    List<PlatformJob> findByStatus(JobStatus status, Pageable pageable);

    @Query("SELECT j FROM PlatformJob j WHERE j.status = org.SprintForge.platform.scheduler.domain.JobStatus.SCHEDULED AND (j.scheduledAt IS NULL OR j.scheduledAt <= :now)")
    List<PlatformJob> findScheduledDue(@Param("now") Instant now);

    @Query("SELECT j FROM PlatformJob j WHERE j.status = org.SprintForge.platform.scheduler.domain.JobStatus.RUNNING AND j.startedAt <= :threshold")
    List<PlatformJob> findLongRunning(@Param("threshold") Instant threshold);

    @Modifying
    @Query("DELETE FROM PlatformJob j WHERE j.status = org.SprintForge.platform.scheduler.domain.JobStatus.COMPLETED AND j.completedAt <= :threshold")
    int deleteCompletedOlderThan(@Param("threshold") Instant threshold);

    @Query("SELECT COUNT(j) FROM PlatformJob j WHERE j.status = org.SprintForge.platform.scheduler.domain.JobStatus.RUNNING")
    long countRunningJobs();

    @Query("SELECT COUNT(j) FROM PlatformJob j WHERE j.status = :status")
    long countByStatus(@Param("status") JobStatus status);
}
