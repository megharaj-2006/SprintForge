package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.request.*;
import org.SprintForge.modules.workspace.task.dto.response.OccurrencePreviewResponse;
import org.SprintForge.modules.workspace.task.dto.response.RecurringTaskResponse;
import org.SprintForge.modules.workspace.task.service.RecurringTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Recurring Task Controller", description = "REST endpoints for configuring enterprise task recurrence schedules")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class RecurringTaskController {

    private final RecurringTaskService recurringTaskService;

    @Operation(summary = "Schedule recurrence for a task")
    @PostMapping("/tasks/{id}/recurring")
    public ResponseEntity<RecurringTaskResponse> scheduleRecurringTask(
            @PathVariable Long id,
            @Valid @RequestBody CreateRecurringTaskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        RecurringTaskResponse response = recurringTaskService.scheduleRecurringTask(id, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get recurrence schedule for a task")
    @GetMapping("/tasks/{id}/recurring")
    public ResponseEntity<RecurringTaskResponse> getRecurringTaskByTaskId(@PathVariable Long id) {
        RecurringTaskResponse response = recurringTaskService.getRecurringTaskByTaskId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update recurring task schedule")
    @PatchMapping("/recurring/{id}")
    public ResponseEntity<RecurringTaskResponse> updateRecurringTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRecurringTaskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        RecurringTaskResponse response = recurringTaskService.updateRecurringTask(id, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cancel recurring task schedule")
    @DeleteMapping("/recurring/{id}")
    public ResponseEntity<Void> cancelRecurringTask(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        recurringTaskService.cancelRecurringTask(id, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Pause recurring task schedule")
    @PostMapping("/recurring/{id}/pause")
    public ResponseEntity<RecurringTaskResponse> pauseRecurringTask(
            @PathVariable Long id,
            @Valid @RequestBody(required = false) PauseRecurringTaskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        PauseRecurringTaskRequest req = request != null ? request : new PauseRecurringTaskRequest();
        RecurringTaskResponse response = recurringTaskService.pauseRecurringTask(id, req, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Resume recurring task schedule")
    @PostMapping("/recurring/{id}/resume")
    public ResponseEntity<RecurringTaskResponse> resumeRecurringTask(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        RecurringTaskResponse response = recurringTaskService.resumeRecurringTask(id, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Preview future occurrence dates for a recurring task")
    @PostMapping("/recurring/{id}/preview")
    public ResponseEntity<OccurrencePreviewResponse> previewOccurrences(
            @PathVariable Long id,
            @Valid @RequestBody PreviewOccurrencesRequest request) {
        OccurrencePreviewResponse response = recurringTaskService.previewOccurrences(id, request);
        return ResponseEntity.ok(response);
    }
}
