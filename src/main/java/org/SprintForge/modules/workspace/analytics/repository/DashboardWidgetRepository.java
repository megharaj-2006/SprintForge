package org.SprintForge.modules.workspace.analytics.repository;

import org.SprintForge.modules.workspace.analytics.entity.DashboardWidget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardWidgetRepository extends JpaRepository<DashboardWidget, Long> {

    List<DashboardWidget> findByDashboardIdAndIsDeletedFalseOrderByPositionAsc(Long dashboardId);
}