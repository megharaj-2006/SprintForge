package org.SprintForge.modules.workspace.timelog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.timelog.dto.request.*;
import org.SprintForge.modules.workspace.timelog.dto.response.*;
import org.SprintForge.modules.workspace.timelog.service.TimeTrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Time Tracking Controller", description = "REST endpoints for task timer and logging")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TimeTrackingController {

    private final TimeTrackingService timeTrackingService;

    @Operation(summary = "Start timer for a task")
    @PostMapping("/tasks/{taskId}/time/start")
    public ResponseEntity<TimeEntryResponse> startTimer(
            @PathVariable Long taskId,
            @RequestBody(required = false) StartTimerRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        if (request == null) {
            request = new StartTimerRequest();
        }
        return ResponseEntity.ok(timeTrackingService.startTimer(taskId, request, actorId));
    }

    @Operation(summary = "Stop running timer for a task")
    @PostMapping("/tasks/{taskId}/time/stop")
    public ResponseEntity<TimeEntryResponse> stopTimer(
            @PathVariable Long taskId,
            @RequestBody(required = false) StopTimerRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        if (request == null) {
            request = new StopTimerRequest();
        }
        return ResponseEntity.ok(timeTrackingService.stopTimer(taskId, request, actorId));
    }

    @Operation(summary = "Log manual time entry for a task")
    @PostMapping("/tasks/{taskId}/time")
    public ResponseEntity<TimeEntryResponse> logTime(
            @PathVariable Long taskId,
            @Valid @RequestBody CreateTimeEntryRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(timeTrackingService.logTime(taskId, request, actorId));
    }

    @Operation(summary = "Update time entry")
    @PatchMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntryResponse> updateTimeEntry(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTimeEntryRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(timeTrackingService.updateTimeEntry(id, request, actorId));
    }

    @Operation(summary = "Delete time entry")
    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<Void> deleteTimeEntry(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        timeTrackingService.deleteTimeEntry(id, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get time entries summary for a task")
    @GetMapping("/tasks/{taskId}/time")
    public ResponseEntity<TaskTimeSummaryResponse> getTaskTimeSummary(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(timeTrackingService.getTaskTimeSummary(taskId, actorId));
    }

    @Operation(summary = "Get time entries summary for a user")
    @GetMapping("/users/{userId}/time")
    public ResponseEntity<UserTimeSummaryResponse> getUserTimeSummary(
            @PathVariable Long userId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(timeTrackingService.getUserTimeSummary(userId, actorId));
    }
}
