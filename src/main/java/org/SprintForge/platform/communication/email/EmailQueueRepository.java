package org.SprintForge.platform.communication.email;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("platformEmailQueueRepository")
public interface EmailQueueRepository extends JpaRepository<EmailQueue, Long> {
    List<EmailQueue> findByStatusOrderByScheduledAtAsc(String status, Pageable pageable);

    @Query(value = "SELECT * FROM email_queue WHERE status = 'PENDING' ORDER BY scheduled_at ASC LIMIT :batchSize FOR UPDATE SKIP LOCKED", nativeQuery = true)
    List<EmailQueue> claimPendingEmails(@Param("batchSize") int batchSize);
}
