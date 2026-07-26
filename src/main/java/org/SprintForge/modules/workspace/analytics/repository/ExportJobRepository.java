package org.SprintForge.modules.workspace.analytics.repository;

import org.SprintForge.modules.workspace.analytics.entity.ExportJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExportJobRepository extends JpaRepository<ExportJob, Long>, JpaSpecificationExecutor<ExportJob> {
}