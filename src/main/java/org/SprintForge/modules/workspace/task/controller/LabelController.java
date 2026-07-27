package org.SprintForge.modules.workspace.task.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import org.SprintForge.modules.workspace.task.dto.request.AssignLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.RemoveLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.response.LabelResponse;
import org.SprintForge.modules.workspace.task.dto.response.LabelSummaryResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskLabelResponse;
import org.SprintForge.modules.workspace.task.service.label.LabelManagementService;
import org.SprintForge.modules.workspace.task.service.label.TaskLabelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class LabelController {

    private final LabelManagementService labelManagementService;
    private final TaskLabelService taskLabelService;

    public LabelController(LabelManagementService labelManagementService, TaskLabelService taskLabelService) {
        this.labelManagementService = labelManagementService;
        this.taskLabelService = taskLabelService;
    }

    // Label CRUD operations (project-scoped)
    @PostMapping("/projects/{projectId}/labels")
    public ResponseEntity<LabelResponse> createLabel(@PathVariable Long projectId,
                                                     @Valid @RequestBody CreateLabelRequest request,
                                                     @RequestHeader("X-User-Id") Long actorId) {
        request.setProjectId(projectId);
        LabelResponse response = labelManagementService.createLabel(request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/projects/{projectId}/labels")
    public ResponseEntity<List<LabelResponse>> getProjectLabels(@PathVariable Long projectId) {
        List<LabelResponse> responses = labelManagementService.getLabelsByProject(projectId);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/labels/{labelId}")
    public ResponseEntity<LabelResponse> updateLabel(@PathVariable Long labelId,
                                                     @Valid @RequestBody UpdateLabelRequest request,
                                                     @RequestHeader("X-User-Id") Long actorId) {
        request.setId(labelId);
        LabelResponse response = labelManagementService.updateLabel(labelId, request, actorId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/labels/{labelId}")
    public ResponseEntity<Void> deleteLabel(@PathVariable Long labelId,
                                            @RequestHeader("X-User-Id") Long actorId) {
        labelManagementService.deleteLabel(labelId, actorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/labels/{labelId}")
    public ResponseEntity<LabelResponse> getLabel(@PathVariable Long labelId) {
        LabelResponse response = labelManagementService.getLabel(labelId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/labels/{labelId}/usage-count")
    public ResponseEntity<Long> countTasksUsingLabel(@PathVariable Long labelId) {
        long count = labelManagementService.countTasksUsingLabel(labelId);
        return ResponseEntity.ok(count);
    }

    // Label assignment operations
    @PostMapping("/tasks/{taskId}/labels/{labelId}")
    public ResponseEntity<Void> assignLabel(@PathVariable Long taskId,
                                            @PathVariable Long labelId,
                                            @RequestHeader("X-User-Id") Long actorId) {
        AssignLabelRequest request = new AssignLabelRequest();
        request.setTaskId(taskId);
        request.setLabelId(labelId);
        taskLabelService.assignLabel(request, actorId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/tasks/{taskId}/labels/{labelId}")
    public ResponseEntity<Void> removeLabel(@PathVariable Long taskId,
                                            @PathVariable Long labelId,
                                            @RequestHeader("X-User-Id") Long actorId) {
        RemoveLabelRequest request = new RemoveLabelRequest();
        request.setTaskId(taskId);
        request.setLabelId(labelId);
        taskLabelService.removeLabel(request, actorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tasks/{taskId}/labels")
    public ResponseEntity<List<TaskLabelResponse>> getTaskLabels(@PathVariable Long taskId,
                                                                 @RequestHeader("X-User-Id") Long actorId) {
        List<TaskLabelResponse> responses = taskLabelService.getTaskLabels(taskId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/labels/{labelId}/tasks")
    public ResponseEntity<List<Long>> getTasksByLabel(@PathVariable Long labelId,
                                                      @RequestHeader("X-User-Id") Long actorId) {
        List<Long> response = taskLabelService.getTaskIdsByLabel(labelId);
        return ResponseEntity.ok(response);
    }
}