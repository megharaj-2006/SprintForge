package org.SprintForge.modules.workspace.project.insights.resource.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.insights.resource.dto.CapacityPlanningResponse;
import org.SprintForge.modules.workspace.project.insights.resource.dto.ResourceAllocationResponse;
import org.SprintForge.modules.workspace.project.insights.resource.dto.UtilizationResponse;
import org.SprintForge.modules.workspace.project.insights.resource.service.ResourceAllocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("insightsResourceAllocationController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Resource Allocation Controller", description = "REST endpoints for enterprise capacity planning, workload utilization, and bottleneck detection")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ResourceAllocationController {

    private final ResourceAllocationService resourceAllocationService;

    @Operation(summary = "Get resource allocation and role hours breakdown for a project")
    @GetMapping("/projects/{projectId}/resources")
    public ResponseEntity<ResourceAllocationResponse> getResourceAllocation(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(resourceAllocationService.getResourceAllocation(projectId));
    }

    @Operation(summary = "Get capacity planning and net capacity margin for a project")
    @GetMapping("/projects/{projectId}/capacity")
    public ResponseEntity<CapacityPlanningResponse> getCapacityPlanning(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(resourceAllocationService.getCapacityPlanning(projectId));
    }

    @Operation(summary = "Get team utilization metrics and overallocation counts for a project")
    @GetMapping("/projects/{projectId}/utilization")
    public ResponseEntity<UtilizationResponse> getUtilization(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(resourceAllocationService.getUtilization(projectId));
    }
}
