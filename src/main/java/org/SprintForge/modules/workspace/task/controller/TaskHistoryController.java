package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.response.TaskHistoryResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskHistorySummaryResponse;
import org.SprintForge.modules.workspace.task.service.TaskHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Task History Controller", description = "REST endpoints for retrieving task audit logs and history feeds")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TaskHistoryController {

    private final TaskHistoryService taskHistoryService;

    @Operation(summary = "Get full chronological history for a task")
    @GetMapping("/tasks/{taskId}/history")
    public ResponseEntity<List<TaskHistoryResponse>> getTaskHistory(
            @PathVariable("taskId") Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<TaskHistoryResponse> response = taskHistoryService.getTaskHistory(taskId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get recent activity summary feed for a task")
    @GetMapping("/tasks/{taskId}/activity")
    public ResponseEntity<List<TaskHistorySummaryResponse>> getRecentActivity(
            @PathVariable("taskId") Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<TaskHistorySummaryResponse> response = taskHistoryService.getRecentActivity(taskId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Clear/delete audit history for a task")
    @DeleteMapping("/tasks/{taskId}/history")
    public ResponseEntity<Void> deleteHistory(
            @PathVariable("taskId") Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        taskHistoryService.deleteHistory(taskId, actorId);
        return ResponseEntity.noContent().build();
    }
}
