package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.TaskPermissionOverride;
import org.SprintForge.modules.workspace.task.service.TaskPermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Permission Controller", description = "REST endpoints for fine-grained task permission overrides")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TaskPermissionController {

    private final TaskPermissionService permissionService;

    @Operation(summary = "Get task permission overrides")
    @GetMapping("/{id}/permissions")
    public ResponseEntity<List<TaskPermissionOverride>> getPermissions(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.getTaskOverrides(id));
    }

    @Operation(summary = "Set task permission override for a user")
    @PatchMapping("/{id}/permissions")
    public ResponseEntity<TaskPermissionOverride> setPermission(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestParam String permission,
            @RequestParam Boolean allowed,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        TaskPermissionOverride override = permissionService.setPermissionOverride(id, userId, permission, allowed, actorId);
        return ResponseEntity.ok(override);
    }

    @Operation(summary = "Remove task permission override")
    @DeleteMapping("/{id}/permissions/{overrideId}")
    public ResponseEntity<Void> removePermission(@PathVariable Long id, @PathVariable Long overrideId) {
        permissionService.removePermissionOverride(overrideId);
        return ResponseEntity.noContent().build();
    }
}
