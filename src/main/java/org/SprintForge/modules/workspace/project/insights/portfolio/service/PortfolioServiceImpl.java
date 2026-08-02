package org.SprintForge.modules.workspace.project.insights.portfolio.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.insights.metrics.dto.ProjectMetricsResponse;
import org.SprintForge.modules.workspace.project.insights.metrics.service.ProjectMetricsService;
import org.SprintForge.modules.workspace.project.insights.portfolio.dto.request.CreatePortfolioRequest;
import org.SprintForge.modules.workspace.project.insights.portfolio.dto.request.UpdatePortfolioRequest;
import org.SprintForge.modules.workspace.project.insights.portfolio.dto.response.PortfolioResponse;
import org.SprintForge.modules.workspace.project.insights.portfolio.entity.Portfolio;
import org.SprintForge.modules.workspace.project.insights.portfolio.entity.PortfolioProject;
import org.SprintForge.modules.workspace.project.insights.portfolio.repository.PortfolioProjectRepository;
import org.SprintForge.modules.workspace.project.insights.portfolio.repository.PortfolioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioProjectRepository portfolioProjectRepository;
    private final ProjectMetricsService projectMetricsService;

    @Override
    @Transactional
    public PortfolioResponse createPortfolio(CreatePortfolioRequest request, Long actorId) {
        Portfolio portfolio = new Portfolio();
        portfolio.setWorkspaceId(request.getWorkspaceId());
        portfolio.setName(request.getName());
        portfolio.setDescription(request.getDescription());
        portfolio.setOwnerId(request.getOwnerId() != null ? request.getOwnerId() : actorId);
        portfolio.setStatus("ACTIVE");

        Portfolio saved = portfolioRepository.save(portfolio);

        if (request.getProjectIds() != null && !request.getProjectIds().isEmpty()) {
            for (Long pId : request.getProjectIds()) {
                PortfolioProject pp = new PortfolioProject();
                pp.setPortfolioId(saved.getId());
                pp.setProjectId(pId);
                portfolioProjectRepository.save(pp);
            }
        }

        return toResponse(saved);
    }

    @Override
    @Transactional
    public PortfolioResponse updatePortfolio(Long portfolioId, UpdatePortfolioRequest request, Long actorId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found with ID: " + portfolioId));

        if (request.getName() != null) portfolio.setName(request.getName());
        if (request.getDescription() != null) portfolio.setDescription(request.getDescription());
        if (request.getStatus() != null) portfolio.setStatus(request.getStatus());
        if (request.getOwnerId() != null) portfolio.setOwnerId(request.getOwnerId());

        if (request.getProjectIds() != null) {
            List<PortfolioProject> existing = portfolioProjectRepository.findByPortfolioIdAndIsDeletedFalse(portfolioId);
            for (PortfolioProject pp : existing) {
                pp.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
                portfolioProjectRepository.save(pp);
            }
            for (Long pId : request.getProjectIds()) {
                PortfolioProject pp = new PortfolioProject();
                pp.setPortfolioId(portfolio.getId());
                pp.setProjectId(pId);
                portfolioProjectRepository.save(pp);
            }
        }

        Portfolio saved = portfolioRepository.save(portfolio);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PortfolioResponse> getPortfolios(Long workspaceId) {
        return portfolioRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PortfolioResponse getPortfolio(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found with ID: " + portfolioId));
        return toResponse(portfolio);
    }

    @Override
    @Transactional
    public void deletePortfolio(Long portfolioId, Long actorId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found with ID: " + portfolioId));

        portfolio.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        portfolioRepository.save(portfolio);
    }

    private PortfolioResponse toResponse(Portfolio portfolio) {
        Long creatorId = null;
        if (portfolio.getCreatedBy() != null) {
            try {
                creatorId = Long.parseLong(portfolio.getCreatedBy());
            } catch (NumberFormatException ignored) {}
        }

        List<PortfolioProject> pps = portfolioProjectRepository.findByPortfolioIdAndIsDeletedFalse(portfolio.getId());
        List<Long> projectIds = pps.stream().map(PortfolioProject::getProjectId).collect(Collectors.toList());

        double totalProgress = 0.0;
        if (!projectIds.isEmpty()) {
            for (Long pId : projectIds) {
                try {
                    ProjectMetricsResponse m = projectMetricsService.getProjectMetrics(pId);
                    totalProgress += m.getCompletionPercentage();
                } catch (Exception ignored) {}
            }
        }

        double avgProgress = !projectIds.isEmpty() ? totalProgress / projectIds.size() : 0.0;

        return PortfolioResponse.builder()
                .id(portfolio.getId())
                .workspaceId(portfolio.getWorkspaceId())
                .name(portfolio.getName())
                .description(portfolio.getDescription())
                .ownerId(portfolio.getOwnerId())
                .status(portfolio.getStatus())
                .projectIds(projectIds)
                .overallProgressPercentage(avgProgress)
                .totalProjects(projectIds.size())
                .createdBy(creatorId)
                .createdAt(portfolio.getCreatedAt())
                .updatedAt(portfolio.getUpdatedAt())
                .build();
    }
}
