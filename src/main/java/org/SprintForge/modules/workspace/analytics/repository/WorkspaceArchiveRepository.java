package org.SprintForge.modules.workspace.analytics.repository;

import org.SprintForge.modules.workspace.analytics.entity.WorkspaceArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceArchiveRepository extends JpaRepository<WorkspaceArchive, Long>, JpaSpecificationExecutor<WorkspaceArchive> {
}