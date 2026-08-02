package org.SprintForge.modules.workspace.project.insights.reporting.repository;

import org.SprintForge.modules.workspace.project.insights.reporting.entity.SavedReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("insightsSavedReportRepository")
public interface SavedReportRepository extends JpaRepository<SavedReport, Long>, JpaSpecificationExecutor<SavedReport> {

    List<SavedReport> findByProjectIdAndIsDeletedFalse(Long projectId);
}
