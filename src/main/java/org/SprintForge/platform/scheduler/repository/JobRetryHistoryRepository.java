package org.SprintForge.platform.scheduler.repository;

import org.SprintForge.platform.scheduler.domain.JobRetryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("platformJobRetryHistoryRepository")
public interface JobRetryHistoryRepository extends JpaRepository<JobRetryHistory, Long> {
    List<JobRetryHistory> findByJobIdOrderByAttemptAsc(Long jobId);
}
