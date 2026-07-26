package org.SprintForge.modules.workspace.workspace.repository;

import org.SprintForge.modules.workspace.workspace.entity.WorkspaceWebhook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceWebhookRepository extends JpaRepository<WorkspaceWebhook, Long>, JpaSpecificationExecutor<WorkspaceWebhook> {
}