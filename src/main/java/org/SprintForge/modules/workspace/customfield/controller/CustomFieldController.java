package org.SprintForge.modules.workspace.customfield.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.customfield.dto.request.AssignCustomFieldRequest;
import org.SprintForge.modules.workspace.customfield.dto.request.CreateCustomFieldRequest;
import org.SprintForge.modules.workspace.customfield.dto.request.UpdateCustomFieldRequest;
import org.SprintForge.modules.workspace.customfield.dto.response.CustomFieldResponse;
import org.SprintForge.modules.workspace.customfield.dto.response.TaskCustomFieldResponse;
import org.SprintForge.modules.workspace.customfield.service.CustomFieldManagementService;
import org.SprintForge.modules.workspace.customfield.service.TaskCustomFieldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Custom Field Controller", description = "REST endpoints for managing project-specific custom fields and task custom field values")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class CustomFieldController {

    private final CustomFieldManagementService customFieldManagementService;
    private final TaskCustomFieldService taskCustomFieldService;

    @Operation(summary = "Create a custom field definition for a project")
    @PostMapping("/projects/{projectId}/custom-fields")
    public ResponseEntity<CustomFieldResponse> createField(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody CreateCustomFieldRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        CustomFieldResponse response = customFieldManagementService.createField(projectId, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all custom field definitions in a project")
    @GetMapping("/projects/{projectId}/custom-fields")
    public ResponseEntity<List<CustomFieldResponse>> getProjectFields(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<CustomFieldResponse> response = customFieldManagementService.getProjectFields(projectId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a custom field definition")
    @PatchMapping("/custom-fields/{id}")
    public ResponseEntity<CustomFieldResponse> updateField(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateCustomFieldRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        CustomFieldResponse response = customFieldManagementService.updateField(id, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a custom field definition")
    @DeleteMapping("/custom-fields/{id}")
    public ResponseEntity<Void> deleteField(
            @PathVariable("id") Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        customFieldManagementService.deleteField(id, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Assign or update custom field value on a task")
    @PatchMapping("/tasks/{taskId}/custom-fields")
    public ResponseEntity<TaskCustomFieldResponse> assignValue(
            @PathVariable("taskId") Long taskId,
            @Valid @RequestBody AssignCustomFieldRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        TaskCustomFieldResponse response = taskCustomFieldService.assignValue(taskId, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all custom field values for a task")
    @GetMapping("/tasks/{taskId}/custom-fields")
    public ResponseEntity<List<TaskCustomFieldResponse>> getTaskFields(
            @PathVariable("taskId") Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<TaskCustomFieldResponse> response = taskCustomFieldService.getTaskFields(taskId, actorId);
        return ResponseEntity.ok(response);
    }
}
