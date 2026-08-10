package org.SprintForge.modules.workspace.workspace.department.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.department.dto.request.CreateDepartmentRequest;
import org.SprintForge.modules.workspace.workspace.department.dto.request.UpdateDepartmentRequest;
import org.SprintForge.modules.workspace.workspace.department.dto.response.DepartmentResponse;
import org.SprintForge.modules.workspace.workspace.department.dto.response.DepartmentSummaryResponse;
import org.SprintForge.modules.workspace.workspace.department.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Department Management", description = "Endpoints for managing departments")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/workspaces/{workspaceId}/departments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a department")
    public DepartmentResponse createDepartment(@PathVariable Long workspaceId, 
                                               @Valid @RequestBody CreateDepartmentRequest request, 
                                               @RequestHeader("X-User-Id") Long actorId) {
        request.setWorkspaceId(workspaceId);
        return departmentService.createDepartment(request, actorId);
    }

    @GetMapping("/workspaces/{workspaceId}/departments")
    @Operation(summary = "List departments by workspace")
    public List<DepartmentResponse> listByWorkspace(@PathVariable Long workspaceId) {
        return departmentService.listByWorkspace(workspaceId);
    }

    @GetMapping("/departments/{id}")
    @Operation(summary = "Get department by ID")
    public DepartmentResponse getDepartment(@PathVariable Long id) {
        return departmentService.getDepartment(id);
    }

    @PatchMapping("/departments/{id}")
    @Operation(summary = "Update department")
    public DepartmentResponse updateDepartment(@PathVariable Long id, 
                                               @Valid @RequestBody UpdateDepartmentRequest request, 
                                               @RequestHeader("X-User-Id") Long actorId) {
        return departmentService.updateDepartment(id, request, actorId);
    }

    @PostMapping("/departments/{id}/archive")
    @Operation(summary = "Archive department")
    public void archiveDepartment(@PathVariable Long id, @RequestHeader("X-User-Id") Long actorId) {
        departmentService.archiveDepartment(id, actorId);
    }

    @PostMapping("/departments/{id}/restore")
    @Operation(summary = "Restore department")
    public void restoreDepartment(@PathVariable Long id, @RequestHeader("X-User-Id") Long actorId) {
        departmentService.restoreDepartment(id, actorId);
    }

    @PostMapping("/departments/{id}/head")
    @Operation(summary = "Assign department head")
    public DepartmentResponse assignHead(@PathVariable Long id, 
                                         @RequestParam Long headMemberId, 
                                         @RequestHeader("X-User-Id") Long actorId) {
        return departmentService.assignHead(id, headMemberId, actorId);
    }

    @PostMapping("/departments/{id}/teams/{teamId}")
    @Operation(summary = "Move team to department")
    public void moveTeamToDepartment(@PathVariable Long id, 
                                     @PathVariable Long teamId, 
                                     @RequestHeader("X-User-Id") Long actorId) {
        departmentService.moveTeamToDepartment(teamId, id, actorId);
    }
}
