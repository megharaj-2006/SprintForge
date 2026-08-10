package org.SprintForge.platform.event.retry;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeadLetterEventRepository extends JpaRepository<DeadLetterEvent, Long> {
    List<DeadLetterEvent> findByOriginalEventId(String originalEventId);
}
