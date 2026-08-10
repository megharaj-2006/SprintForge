package org.SprintForge.modules.workspace.workspace.security.audit.repository;

import org.SprintForge.modules.workspace.workspace.entity.WorkspaceAuditLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkspaceAuditRepository extends JpaRepository<WorkspaceAuditLog, Long>, JpaSpecificationExecutor<WorkspaceAuditLog> {
    List<WorkspaceAuditLog> findByWorkspaceIdOrderByCreatedAtDesc(Long workspaceId, Pageable pageable);
    List<WorkspaceAuditLog> findByWorkspaceIdAndActorId(Long workspaceId, Long actorId, Pageable pageable);
    List<WorkspaceAuditLog> findByWorkspaceIdAndAction(Long workspaceId, String action, Pageable pageable);
    List<WorkspaceAuditLog> findByWorkspaceIdAndEntityType(Long workspaceId, String entityType, Pageable pageable);

    @Query("SELECT w FROM WorkspaceAuditLog w WHERE w.workspaceId = :workspaceId AND w.createdAt BETWEEN :from AND :to ORDER BY w.createdAt DESC")
    List<WorkspaceAuditLog> findByDateRange(
            @Param("workspaceId") Long workspaceId, 
            @Param("from") LocalDateTime from, 
            @Param("to") LocalDateTime to, 
            Pageable pageable);
}
