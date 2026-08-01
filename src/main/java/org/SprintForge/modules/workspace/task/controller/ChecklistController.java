package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.request.CreateChecklistRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateChecklistRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateChecklistItemRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateChecklistItemRequest;
import org.SprintForge.modules.workspace.task.dto.request.MoveChecklistItemRequest;
import org.SprintForge.modules.workspace.task.dto.response.ChecklistResponse;
import org.SprintForge.modules.workspace.task.dto.response.ChecklistItemResponse;
import org.SprintForge.modules.workspace.task.service.ChecklistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Checklist Controller", description = "REST endpoints for managing task checklists and items")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ChecklistController {

    private final ChecklistService checklistService;

    @Operation(summary = "Create a checklist for a task")
    @PostMapping("/tasks/{taskId}/checklists")
    public ResponseEntity<ChecklistResponse> createChecklist(
            @PathVariable Long taskId,
            @Valid @RequestBody CreateChecklistRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(checklistService.createChecklist(taskId, request, actorId));
    }

    @Operation(summary = "Get all checklists for a task")
    @GetMapping("/tasks/{taskId}/checklists")
    public ResponseEntity<List<ChecklistResponse>> getTaskChecklists(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(checklistService.getTaskChecklists(taskId, actorId));
    }

    @Operation(summary = "Update a checklist title")
    @PatchMapping("/checklists/{id}")
    public ResponseEntity<ChecklistResponse> updateChecklist(
            @PathVariable Long id,
            @Valid @RequestBody UpdateChecklistRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(checklistService.updateChecklist(id, request, actorId));
    }

    @Operation(summary = "Delete a checklist")
    @DeleteMapping("/checklists/{id}")
    public ResponseEntity<Void> deleteChecklist(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        checklistService.deleteChecklist(id, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add an item to a checklist")
    @PostMapping("/checklists/{id}/items")
    public ResponseEntity<ChecklistItemResponse> addItem(
            @PathVariable Long id,
            @Valid @RequestBody CreateChecklistItemRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(checklistService.addItem(id, request, actorId));
    }

    @Operation(summary = "Update details of a checklist item")
    @PatchMapping("/checklist-items/{id}")
    public ResponseEntity<ChecklistItemResponse> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody UpdateChecklistItemRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(checklistService.updateItem(id, request, actorId));
    }

    @Operation(summary = "Delete a checklist item")
    @DeleteMapping("/checklist-items/{id}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        checklistService.deleteItem(id, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Complete or uncomplete a checklist item")
    @PatchMapping("/checklist-items/{id}/complete")
    public ResponseEntity<ChecklistItemResponse> completeItem(
            @PathVariable Long id,
            @RequestParam Boolean completed,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(checklistService.completeItem(id, completed, actorId));
    }

    @Operation(summary = "Reorder items in a checklist")
    @PatchMapping("/checklist-items/reorder")
    public ResponseEntity<Void> reorderItems(
            @Valid @RequestBody List<MoveChecklistItemRequest> request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        checklistService.reorderItems(request, actorId);
        return ResponseEntity.ok().build();
    }
}
