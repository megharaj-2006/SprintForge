package org.SprintForge.modules.workspace.workspace.repository;

import org.SprintForge.modules.workspace.workspace.entity.WorkspaceSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkspaceSubscriptionRepository extends JpaRepository<WorkspaceSubscription, Long>, JpaSpecificationExecutor<WorkspaceSubscription> {

    Optional<WorkspaceSubscription> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);
}