package org.SprintForge.modules.workspace.workspace.security.webhook.repository;

import org.SprintForge.modules.workspace.workspace.entity.WorkspaceWebhook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("workspaceSecurityWebhookRepository")
public interface WorkspaceWebhookRepository extends JpaRepository<WorkspaceWebhook, Long>, JpaSpecificationExecutor<WorkspaceWebhook> {
    List<WorkspaceWebhook> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);
    List<WorkspaceWebhook> findByWorkspaceIdAndIsActiveTrueAndIsDeletedFalse(Long workspaceId);
    
    @Query("SELECT w FROM WorkspaceWebhook w WHERE w.workspaceId = :wid AND w.isDeleted = false AND w.events LIKE %:event%")
    List<WorkspaceWebhook> findByWorkspaceIdAndEvent(@Param("wid") Long wid, @Param("event") String event);
}
