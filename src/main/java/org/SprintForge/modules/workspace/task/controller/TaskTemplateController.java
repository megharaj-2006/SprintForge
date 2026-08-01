package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.request.*;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskTemplateDetailResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskTemplateResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskTemplateSummaryResponse;
import org.SprintForge.modules.workspace.task.service.TaskTemplateApplicationService;
import org.SprintForge.modules.workspace.task.service.TaskTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Template Controller", description = "REST endpoints for managing reusable task templates")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TaskTemplateController {

    private final TaskTemplateService templateService;
    private final TaskTemplateApplicationService applicationService;

    @Operation(summary = "Create task template in workspace")
    @PostMapping("/workspaces/{workspaceId}/task-templates")
    public ResponseEntity<TaskTemplateResponse> createTemplate(
            @PathVariable Long workspaceId,
            @Valid @RequestBody CreateTaskTemplateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        request.setWorkspaceId(workspaceId);
        TaskTemplateResponse response = templateService.createTemplate(workspaceId, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get workspace task templates")
    @GetMapping("/workspaces/{workspaceId}/task-templates")
    public ResponseEntity<List<TaskTemplateSummaryResponse>> getWorkspaceTemplates(
            @PathVariable Long workspaceId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<TaskTemplateSummaryResponse> response = templateService.getWorkspaceTemplates(workspaceId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get task template by ID")
    @GetMapping("/task-templates/{id}")
    public ResponseEntity<TaskTemplateResponse> getTemplateById(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        TaskTemplateResponse response = templateService.getTemplateById(id, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get detailed task template information")
    @GetMapping("/task-templates/{id}/details")
    public ResponseEntity<TaskTemplateDetailResponse> getTemplateDetails(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        TaskTemplateDetailResponse response = templateService.getTemplateDetails(id, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update task template")
    @PatchMapping("/task-templates/{id}")
    public ResponseEntity<TaskTemplateResponse> updateTemplate(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskTemplateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        TaskTemplateResponse response = templateService.updateTemplate(id, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete task template")
    @DeleteMapping("/task-templates/{id}")
    public ResponseEntity<Void> deleteTemplate(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        templateService.deleteTemplate(id, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Apply task template to create a new task")
    @PostMapping("/task-templates/{id}/apply")
    public ResponseEntity<TaskResponse> applyTemplate(
            @PathVariable Long id,
            @Valid @RequestBody ApplyTaskTemplateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        TaskResponse response = applicationService.applyTemplate(id, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Duplicate task template")
    @PostMapping("/task-templates/{id}/duplicate")
    public ResponseEntity<TaskTemplateResponse> duplicateTemplate(
            @PathVariable Long id,
            @Valid @RequestBody DuplicateTaskTemplateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        TaskTemplateResponse response = templateService.duplicateTemplate(id, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Toggle favorite status on task template")
    @PostMapping("/task-templates/{id}/favorite")
    public ResponseEntity<TaskTemplateResponse> favoriteTemplate(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        TaskTemplateResponse response = templateService.favoriteTemplate(id, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Share task template publicly in workspace")
    @PostMapping("/task-templates/{id}/share")
    public ResponseEntity<TaskTemplateResponse> shareTemplate(
            @PathVariable Long id,
            @Valid @RequestBody ShareTemplateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        TaskTemplateResponse response = templateService.shareTemplate(id, request, actorId);
        return ResponseEntity.ok(response);
    }
}
