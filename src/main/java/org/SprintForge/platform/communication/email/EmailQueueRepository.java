package org.SprintForge.platform.communication.email;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("platformEmailQueueRepository")
public interface EmailQueueRepository extends JpaRepository<EmailQueue, Long> {
    List<EmailQueue> findByStatusOrderByScheduledAtAsc(String status, Pageable pageable);
}
