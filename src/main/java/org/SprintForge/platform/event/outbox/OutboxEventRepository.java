package org.SprintForge.platform.event.outbox;

import org.SprintForge.platform.event.metadata.EventStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {
    List<OutboxEvent> findByStatusOrderByCreatedAtAsc(EventStatus status, Pageable pageable);

    @Query(value = "SELECT * FROM outbox_events WHERE status = 'PENDING' ORDER BY created_at ASC LIMIT :batchSize FOR UPDATE SKIP LOCKED", nativeQuery = true)
    List<OutboxEvent> claimPendingEvents(@Param("batchSize") int batchSize);
}
