package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.request.AddWatcherRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskWatcherResponse;
import org.SprintForge.modules.workspace.task.service.TaskWatcherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Watcher Controller", description = "REST endpoints for managing task watchers")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TaskWatcherController {

    private final TaskWatcherService taskWatcherService;

    @Operation(summary = "Add a watcher to a task")
    @PostMapping("/tasks/{taskId}/watchers")
    public ResponseEntity<TaskWatcherResponse> addWatcher(
            @PathVariable Long taskId,
            @Valid @RequestBody AddWatcherRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskWatcherService.addWatcher(taskId, request, actorId));
    }

    @Operation(summary = "Remove a watcher from a task")
    @DeleteMapping("/tasks/{taskId}/watchers/{userId}")
    public ResponseEntity<Void> removeWatcher(
            @PathVariable Long taskId,
            @PathVariable Long userId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        taskWatcherService.removeWatcher(taskId, userId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all watchers of a task")
    @GetMapping("/tasks/{taskId}/watchers")
    public ResponseEntity<List<TaskWatcherResponse>> getTaskWatchers(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskWatcherService.getTaskWatchers(taskId, actorId));
    }

    @Operation(summary = "Get tasks watched by a user")
    @GetMapping("/users/{userId}/watching")
    public ResponseEntity<List<TaskResponse>> getWatchingTasks(
            @PathVariable Long userId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskWatcherService.getWatchingTasks(userId, actorId));
    }
}
