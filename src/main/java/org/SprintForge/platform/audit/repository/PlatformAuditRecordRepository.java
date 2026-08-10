package org.SprintForge.platform.audit.repository;

import org.SprintForge.platform.audit.domain.AuditRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository("platformAuditRecordRepository")
public interface PlatformAuditRecordRepository extends JpaRepository<AuditRecord, Long> {

    List<AuditRecord> findByEntityTypeAndEntityId(String entityType, String entityId, Pageable pageable);

    List<AuditRecord> findByActorId(String actorId, Pageable pageable);

    List<AuditRecord> findByWorkspaceId(String workspaceId, Pageable pageable);

    List<AuditRecord> findByProjectId(String projectId, Pageable pageable);

    @Modifying
    void deleteByCreatedAtBefore(Instant threshold);
}
