package org.SprintForge.modules.workspace.task.service;

import org.SprintForge.modules.workspace.task.dto.request.AssignLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateTaskDependencyRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateSubtaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.DuplicateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.RemoveLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskAssignmentResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskAssigneeResponse;
import org.SprintForge.modules.workspace.task.dto.request.CreateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskDependencyResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskHierarchyResponse;
import org.SprintForge.modules.workspace.task.dto.response.LabelResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskLabelResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskStatisticsResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.SubtaskResponse;
import org.SprintForge.modules.workspace.task.entity.enums.TaskPriority;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.entity.enums.TaskType;

import java.util.List;

public interface TaskService {

    // Lifecycle
    TaskResponse createTask(CreateTaskRequest request, Long actorId);

    TaskResponse updateTask(Long id, UpdateTaskRequest request, Long actorId);

    void deleteTask(Long id, Long actorId);

    TaskResponse archiveTask(Long id, Long actorId);

    TaskResponse restoreTask(Long id, Long actorId);

    TaskResponse duplicateTask(Long id, DuplicateTaskRequest request, Long actorId);

    TaskResponse moveTaskToSprint(Long id, Long sprintId, Long actorId);

    TaskResponse removeFromSprint(Long id, Long actorId);

    // Query
    TaskResponse getTask(Long id, Long actorId);

    List<TaskResponse> getTasks(Long projectId, Long actorId);

    List<TaskResponse> getBacklog(Long projectId, Long actorId);

    List<TaskResponse> searchTasks(Long projectId, String query, Long actorId);

    List<TaskResponse> getArchivedTasks(Long projectId, Long actorId);

    TaskStatisticsResponse getTaskStatistics(Long projectId, Long actorId);

    // Workflow
    TaskResponse changeStatus(Long id, TaskStatus status, Long actorId);

    TaskResponse changePriority(Long id, TaskPriority priority, Long actorId);

    TaskResponse changeType(Long id, TaskType type, Long actorId);

    // Assignment Operations
    TaskAssignmentResponse assignMember(Long taskId, Long projectMemberId, Long actorId);

    List<TaskAssignmentResponse> assignMembers(Long taskId, List<Long> projectMemberIds, Long actorId);

    void unassignMember(Long taskId, Long projectMemberId, Long actorId);

    void unassignAllMembers(Long taskId, Long actorId);

    List<TaskAssignmentResponse> reassignTask(Long taskId, List<Long> projectMemberIds, Long actorId);

    List<TaskAssigneeResponse> getAssignees(Long taskId, Long actorId);

    List<TaskResponse> getAssignedTasks(Long projectMemberId, Long actorId);

    List<TaskResponse> getUnassignedTasks(Long projectId, Long actorId);

    long countAssignments(Long taskId, Long actorId);

    boolean isAssigned(Long taskId, Long projectMemberId);

    boolean isTaskAssigned(Long taskId);

    // Workflow Actions
    boolean validateTransition(TaskStatus current, TaskStatus target);

    List<TaskStatus> getAllowedTransitions(Long id, Long actorId);

    TaskResponse startTask(Long id, Long actorId);

    TaskResponse sendForReview(Long id, Long actorId);

    TaskResponse completeTask(Long id, Long actorId);

    TaskResponse cancelTask(Long id, Long actorId);

    TaskResponse reopenTask(Long id, TaskStatus targetStatus, Long actorId);

    List<TaskResponse> getTasksByStatus(Long projectId, TaskStatus status, Long actorId);

    long countTasksByStatus(Long projectId, TaskStatus status, Long actorId);

    // Dependencies
    TaskDependencyResponse addDependency(CreateTaskDependencyRequest request, Long actorId);

    void removeDependency(Long dependencyId, Long actorId);

    List<TaskDependencyResponse> getDependencies(Long taskId, Long actorId);

    List<TaskResponse> getBlockingTasks(Long taskId, Long actorId);

    List<TaskResponse> getDependentTasks(Long taskId, Long actorId);

    boolean hasDependencies(Long taskId);

    void validateDependencies(Long taskId);

    boolean canStartTask(Long taskId);

    boolean hasBlockingDependencies(Long taskId);

    List<TaskDependencyResponse> getDependencyGraph(Long taskId, Long actorId);

    long countDependencies(Long taskId);

    // Subtasks / Hierarchy
    SubtaskResponse createSubtask(Long parentTaskId, CreateSubtaskRequest request, Long actorId);

    SubtaskResponse moveSubtask(Long taskId, Long parentTaskId, Long actorId);

    void removeParent(Long taskId, Long actorId);

    TaskResponse getParentTask(Long taskId, Long actorId);

    List<SubtaskResponse> getSubtasks(Long taskId, Long actorId);

    List<TaskResponse> getRootTasks(Long projectId, Long actorId);

    TaskHierarchyResponse getTaskHierarchy(Long taskId, Long actorId);

    boolean hasChildren(Long taskId);

    long countSubtasks(Long taskId);

    // Label Management
    LabelResponse createLabel(Long projectId, CreateLabelRequest request, Long actorId);

    LabelResponse updateLabel(Long labelId, UpdateLabelRequest request, Long actorId);

    LabelResponse archiveLabel(Long labelId, Long actorId);

    LabelResponse restoreLabel(Long labelId, Long actorId);

    void deleteLabel(Long labelId, Long actorId);

    List<LabelResponse> getLabels(Long projectId, Long actorId);

    List<LabelResponse> searchLabels(Long projectId, String query, Long actorId);

    // Task Label Assignment
    void assignLabel(Long taskId, Long labelId, Long actorId);

    void assignLabels(Long taskId, List<Long> labelIds, Long actorId);

    void removeLabel(Long taskId, Long labelId, Long actorId);

    void removeAllLabels(Long taskId, Long actorId);

    List<LabelResponse> getTaskLabels(Long taskId, Long actorId);

    List<TaskResponse> getTasksByLabel(Long labelId, Long actorId);

    boolean hasLabel(Long taskId, Long labelId);

    long countTasksUsingLabel(Long labelId);
}