package org.SprintForge.modules.workspace.project.insights.portfolio.service;

import org.SprintForge.modules.workspace.project.insights.metrics.service.ProjectMetricsService;
import org.SprintForge.modules.workspace.project.insights.portfolio.dto.request.CreatePortfolioRequest;
import org.SprintForge.modules.workspace.project.insights.portfolio.dto.response.PortfolioResponse;
import org.SprintForge.modules.workspace.project.insights.portfolio.entity.Portfolio;
import org.SprintForge.modules.workspace.project.insights.portfolio.repository.PortfolioProjectRepository;
import org.SprintForge.modules.workspace.project.insights.portfolio.repository.PortfolioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceImplTest {

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private PortfolioProjectRepository portfolioProjectRepository;

    @Mock
    private ProjectMetricsService projectMetricsService;

    @InjectMocks
    private PortfolioServiceImpl portfolioService;

    private Portfolio testPortfolio;

    @BeforeEach
    void setUp() {
        testPortfolio = new Portfolio();
        testPortfolio.setId(10L);
        testPortfolio.setWorkspaceId(1L);
        testPortfolio.setName("Core Products Portfolio");
        testPortfolio.setStatus("ACTIVE");
    }

    @Test
    void createPortfolio_Success() {
        CreatePortfolioRequest request = CreatePortfolioRequest.builder()
                .workspaceId(1L)
                .name("Core Products Portfolio")
                .projectIds(Collections.singletonList(100L))
                .build();

        when(portfolioRepository.save(any(Portfolio.class))).thenReturn(testPortfolio);

        PortfolioResponse response = portfolioService.createPortfolio(request, 1L);

        assertNotNull(response);
        assertEquals("Core Products Portfolio", response.getName());
        assertEquals(10L, response.getId());
    }

    @Test
    void getPortfolio_Success() {
        when(portfolioRepository.findById(10L)).thenReturn(Optional.of(testPortfolio));

        PortfolioResponse response = portfolioService.getPortfolio(10L);

        assertNotNull(response);
        assertEquals("Core Products Portfolio", response.getName());
    }
}
