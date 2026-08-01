package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.request.ExportTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.ImportTaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.ExportJobResponse;
import org.SprintForge.modules.workspace.task.dto.response.ImportResultResponse;
import org.SprintForge.modules.workspace.task.service.TaskExportService;
import org.SprintForge.modules.workspace.task.service.TaskImportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Import & Export Controller", description = "REST endpoints for enterprise CSV, Excel, and JSON task migration")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ImportExportController {

    private final TaskImportService importService;
    private final TaskExportService exportService;

    @Operation(summary = "Import tasks from CSV, Excel, or JSON")
    @PostMapping("/import")
    public ResponseEntity<ImportResultResponse> importTasks(
            @Valid @RequestBody ImportTaskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ImportResultResponse response = importService.importTasks(request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Export tasks to CSV, Excel, JSON, or PDF")
    @PostMapping("/export")
    public ResponseEntity<ExportJobResponse> exportTasks(
            @Valid @RequestBody ExportTaskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ExportJobResponse response = exportService.exportTasks(request, actorId);
        return ResponseEntity.ok(response);
    }
}
