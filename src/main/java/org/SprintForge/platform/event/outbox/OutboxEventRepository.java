package org.SprintForge.platform.event.outbox;

import org.SprintForge.platform.event.metadata.EventStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {
    List<OutboxEvent> findByStatusOrderByCreatedAtAsc(EventStatus status, Pageable pageable);
}
