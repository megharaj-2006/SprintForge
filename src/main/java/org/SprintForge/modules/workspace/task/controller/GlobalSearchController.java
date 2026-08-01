package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.entity.enums.TaskPriority;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.service.GlobalTaskSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
@Validated
@Tag(name = "Global Search Controller", description = "REST endpoints for specification-based global task search and auto-suggestions")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class GlobalSearchController {

    private final GlobalTaskSearchService searchService;

    @Operation(summary = "Global search tasks with advanced specification filters")
    @GetMapping
    public ResponseEntity<List<TaskResponse>> searchTasks(
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "projectId", required = false) Long projectId,
            @RequestParam(value = "status", required = false) TaskStatus status,
            @RequestParam(value = "priority", required = false) TaskPriority priority,
            @RequestParam(value = "sprintId", required = false) Long sprintId,
            @RequestParam(value = "isOverdue", required = false) Boolean isOverdue,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        List<TaskResponse> response = searchService.globalSearch(query, projectId, status, priority, sprintId, isOverdue, page, size);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get instant auto-complete suggestions for task search")
    @GetMapping("/suggestions")
    public ResponseEntity<List<String>> getSuggestions(@RequestParam(value = "q") String query) {
        List<String> suggestions = searchService.getSearchSuggestions(query);
        return ResponseEntity.ok(suggestions);
    }
}
