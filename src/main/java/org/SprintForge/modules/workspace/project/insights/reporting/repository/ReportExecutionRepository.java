package org.SprintForge.modules.workspace.project.insights.reporting.repository;

import org.SprintForge.modules.workspace.project.insights.reporting.entity.ReportExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("insightsReportExecutionRepository")
public interface ReportExecutionRepository extends JpaRepository<ReportExecution, Long>, JpaSpecificationExecutor<ReportExecution> {

    List<ReportExecution> findByReportIdAndIsDeletedFalse(Long reportId);
}
