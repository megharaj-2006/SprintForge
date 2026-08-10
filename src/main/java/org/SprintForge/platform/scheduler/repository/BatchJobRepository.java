package org.SprintForge.platform.scheduler.repository;

import org.SprintForge.platform.scheduler.domain.BatchJob;
import org.SprintForge.platform.scheduler.domain.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("platformBatchJobRepository")
public interface BatchJobRepository extends JpaRepository<BatchJob, Long> {
    List<BatchJob> findByStatus(JobStatus status);
}
