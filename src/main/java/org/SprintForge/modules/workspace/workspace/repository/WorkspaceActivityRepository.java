package org.SprintForge.modules.workspace.workspace.repository;

import org.SprintForge.modules.workspace.workspace.entity.WorkspaceActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceActivityRepository extends JpaRepository<WorkspaceActivity, Long>, JpaSpecificationExecutor<WorkspaceActivity> {

    Page<WorkspaceActivity> findByWorkspaceIdOrderByCreatedAtDesc(Long workspaceId, Pageable pageable);

    Page<WorkspaceActivity> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    Page<WorkspaceActivity> findByEntityTypeAndEntityIdOrderByCreatedAtDesc(String entityType, Long entityId, Pageable pageable);
}