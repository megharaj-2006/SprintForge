package org.SprintForge.modules.dashboard.repository;

import org.SprintForge.modules.dashboard.entity.SprintMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintMetricsRepository extends JpaRepository<SprintMetrics, Long>, JpaSpecificationExecutor<SprintMetrics> {
}