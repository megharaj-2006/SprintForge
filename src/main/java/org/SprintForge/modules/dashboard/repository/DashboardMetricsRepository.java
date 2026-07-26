package org.SprintForge.modules.dashboard.repository;

import org.SprintForge.modules.dashboard.entity.DashboardMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardMetricsRepository extends JpaRepository<DashboardMetrics, Long>, JpaSpecificationExecutor<DashboardMetrics> {
}