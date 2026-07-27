package org.SprintForge.modules.workspace.task.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.request.AssignTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.MoveTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.ReassignTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.ChangeTaskStatusRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateTaskDependencyRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateSubtaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.MoveSubtaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.AllowedTransitionsResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskDependencyResponse;
import org.SprintForge.modules.workspace.task.dto.request.CreateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.response.LabelResponse;
import org.SprintForge.modules.workspace.task.dto.response.SubtaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskHierarchyResponse;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.dto.response.TaskAssignmentResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskAssigneeResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Controller", description = "REST endpoints for managing tasks lifecycle, queries and workflow")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Create a new task for a project")
    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<TaskResponse> createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody CreateTaskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        request.setProjectId(projectId);
        TaskResponse response = taskService.createTask(request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get tasks for a project")
    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<List<TaskResponse>> getProjectTasks(
            @PathVariable Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.getTasks(projectId, actorId));
    }

    @Operation(summary = "Get task by ID")
    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponse> getTask(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.getTask(taskId, actorId));
    }

    @Operation(summary = "Update task details")
    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long taskId,
            @Valid @RequestBody UpdateTaskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.updateTask(taskId, request, actorId));
    }

    @Operation(summary = "Delete a task")
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        taskService.deleteTask(taskId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Archive a task")
    @PostMapping("/tasks/{taskId}/archive")
    public ResponseEntity<TaskResponse> archiveTask(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.archiveTask(taskId, actorId));
    }

    @Operation(summary = "Restore an archived task")
    @PostMapping("/tasks/{taskId}/restore")
    public ResponseEntity<TaskResponse> restoreTask(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.restoreTask(taskId, actorId));
    }

    @Operation(summary = "Move task to a sprint")
    @PostMapping("/tasks/{taskId}/move")
    public ResponseEntity<TaskResponse> moveTask(
            @PathVariable Long taskId,
            @Valid @RequestBody MoveTaskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        if (request.getSprintId() != null) {
            return ResponseEntity.ok(taskService.moveTaskToSprint(taskId, request.getSprintId(), actorId));
        } else {
            return ResponseEntity.ok(taskService.removeFromSprint(taskId, actorId));
        }
    }

    @Operation(summary = "Assign a project member to a task")
    @PostMapping("/tasks/{taskId}/assign")
    public ResponseEntity<TaskAssignmentResponse> assignMember(
            @PathVariable Long taskId,
            @Valid @RequestBody AssignTaskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        TaskAssignmentResponse response = taskService.assignMember(taskId, request.getProjectMemberId(), actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Bulk assign project members to a task")
    @PostMapping("/tasks/{taskId}/assign/bulk")
    public ResponseEntity<List<TaskAssignmentResponse>> assignMembers(
            @PathVariable Long taskId,
            @RequestBody @NotEmpty(message = "Member list must not be empty") List<Long> projectMemberIds,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<TaskAssignmentResponse> response = taskService.assignMembers(taskId, projectMemberIds, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Unassign a project member from a task")
    @DeleteMapping("/tasks/{taskId}/assignees/{projectMemberId}")
    public ResponseEntity<Void> unassignMember(
            @PathVariable Long taskId,
            @PathVariable Long projectMemberId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        taskService.unassignMember(taskId, projectMemberId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reassign task assignees")
    @PutMapping("/tasks/{taskId}/reassign")
    public ResponseEntity<List<TaskAssignmentResponse>> reassignTask(
            @PathVariable Long taskId,
            @Valid @RequestBody ReassignTaskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<TaskAssignmentResponse> response = taskService.reassignTask(taskId, request.getProjectMemberIds(), actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get assignees of a task")
    @GetMapping("/tasks/{taskId}/assignees")
    public ResponseEntity<List<TaskAssigneeResponse>> getTaskAssignees(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.getAssignees(taskId, actorId));
    }

    @Operation(summary = "Get tasks assigned to a project member")
    @GetMapping("/members/{projectMemberId}/tasks")
    public ResponseEntity<List<TaskResponse>> getMemberTasks(
            @PathVariable Long projectMemberId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.getAssignedTasks(projectMemberId, actorId));
    }

    @Operation(summary = "Change task status")
    @PatchMapping("/tasks/{taskId}/status")
    public ResponseEntity<TaskResponse> changeStatus(
            @PathVariable Long taskId,
            @Valid @RequestBody ChangeTaskStatusRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.changeStatus(taskId, request.getStatus(), actorId));
    }

    @Operation(summary = "Start progress on a task")
    @PostMapping("/tasks/{taskId}/start")
    public ResponseEntity<TaskResponse> startTask(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.startTask(taskId, actorId));
    }

    @Operation(summary = "Submit task for review")
    @PostMapping("/tasks/{taskId}/review")
    public ResponseEntity<TaskResponse> sendForReview(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.sendForReview(taskId, actorId));
    }

    @Operation(summary = "Complete task")
    @PostMapping("/tasks/{taskId}/complete")
    public ResponseEntity<TaskResponse> completeTask(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.completeTask(taskId, actorId));
    }

    @Operation(summary = "Cancel task")
    @PostMapping("/tasks/{taskId}/cancel")
    public ResponseEntity<TaskResponse> cancelTask(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.cancelTask(taskId, actorId));
    }

    @Operation(summary = "Reopen task")
    @PostMapping("/tasks/{taskId}/reopen")
    public ResponseEntity<TaskResponse> reopenTask(
            @PathVariable Long taskId,
            @RequestBody(required = false) ChangeTaskStatusRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        TaskStatus targetStatus = (request != null) ? request.getStatus() : null;
        return ResponseEntity.ok(taskService.reopenTask(taskId, targetStatus, actorId));
    }

    @Operation(summary = "Get allowed transitions for a task")
    @GetMapping("/tasks/{taskId}/allowed-transitions")
    public ResponseEntity<AllowedTransitionsResponse> getAllowedTransitions(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<TaskStatus> allowed = taskService.getAllowedTransitions(taskId, actorId);
        AllowedTransitionsResponse response = AllowedTransitionsResponse.builder()
                .taskId(taskId)
                .allowedTransitions(allowed)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add a task dependency")
    @PostMapping("/tasks/{taskId}/dependencies")
    public ResponseEntity<TaskDependencyResponse> addDependency(
            @PathVariable Long taskId,
            @Valid @RequestBody CreateTaskDependencyRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        request.setSuccessorTaskId(taskId);
        TaskDependencyResponse response = taskService.addDependency(request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Remove a task dependency")
    @DeleteMapping("/tasks/{taskId}/dependencies/{dependencyId}")
    public ResponseEntity<Void> removeDependency(
            @PathVariable Long taskId,
            @PathVariable Long dependencyId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        taskService.removeDependency(dependencyId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get task dependencies")
    @GetMapping("/tasks/{taskId}/dependencies")
    public ResponseEntity<List<TaskDependencyResponse>> getDependencies(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.getDependencies(taskId, actorId));
    }

    @Operation(summary = "Get blocking tasks for a task")
    @GetMapping("/tasks/{taskId}/blocking")
    public ResponseEntity<List<TaskResponse>> getBlockingTasks(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.getBlockingTasks(taskId, actorId));
    }

    @Operation(summary = "Get dependent tasks for a task")
    @GetMapping("/tasks/{taskId}/dependents")
    public ResponseEntity<List<TaskResponse>> getDependentTasks(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.getDependentTasks(taskId, actorId));
    }

    @Operation(summary = "Create a subtask")
    @PostMapping("/tasks/{taskId}/subtasks")
    public ResponseEntity<SubtaskResponse> createSubtask(
            @PathVariable Long taskId,
            @Valid @RequestBody CreateSubtaskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        SubtaskResponse response = taskService.createSubtask(taskId, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get direct subtasks of a task")
    @GetMapping("/tasks/{taskId}/subtasks")
    public ResponseEntity<List<SubtaskResponse>> getSubtasks(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.getSubtasks(taskId, actorId));
    }

    @Operation(summary = "Get task hierarchy tree")
    @GetMapping("/tasks/{taskId}/hierarchy")
    public ResponseEntity<TaskHierarchyResponse> getTaskHierarchy(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.getTaskHierarchy(taskId, actorId));
    }

    @Operation(summary = "Change parent task of a task")
    @PatchMapping("/tasks/{taskId}/parent")
    public ResponseEntity<SubtaskResponse> moveSubtask(
            @PathVariable Long taskId,
            @RequestBody MoveSubtaskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        SubtaskResponse response = taskService.moveSubtask(taskId, request.getParentTaskId(), actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove parent relationship from a task")
    @DeleteMapping("/tasks/{taskId}/parent")
    public ResponseEntity<Void> removeParent(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        taskService.removeParent(taskId, actorId);
        return ResponseEntity.noContent().build();
    }

    // Label Management
    @Operation(summary = "Create a project label")
    @PostMapping("/projects/{projectId}/labels")
    public ResponseEntity<LabelResponse> createLabel(
            @PathVariable Long projectId,
            @Valid @RequestBody CreateLabelRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        LabelResponse response = taskService.createLabel(projectId, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all labels of a project")
    @GetMapping("/projects/{projectId}/labels")
    public ResponseEntity<List<LabelResponse>> getLabels(
            @PathVariable Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.getLabels(projectId, actorId));
    }

    @Operation(summary = "Update a label")
    @PatchMapping("/labels/{labelId}")
    public ResponseEntity<LabelResponse> updateLabel(
            @PathVariable Long labelId,
            @Valid @RequestBody UpdateLabelRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.updateLabel(labelId, request, actorId));
    }

    @Operation(summary = "Delete a label")
    @DeleteMapping("/labels/{labelId}")
    public ResponseEntity<Void> deleteLabel(
            @PathVariable Long labelId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        taskService.deleteLabel(labelId, actorId);
        return ResponseEntity.noContent().build();
    }

    // Task Label Assignment
    @Operation(summary = "Assign a label to a task")
    @PostMapping("/tasks/{taskId}/labels/{labelId}")
    public ResponseEntity<Void> assignLabel(
            @PathVariable Long taskId,
            @PathVariable Long labelId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        taskService.assignLabel(taskId, labelId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove a label from a task")
    @DeleteMapping("/tasks/{taskId}/labels/{labelId}")
    public ResponseEntity<Void> removeLabel(
            @PathVariable Long taskId,
            @PathVariable Long labelId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        taskService.removeLabel(taskId, labelId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get labels assigned to a task")
    @GetMapping("/tasks/{taskId}/labels")
    public ResponseEntity<List<LabelResponse>> getTaskLabels(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.getTaskLabels(taskId, actorId));
    }

    @Operation(summary = "Get tasks containing a label")
    @GetMapping("/labels/{labelId}/tasks")
    public ResponseEntity<List<TaskResponse>> getTasksByLabel(
            @PathVariable Long labelId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(taskService.getTasksByLabel(labelId, actorId));
    }
}