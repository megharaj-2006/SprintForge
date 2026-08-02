package org.SprintForge.modules.workspace.project.insights.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity(name = "InsightsPortfolioProject")
@Table(name = "portfolio_projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioProject extends SoftDeleteEntity {

    @Column(name = "portfolio_id", nullable = false)
    private Long portfolioId;

    @Column(name = "project_id", nullable = false)
    private Long projectId;
}
