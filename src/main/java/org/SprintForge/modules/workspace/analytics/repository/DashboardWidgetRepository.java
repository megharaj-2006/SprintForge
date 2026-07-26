package org.SprintForge.modules.workspace.analytics.repository;

import org.SprintForge.modules.workspace.analytics.entity.DashboardWidget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardWidgetRepository extends JpaRepository<DashboardWidget, Long>, JpaSpecificationExecutor<DashboardWidget> {
}