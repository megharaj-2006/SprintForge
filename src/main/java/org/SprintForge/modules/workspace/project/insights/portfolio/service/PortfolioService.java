package org.SprintForge.modules.workspace.project.insights.portfolio.service;

import org.SprintForge.modules.workspace.project.insights.portfolio.dto.request.CreatePortfolioRequest;
import org.SprintForge.modules.workspace.project.insights.portfolio.dto.request.UpdatePortfolioRequest;
import org.SprintForge.modules.workspace.project.insights.portfolio.dto.response.PortfolioResponse;

import java.util.List;

public interface PortfolioService {
    PortfolioResponse createPortfolio(CreatePortfolioRequest request, Long actorId);
    PortfolioResponse updatePortfolio(Long portfolioId, UpdatePortfolioRequest request, Long actorId);
    List<PortfolioResponse> getPortfolios(Long workspaceId);
    PortfolioResponse getPortfolio(Long portfolioId);
    void deletePortfolio(Long portfolioId, Long actorId);
}
