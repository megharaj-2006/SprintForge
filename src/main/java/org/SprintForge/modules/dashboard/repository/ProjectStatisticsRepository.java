package org.SprintForge.modules.dashboard.repository;

import org.SprintForge.modules.dashboard.entity.ProjectStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStatisticsRepository extends JpaRepository<ProjectStatistics, Long>, JpaSpecificationExecutor<ProjectStatistics> {
}