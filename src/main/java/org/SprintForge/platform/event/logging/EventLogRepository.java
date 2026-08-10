package org.SprintForge.platform.event.logging;

import org.SprintForge.platform.event.metadata.EventStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {

    List<EventLog> findByStatus(EventStatus status);

    List<EventLog> findByEventType(String eventType);

    List<EventLog> findByAggregateTypeAndAggregateId(String aggregateType, String aggregateId);

    @Query("SELECT e FROM EventLog e WHERE e.durationMs >= :minDurationMs ORDER BY e.durationMs DESC")
    List<EventLog> findSlowEvents(@Param("minDurationMs") long minDurationMs, Pageable pageable);

    @Query("SELECT e FROM EventLog e ORDER BY e.processedAt DESC")
    List<EventLog> findRecent(Pageable pageable);
}
