package org.SprintForge.modules.workspace.project.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.dto.request.*;
import org.SprintForge.modules.workspace.project.dto.response.*;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectVisibility;
import org.SprintForge.modules.workspace.project.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Milestone DTOs — resolved via wildcard imports above (dto.request.* / dto.response.*)

import org.SprintForge.modules.workspace.project.entity.ProjectCategory;
import org.SprintForge.modules.workspace.project.entity.ProjectTag;
import org.SprintForge.modules.workspace.project.service.category.ProjectCategoryService;
import org.SprintForge.modules.workspace.project.service.dashboard.ProjectDashboardService;
import org.SprintForge.modules.workspace.project.service.management.ProjectLifecycleService;
import org.SprintForge.modules.workspace.project.service.member.ProjectMemberService;
import org.SprintForge.modules.workspace.project.service.role.ProjectRoleService;
import org.SprintForge.modules.workspace.project.service.tag.ProjectTagService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Project Controller", description = "REST endpoints for managing project lifecycle, queries, settings, membership, roles, categories, and dashboard")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectLifecycleService projectLifecycleService;
    private final ProjectMemberService projectMemberService;
    private final ProjectRoleService projectRoleService;
    private final ProjectCategoryService projectCategoryService;
    private final ProjectTagService projectTagService;
    private final ProjectDashboardService projectDashboardService;

    @Operation(summary = "Create a project in a workspace")
    @PostMapping("/workspaces/{workspaceId}/projects")
    public ResponseEntity<ProjectResponse> createProject(
            @PathVariable("workspaceId") Long workspaceId,
            @Valid @RequestBody ProjectCreateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.createProject(workspaceId, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all active projects in a workspace")
    @GetMapping("/workspaces/{workspaceId}/projects")
    public ResponseEntity<List<ProjectResponse>> getProjects(
            @PathVariable("workspaceId") Long workspaceId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<ProjectResponse> response = projectService.getProjects(workspaceId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all archived projects in a workspace")
    @GetMapping("/workspaces/{workspaceId}/projects/archived")
    public ResponseEntity<List<ProjectResponse>> getArchivedProjects(
            @PathVariable("workspaceId") Long workspaceId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<ProjectResponse> response = projectService.getArchivedProjects(workspaceId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a project by ID")
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.getProject(projectId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a project")
    @PatchMapping("/projects/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody ProjectUpdateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.updateProject(projectId, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Archive a project")
    @PostMapping("/projects/{projectId}/archive")
    public ResponseEntity<ProjectResponse> archiveProject(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.archiveProject(projectId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Restore an archived project")
    @PostMapping("/projects/{projectId}/restore")
    public ResponseEntity<ProjectResponse> restoreProject(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.restoreProject(projectId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a project (soft delete)")
    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        projectService.deleteProject(projectId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Duplicate a project")
    @PostMapping("/projects/{projectId}/duplicate")
    public ResponseEntity<ProjectResponse> duplicateProject(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.duplicateProject(projectId, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Transfer ownership of a project")
    @PostMapping({"/projects/{projectId}/transfer-ownership", "/projects/{projectId}/transfer"})
    public ResponseEntity<ProjectResponse> transferOwnership(
            @PathVariable("projectId") Long projectId,
            @RequestParam(value = "newOwnerId", required = false) Long newOwnerIdParam,
            @RequestBody(required = false) TransferProjectOwnershipRequest requestBody,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        Long targetOwnerId = newOwnerIdParam != null ? newOwnerIdParam : (requestBody != null ? requestBody.getNewOwnerUserId() : null);
        ProjectResponse response = projectService.transferOwnership(projectId, targetOwnerId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Change project lead")
    @PostMapping("/projects/{projectId}/lead")
    public ResponseEntity<ProjectResponse> changeLead(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody ChangeProjectLeadRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectLifecycleService.changeLead(projectId, request.getNewLeadId(), actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Activate project")
    @PostMapping("/projects/{projectId}/lifecycle/activate")
    public ResponseEntity<ProjectResponse> activateProject(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(projectLifecycleService.activateProject(projectId, actorId));
    }

    @Operation(summary = "Pause project")
    @PostMapping("/projects/{projectId}/lifecycle/pause")
    public ResponseEntity<ProjectResponse> pauseProject(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(projectLifecycleService.pauseProject(projectId, actorId));
    }

    @Operation(summary = "Resume project")
    @PostMapping("/projects/{projectId}/lifecycle/resume")
    public ResponseEntity<ProjectResponse> resumeProject(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(projectLifecycleService.resumeProject(projectId, actorId));
    }

    @Operation(summary = "Complete project")
    @PostMapping("/projects/{projectId}/lifecycle/complete")
    public ResponseEntity<ProjectResponse> completeProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam(value = "confirmOverride", defaultValue = "false") Boolean confirmOverride,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(projectLifecycleService.completeProject(projectId, confirmOverride, actorId));
    }

    @Operation(summary = "Cancel project")
    @PostMapping("/projects/{projectId}/lifecycle/cancel")
    public ResponseEntity<ProjectResponse> cancelProject(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(projectLifecycleService.cancelProject(projectId, actorId));
    }

    @Operation(summary = "Get project dashboard summary")
    @GetMapping("/projects/{projectId}/dashboard")
    public ResponseEntity<ProjectDashboardSummaryResponse> getDashboardSummary(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(projectDashboardService.getDashboardSummary(projectId, actorId));
    }

    @Operation(summary = "Get project settings")
    @GetMapping("/projects/{projectId}/settings")
    public ResponseEntity<ProjectSettingsResponse> getSettings(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectSettingsResponse response = projectService.getSettings(projectId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update project settings")
    @PatchMapping("/projects/{projectId}/settings")
    public ResponseEntity<ProjectSettingsResponse> updateSettings(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody ProjectSettingsRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectSettingsResponse response = projectService.updateSettings(projectId, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Change project visibility")
    @PatchMapping("/projects/{projectId}/visibility")
    public ResponseEntity<ProjectResponse> changeVisibility(
            @PathVariable("projectId") Long projectId,
            @RequestParam("visibility") ProjectVisibility visibility,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.changeVisibility(projectId, visibility, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Change project color")
    @PatchMapping("/projects/{projectId}/color")
    public ResponseEntity<ProjectResponse> changeColor(
            @PathVariable("projectId") Long projectId,
            @RequestParam("color") String color,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.changeColor(projectId, color, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Change project icon")
    @PatchMapping("/projects/{projectId}/icon")
    public ResponseEntity<ProjectResponse> changeIcon(
            @PathVariable("projectId") Long projectId,
            @RequestParam("icon") String icon,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.changeIcon(projectId, icon, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update project key")
    @PatchMapping("/projects/{projectId}/key")
    public ResponseEntity<ProjectResponse> updateProjectKey(
            @PathVariable("projectId") Long projectId,
            @RequestParam("key") String key,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.updateProjectKey(projectId, key, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add a member to a project")
    @PostMapping("/projects/{projectId}/members")
    public ResponseEntity<ProjectMemberResponse> addMember(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody AddProjectMemberRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectMemberResponse response = projectService.addMember(projectId, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all members of a project")
    @GetMapping("/projects/{projectId}/members")
    public ResponseEntity<List<ProjectMemberResponse>> getMembers(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<ProjectMemberResponse> response = projectService.getMembers(projectId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove a member from a project")
    @DeleteMapping("/projects/{projectId}/members/{memberId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable("projectId") Long projectId,
            @PathVariable("memberId") Long memberId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        projectService.removeMember(projectId, memberId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Change role of a project member")
    @PatchMapping("/projects/{projectId}/members/{memberId}/role")
    public ResponseEntity<ProjectMemberResponse> changeRole(
            @PathVariable("projectId") Long projectId,
            @PathVariable("memberId") Long memberId,
            @Valid @RequestBody UpdateProjectMemberRoleRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectMemberResponse response = projectService.changeRole(projectId, memberId, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Toggle favorite status for project")
    @PostMapping("/projects/{projectId}/favorite")
    public ResponseEntity<ProjectMemberResponse> toggleFavorite(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(projectMemberService.toggleFavorite(projectId, actorId));
    }

    @Operation(summary = "Change allocation percentage of a project member")
    @PatchMapping("/projects/{projectId}/members/{memberId}/allocation")
    public ResponseEntity<ProjectMemberResponse> changeAllocation(
            @PathVariable("projectId") Long projectId,
            @PathVariable("memberId") Long memberId,
            @Valid @RequestBody UpdateMemberAllocationRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(projectMemberService.changeAllocation(projectId, memberId, request.getAllocationPercentage(), actorId));
    }

    @Operation(summary = "Get project roles")
    @GetMapping("/projects/{projectId}/roles")
    public ResponseEntity<List<ProjectRoleResponse>> getRoles(
            @PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(projectRoleService.getRoles(projectId));
    }

    @Operation(summary = "Create a project role")
    @PostMapping("/projects/{projectId}/roles")
    public ResponseEntity<ProjectRoleResponse> createRole(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody CreateProjectRoleRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectRoleService.createRole(projectId, request, actorId));
    }

    @Operation(summary = "Clone a project role")
    @PostMapping("/projects/{projectId}/roles/{roleId}/clone")
    public ResponseEntity<ProjectRoleResponse> cloneRole(
            @PathVariable("projectId") Long projectId,
            @PathVariable("roleId") Long roleId,
            @RequestParam(value = "newRoleName", required = false) String newRoleName,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectRoleService.cloneRole(roleId, newRoleName, actorId));
    }

    @Operation(summary = "Get workspace project categories")
    @GetMapping("/workspaces/{workspaceId}/project-categories")
    public ResponseEntity<List<ProjectCategory>> getCategories(
            @PathVariable("workspaceId") Long workspaceId) {
        return ResponseEntity.ok(projectCategoryService.getCategories(workspaceId));
    }

    @Operation(summary = "Create workspace project category")
    @PostMapping("/workspaces/{workspaceId}/project-categories")
    public ResponseEntity<ProjectCategory> createCategory(
            @PathVariable("workspaceId") Long workspaceId,
            @RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "color", required = false) String color,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectCategoryService.createCategory(workspaceId, name, description, color, actorId));
    }

    @Operation(summary = "Get project tags")
    @GetMapping("/projects/{projectId}/tags")
    public ResponseEntity<List<ProjectTag>> getTags(
            @PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(projectTagService.getTags(projectId));
    }

    @Operation(summary = "Create project tag")
    @PostMapping("/projects/{projectId}/tags")
    public ResponseEntity<ProjectTag> createTag(
            @PathVariable("projectId") Long projectId,
            @RequestParam("name") String name,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "description", required = false) String description,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectTagService.createTag(projectId, name, color, description, actorId));
    }

    // -------------------------------------------------------------------------
    // Milestone Endpoints
    // -------------------------------------------------------------------------

    @Operation(summary = "Create a milestone in a project")
    @PostMapping("/projects/{projectId}/milestones")
    public ResponseEntity<MilestoneResponse> createMilestone(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody MilestoneCreateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        MilestoneResponse response = projectService.createMilestone(projectId, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all active milestones in a project")
    @GetMapping("/projects/{projectId}/milestones")
    public ResponseEntity<List<MilestoneResponse>> getProjectMilestones(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<MilestoneResponse> response = projectService.getProjectMilestones(projectId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get overdue milestones in a project")
    @GetMapping("/projects/{projectId}/milestones/overdue")
    public ResponseEntity<List<MilestoneResponse>> getOverdueMilestones(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<MilestoneResponse> response = projectService.getOverdueMilestones(projectId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a milestone")
    @PatchMapping("/milestones/{milestoneId}")
    public ResponseEntity<MilestoneResponse> updateMilestone(
            @PathVariable("milestoneId") Long milestoneId,
            @Valid @RequestBody MilestoneUpdateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        MilestoneResponse response = projectService.updateMilestone(milestoneId, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Archive a milestone")
    @PostMapping("/milestones/{milestoneId}/archive")
    public ResponseEntity<MilestoneResponse> archiveMilestone(
            @PathVariable("milestoneId") Long milestoneId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        MilestoneResponse response = projectService.archiveMilestone(milestoneId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Complete a milestone")
    @PostMapping("/milestones/{milestoneId}/complete")
    public ResponseEntity<MilestoneResponse> completeMilestone(
            @PathVariable("milestoneId") Long milestoneId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        MilestoneResponse response = projectService.completeMilestone(milestoneId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a milestone (soft delete)")
    @DeleteMapping("/milestones/{milestoneId}")
    public ResponseEntity<Void> deleteMilestone(
            @PathVariable("milestoneId") Long milestoneId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        projectService.deleteMilestone(milestoneId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Assign a task to a milestone")
    @PostMapping("/milestones/{milestoneId}/tasks/{taskId}")
    public ResponseEntity<MilestoneResponse> assignTask(
            @PathVariable("milestoneId") Long milestoneId,
            @PathVariable("taskId") Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        MilestoneResponse response = projectService.assignTask(milestoneId, taskId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove a task from a milestone")
    @DeleteMapping("/milestones/{milestoneId}/tasks/{taskId}")
    public ResponseEntity<Void> removeTask(
            @PathVariable("milestoneId") Long milestoneId,
            @PathVariable("taskId") Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        projectService.removeTask(milestoneId, taskId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get milestone progress")
    @GetMapping("/milestones/{milestoneId}/progress")
    public ResponseEntity<MilestoneProgressResponse> getMilestoneProgress(
            @PathVariable("milestoneId") Long milestoneId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        MilestoneProgressResponse response = projectService.calculateProgress(milestoneId, actorId);
        return ResponseEntity.ok(response);
    }
}