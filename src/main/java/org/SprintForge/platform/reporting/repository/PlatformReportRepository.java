package org.SprintForge.platform.reporting.repository;

import org.SprintForge.platform.reporting.domain.PlatformReport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("platformReportRepository")
public interface PlatformReportRepository extends JpaRepository<PlatformReport, Long> {
    List<PlatformReport> findByGeneratedBy(String generatedBy, Pageable pageable);
}
