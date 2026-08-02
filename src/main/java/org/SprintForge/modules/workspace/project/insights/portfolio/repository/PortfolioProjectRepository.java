package org.SprintForge.modules.workspace.project.insights.portfolio.repository;

import org.SprintForge.modules.workspace.project.insights.portfolio.entity.PortfolioProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("insightsPortfolioProjectRepository")
public interface PortfolioProjectRepository extends JpaRepository<PortfolioProject, Long>, JpaSpecificationExecutor<PortfolioProject> {

    List<PortfolioProject> findByPortfolioIdAndIsDeletedFalse(Long portfolioId);

    void deleteByPortfolioIdAndProjectId(Long portfolioId, Long projectId);
}
