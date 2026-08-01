package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.request.ScheduleTaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskScheduleResponse;
import org.SprintForge.modules.workspace.task.service.TaskSchedulingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Scheduling Controller", description = "REST endpoints for task date scheduling, overdue tracking, and deadlines")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TaskSchedulingController {

    private final TaskSchedulingService taskSchedulingService;

    @Operation(summary = "Schedule task start date, due date, and target date")
    @PatchMapping("/{id}/schedule")
    public ResponseEntity<TaskScheduleResponse> scheduleTask(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleTaskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        TaskScheduleResponse response = taskSchedulingService.scheduleTask(id, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all overdue tasks")
    @GetMapping("/overdue")
    public ResponseEntity<List<TaskScheduleResponse>> getOverdueTasks() {
        List<TaskScheduleResponse> response = taskSchedulingService.getOverdueTasks();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get upcoming tasks due within 7 days")
    @GetMapping("/upcoming")
    public ResponseEntity<List<TaskScheduleResponse>> getUpcomingTasks() {
        List<TaskScheduleResponse> response = taskSchedulingService.getUpcomingTasks();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get tasks due today")
    @GetMapping("/today")
    public ResponseEntity<List<TaskScheduleResponse>> getTasksDueToday() {
        List<TaskScheduleResponse> response = taskSchedulingService.getTasksDueToday();
        return ResponseEntity.ok(response);
    }
}
