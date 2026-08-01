package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.TrashRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrashRecordRepository extends JpaRepository<TrashRecord, Long> {

    List<TrashRecord> findByIsDeletedFalseOrderByDeletedAtDesc();

    Optional<TrashRecord> findByEntityTypeAndEntityIdAndIsDeletedFalse(String entityType, Long entityId);

    List<TrashRecord> findByScheduledPurgeAtBeforeAndIsDeletedFalse(LocalDateTime now);
}
