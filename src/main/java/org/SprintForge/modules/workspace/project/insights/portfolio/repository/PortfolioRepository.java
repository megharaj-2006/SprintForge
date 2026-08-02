package org.SprintForge.modules.workspace.project.insights.portfolio.repository;

import org.SprintForge.modules.workspace.project.insights.portfolio.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("insightsPortfolioRepository")
public interface PortfolioRepository extends JpaRepository<Portfolio, Long>, JpaSpecificationExecutor<Portfolio> {

    List<Portfolio> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);
}
