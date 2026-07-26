package org.SprintForge.modules.workspace.analytics.repository;

import org.SprintForge.modules.workspace.analytics.entity.WorkspaceBackup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceBackupRepository extends JpaRepository<WorkspaceBackup, Long>, JpaSpecificationExecutor<WorkspaceBackup> {
}