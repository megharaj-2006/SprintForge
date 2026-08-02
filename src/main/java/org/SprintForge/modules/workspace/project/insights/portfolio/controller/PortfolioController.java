package org.SprintForge.modules.workspace.project.insights.portfolio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.insights.portfolio.dto.request.CreatePortfolioRequest;
import org.SprintForge.modules.workspace.project.insights.portfolio.dto.request.UpdatePortfolioRequest;
import org.SprintForge.modules.workspace.project.insights.portfolio.dto.response.PortfolioResponse;
import org.SprintForge.modules.workspace.project.insights.portfolio.service.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("insightsPortfolioController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Portfolio Controller", description = "REST endpoints for managing multi-project portfolios and enterprise rollups")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Operation(summary = "Create a multi-project portfolio")
    @PostMapping("/portfolios")
    public ResponseEntity<PortfolioResponse> createPortfolio(
            @Valid @RequestBody CreatePortfolioRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(portfolioService.createPortfolio(request, actorId));
    }

    @Operation(summary = "Get all portfolios for a workspace")
    @GetMapping("/portfolios")
    public ResponseEntity<List<PortfolioResponse>> getPortfolios(@RequestParam("workspaceId") Long workspaceId) {
        return ResponseEntity.ok(portfolioService.getPortfolios(workspaceId));
    }

    @Operation(summary = "Get portfolio details by ID")
    @GetMapping("/portfolios/{portfolioId}")
    public ResponseEntity<PortfolioResponse> getPortfolio(@PathVariable("portfolioId") Long portfolioId) {
        return ResponseEntity.ok(portfolioService.getPortfolio(portfolioId));
    }

    @Operation(summary = "Update portfolio metadata or project assignments")
    @PatchMapping("/portfolios/{portfolioId}")
    public ResponseEntity<PortfolioResponse> updatePortfolio(
            @PathVariable("portfolioId") Long portfolioId,
            @Valid @RequestBody UpdatePortfolioRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(portfolioService.updatePortfolio(portfolioId, request, actorId));
    }

    @Operation(summary = "Delete / archive portfolio")
    @DeleteMapping("/portfolios/{portfolioId}")
    public ResponseEntity<Void> deletePortfolio(
            @PathVariable("portfolioId") Long portfolioId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        portfolioService.deletePortfolio(portfolioId, actorId);
        return ResponseEntity.noContent().build();
    }
}
