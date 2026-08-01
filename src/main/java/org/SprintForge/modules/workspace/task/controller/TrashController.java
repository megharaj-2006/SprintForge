package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.TrashRecord;
import org.SprintForge.modules.workspace.task.service.ArchiveService;
import org.SprintForge.modules.workspace.task.service.TrashService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Trash & Archive Controller", description = "REST endpoints for task archiving, trash management, and data restoration")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TrashController {

    private final ArchiveService archiveService;
    private final TrashService trashService;

    @Operation(summary = "Archive a task")
    @PostMapping("/tasks/{id}/archive")
    public ResponseEntity<Task> archiveTask(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(archiveService.archiveTask(id, actorId));
    }

    @Operation(summary = "Unarchive a task")
    @PostMapping("/tasks/{id}/restore-archive")
    public ResponseEntity<Task> unarchiveTask(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(archiveService.unarchiveTask(id, actorId));
    }

    @Operation(summary = "Move task to trash")
    @PostMapping("/tasks/{id}/trash")
    public ResponseEntity<TrashRecord> moveToTrash(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "User deletion") String reason,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(trashService.moveToTrash("TASK", id, reason, actorId));
    }

    @Operation(summary = "Get list of trash records")
    @GetMapping("/trash")
    public ResponseEntity<List<TrashRecord>> getTrash() {
        return ResponseEntity.ok(trashService.getTrashItems());
    }

    @Operation(summary = "Restore item from trash")
    @PostMapping("/trash/{id}/restore")
    public ResponseEntity<Void> restoreFromTrash(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        trashService.restoreFromTrash(id, actorId);
        return ResponseEntity.noContent().build();
    }
}
