package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.request.*;
import org.SprintForge.modules.workspace.task.dto.response.BulkOperationResponse;
import org.SprintForge.modules.workspace.task.service.BulkTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks/bulk")
@RequiredArgsConstructor
@Validated
@Tag(name = "Bulk Task Controller", description = "REST endpoints for performing high-throughput batch operations on tasks")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class BulkTaskController {

    private final BulkTaskService bulkTaskService;

    @Operation(summary = "Bulk assign tasks to a user or unassign")
    @PostMapping("/assign")
    public ResponseEntity<BulkOperationResponse> bulkAssign(
            @Valid @RequestBody BulkAssignRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        BulkOperationResponse response = bulkTaskService.bulkAssign(request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Bulk update task status")
    @PostMapping("/status")
    public ResponseEntity<BulkOperationResponse> bulkStatus(
            @Valid @RequestBody BulkStatusRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        BulkOperationResponse response = bulkTaskService.bulkStatus(request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Bulk update task priority")
    @PostMapping("/priority")
    public ResponseEntity<BulkOperationResponse> bulkPriority(
            @Valid @RequestBody BulkPriorityRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        BulkOperationResponse response = bulkTaskService.bulkPriority(request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Bulk archive tasks")
    @PostMapping("/archive")
    public ResponseEntity<BulkOperationResponse> bulkArchive(
            @Valid @RequestBody BulkArchiveRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        BulkOperationResponse response = bulkTaskService.bulkArchive(request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Bulk soft-delete tasks")
    @PostMapping("/delete")
    public ResponseEntity<BulkOperationResponse> bulkDelete(
            @Valid @RequestBody BulkDeleteRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        BulkOperationResponse response = bulkTaskService.bulkDelete(request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Bulk move tasks to a sprint")
    @PostMapping("/sprint")
    public ResponseEntity<BulkOperationResponse> bulkMoveSprint(
            @Valid @RequestBody BulkMoveSprintRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        BulkOperationResponse response = bulkTaskService.bulkMoveSprint(request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Bulk move tasks to a milestone")
    @PostMapping("/milestone")
    public ResponseEntity<BulkOperationResponse> bulkMoveMilestone(
            @Valid @RequestBody BulkMoveMilestoneRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        BulkOperationResponse response = bulkTaskService.bulkMoveMilestone(request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Bulk add or remove labels on tasks")
    @PostMapping("/labels")
    public ResponseEntity<BulkOperationResponse> bulkLabels(
            @Valid @RequestBody BulkLabelRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        BulkOperationResponse response = bulkTaskService.bulkLabels(request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Bulk update custom field value on tasks")
    @PostMapping("/custom-fields")
    public ResponseEntity<BulkOperationResponse> bulkCustomField(
            @Valid @RequestBody BulkCustomFieldRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        BulkOperationResponse response = bulkTaskService.bulkCustomField(request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Bulk restore archived or deleted tasks")
    @PostMapping("/restore")
    public ResponseEntity<BulkOperationResponse> bulkRestore(
            @RequestBody List<Long> taskIds,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        BulkOperationResponse response = bulkTaskService.bulkRestore(taskIds, actorId);
        return ResponseEntity.ok(response);
    }
}
