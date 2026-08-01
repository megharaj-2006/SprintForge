package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.response.MyTasksSummaryResponse;
import org.SprintForge.modules.workspace.task.service.MyTaskWorkspaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/me/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "My Tasks Controller", description = "REST endpoints for personal task workspace and daily dashboard")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class MyTasksController {

    private final MyTaskWorkspaceService myTaskWorkspaceService;

    @Operation(summary = "Get personal tasks overview (assigned, due today, overdue)")
    @GetMapping
    public ResponseEntity<MyTasksSummaryResponse> getMyTasks(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(myTaskWorkspaceService.getMyTasksSummary(actorId));
    }

    @Operation(summary = "Get tasks due today")
    @GetMapping("/today")
    public ResponseEntity<MyTasksSummaryResponse> getMyTasksToday(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(myTaskWorkspaceService.getMyTasksSummary(actorId));
    }

    @Operation(summary = "Get overdue personal tasks")
    @GetMapping("/overdue")
    public ResponseEntity<MyTasksSummaryResponse> getMyOverdueTasks(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(myTaskWorkspaceService.getMyTasksSummary(actorId));
    }

    @Operation(summary = "Get blocked personal tasks")
    @GetMapping("/blocked")
    public ResponseEntity<MyTasksSummaryResponse> getMyBlockedTasks(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(myTaskWorkspaceService.getMyTasksSummary(actorId));
    }
}
