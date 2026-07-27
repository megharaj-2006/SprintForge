package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.request.CreateTaskDependencyRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateSubtaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.DuplicateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.*;
import org.SprintForge.modules.workspace.task.entity.enums.TaskPriority;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.entity.enums.TaskType;
import org.SprintForge.modules.workspace.task.service.management.TaskLifecycleService;
import org.SprintForge.modules.workspace.task.service.management.TaskWorkflowService;
import org.SprintForge.modules.workspace.task.service.query.TaskQueryService;
import org.SprintForge.modules.workspace.task.service.member.TaskAssignmentService;
import org.SprintForge.modules.workspace.task.service.TaskLabelService;
import org.SprintForge.modules.workspace.task.service.relation.TaskDependencyService;
import org.SprintForge.modules.workspace.task.service.relation.TaskHierarchyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskLifecycleService taskLifecycleService;
    private final TaskQueryService taskQueryService;
    private final TaskWorkflowService taskWorkflowService;
    private final TaskAssignmentService taskAssignmentService;
    private final TaskDependencyService taskDependencyService;
    private final TaskHierarchyService taskHierarchyService;
    private final TaskLabelService taskLabelService;
    private final LabelManagementService labelManagementService;

    @Override
    public TaskResponse createTask(CreateTaskRequest request, Long actorId) {
        return taskLifecycleService.createTask(request, actorId);
    }

    @Override
    public TaskResponse updateTask(Long id, UpdateTaskRequest request, Long actorId) {
        return taskLifecycleService.updateTask(id, request, actorId);
    }

    @Override
    public void deleteTask(Long id, Long actorId) {
        taskLifecycleService.deleteTask(id, actorId);
    }

    @Override
    public TaskResponse archiveTask(Long id, Long actorId) {
        return taskLifecycleService.archiveTask(id, actorId);
    }

    @Override
    public TaskResponse restoreTask(Long id, Long actorId) {
        return taskLifecycleService.restoreTask(id, actorId);
    }

    @Override
    public TaskResponse duplicateTask(Long id, DuplicateTaskRequest request, Long actorId) {
        return taskLifecycleService.duplicateTask(id, request, actorId);
    }

    @Override
    public TaskResponse moveTaskToSprint(Long id, Long sprintId, Long actorId) {
        return taskLifecycleService.moveTaskToSprint(id, sprintId, actorId);
    }

    @Override
    public TaskResponse removeFromSprint(Long id, Long actorId) {
        return taskLifecycleService.removeFromSprint(id, actorId);
    }

    @Override
    public TaskResponse getTask(Long id, Long actorId) {
        return taskQueryService.getTask(id, actorId);
    }

    @Override
    public List<TaskResponse> getTasks(Long projectId, Long actorId) {
        return taskQueryService.getTasks(projectId, actorId);
    }

    @Override
    public List<TaskResponse> getBacklog(Long projectId, Long actorId) {
        return taskQueryService.getBacklog(projectId, actorId);
    }

    @Override
    public List<TaskResponse> searchTasks(Long projectId, String query, Long actorId) {
        return taskQueryService.searchTasks(projectId, query, actorId);
    }

    @Override
    public List<TaskResponse> getArchivedTasks(Long projectId, Long actorId) {
        return taskQueryService.getArchivedTasks(projectId, actorId);
    }

    @Override
    public TaskStatisticsResponse getTaskStatistics(Long projectId, Long actorId) {
        return taskQueryService.getTaskStatistics(projectId, actorId);
    }

    // Assignment Operations
    @Override
    public TaskAssignmentResponse assignMember(Long taskId, Long projectMemberId, Long actorId) {
        return taskAssignmentService.assignMember(taskId, projectMemberId, actorId);
    }

    @Override
    public List<TaskAssignmentResponse> assignMembers(Long taskId, List<Long> projectMemberIds, Long actorId) {
        return taskAssignmentService.assignMembers(taskId, projectMemberIds, actorId);
    }

    @Override
    public void unassignMember(Long taskId, Long projectMemberId, Long actorId) {
        taskAssignmentService.unassignMember(taskId, projectMemberId, actorId);
    }

    @Override
    public void unassignAllMembers(Long taskId, Long actorId) {
        taskAssignmentService.unassignAllMembers(taskId, actorId);
    }

    @Override
    public List<TaskAssignmentResponse> reassignTask(Long taskId, List<Long> projectMemberIds, Long actorId) {
        return taskAssignmentService.reassignTask(taskId, projectMemberIds, actorId);
    }

    @Override
    public List<TaskAssigneeResponse> getAssignees(Long taskId, Long actorId) {
        return taskAssignmentService.getAssignees(taskId, actorId);
    }

    @Override
    public List<TaskResponse> getAssignedTasks(Long projectMemberId, Long actorId) {
        return taskAssignmentService.getAssignedTasks(projectMemberId, actorId);
    }

    @Override
    public List<TaskResponse> getUnassignedTasks(Long projectId, Long actorId) {
        return taskAssignmentService.getUnassignedTasks(projectId, actorId);
    }

    @Override
    public long countAssignments(Long taskId, Long actorId) {
        return taskAssignmentService.countAssignments(taskId, actorId);
    }

    @Override
    public boolean isAssigned(Long taskId, Long projectMemberId) {
        return taskAssignmentService.isAssigned(taskId, projectMemberId);
    }

    @Override
    public boolean isTaskAssigned(Long taskId) {
        return taskAssignmentService.isTaskAssigned(taskId);
    }

    // Workflow Actions
    @Override
    public TaskResponse changeStatus(Long id, TaskStatus status, Long actorId) {
        return taskWorkflowService.changeStatus(id, status, actorId);
    }

    @Override
    public TaskResponse changePriority(Long id, TaskPriority priority, Long actorId) {
        return taskWorkflowService.changePriority(id, priority, actorId);
    }

    @Override
    public TaskResponse changeType(Long id, TaskType type, Long actorId) {
        return taskWorkflowService.changeType(id, type, actorId);
    }

    @Override
    public boolean validateTransition(TaskStatus current, TaskStatus target) {
        return taskWorkflowService.validateTransition(current, target);
    }

    @Override
    public List<TaskStatus> getAllowedTransitions(Long id, Long actorId) {
        return taskWorkflowService.getAllowedTransitions(id, actorId);
    }

    @Override
    public TaskResponse startTask(Long id, Long actorId) {
        return taskWorkflowService.startTask(id, actorId);
    }

    @Override
    public TaskResponse sendForReview(Long id, Long actorId) {
        return taskWorkflowService.sendForReview(id, actorId);
    }

    @Override
    public TaskResponse completeTask(Long id, Long actorId) {
        return taskWorkflowService.completeTask(id, actorId);
    }

    @Override
    public TaskResponse cancelTask(Long id, Long actorId) {
        return taskWorkflowService.cancelTask(id, actorId);
    }

    @Override
    public TaskResponse reopenTask(Long id, TaskStatus targetStatus, Long actorId) {
        return taskWorkflowService.reopenTask(id, targetStatus, actorId);
    }

    @Override
    public List<TaskResponse> getTasksByStatus(Long projectId, TaskStatus status, Long actorId) {
        return taskWorkflowService.getTasksByStatus(projectId, status, actorId);
    }

    @Override
    public long countTasksByStatus(Long projectId, TaskStatus status, Long actorId) {
        return taskWorkflowService.countTasksByStatus(projectId, status, actorId);
    }

    // Dependencies
    @Override
    public TaskDependencyResponse addDependency(CreateTaskDependencyRequest request, Long actorId) {
        return taskDependencyService.addDependency(request, actorId);
    }

    @Override
    public void removeDependency(Long dependencyId, Long actorId) {
        taskDependencyService.removeDependency(dependencyId, actorId);
    }

    @Override
    public List<TaskDependencyResponse> getDependencies(Long taskId, Long actorId) {
        return taskDependencyService.getDependencies(taskId, actorId);
    }

    @Override
    public List<TaskResponse> getBlockingTasks(Long taskId, Long actorId) {
        return taskDependencyService.getBlockingTasks(taskId, actorId);
    }

    @Override
    public List<TaskResponse> getDependentTasks(Long taskId, Long actorId) {
        return taskDependencyService.getDependentTasks(taskId, actorId);
    }

    @Override
    public boolean hasDependencies(Long taskId) {
        return taskDependencyService.hasDependencies(taskId);
    }

    @Override
    public void validateDependencies(Long taskId) {
        taskDependencyService.validateDependencies(taskId);
    }

    @Override
    public boolean canStartTask(Long taskId) {
        return taskDependencyService.canStartTask(taskId);
    }

    @Override
    public boolean hasBlockingDependencies(Long taskId) {
        return taskDependencyService.hasBlockingDependencies(taskId);
    }

    @Override
    public List<TaskDependencyResponse> getDependencyGraph(Long taskId, Long actorId) {
        return taskDependencyService.getDependencyGraph(taskId, actorId);
    }

    @Override
    public long countDependencies(Long taskId) {
        return taskDependencyService.countDependencies(taskId);
    }

    // Subtasks / Hierarchy
    @Override
    public SubtaskResponse createSubtask(Long parentTaskId, CreateSubtaskRequest request, Long actorId) {
        return taskHierarchyService.createSubtask(parentTaskId, request, actorId);
    }

    @Override
    public SubtaskResponse moveSubtask(Long taskId, Long parentTaskId, Long actorId) {
        return taskHierarchyService.moveSubtask(taskId, parentTaskId, actorId);
    }

    @Override
    public void removeParent(Long taskId, Long actorId) {
        taskHierarchyService.removeParent(taskId, actorId);
    }

    @Override
    public TaskResponse getParentTask(Long taskId, Long actorId) {
        return taskHierarchyService.getParentTask(taskId, actorId);
    }

    @Override
    public List<SubtaskResponse> getSubtasks(Long taskId, Long actorId) {
        return taskHierarchyService.getSubtasks(taskId, actorId);
    }

    @Override
    public List<TaskResponse> getRootTasks(Long projectId, Long actorId) {
        return taskHierarchyService.getRootTasks(projectId, actorId);
    }

    @Override
    public TaskHierarchyResponse getTaskHierarchy(Long taskId, Long actorId) {
        return taskHierarchyService.getTaskHierarchy(taskId, actorId);
    }

    @Override
    public boolean hasChildren(Long taskId) {
        return taskHierarchyService.hasChildren(taskId);
    }

    @Override
    public long countSubtasks(Long taskId) {
        return taskHierarchyService.countSubtasks(taskId);
    }

    // Label Management
    @Override
    public LabelResponse createLabel(Long projectId, CreateLabelRequest request, Long actorId) {
        return labelManagementService.createLabel(projectId, request, actorId);
    }

    @Override
    public LabelResponse updateLabel(Long labelId, UpdateLabelRequest request, Long actorId) {
        return labelManagementService.updateLabel(labelId, request, actorId);
    }

    @Override
    public LabelResponse archiveLabel(Long labelId, Long actorId) {
        return labelManagementService.archiveLabel(labelId, actorId);
    }

    @Override
    public LabelResponse restoreLabel(Long labelId, Long actorId) {
        return labelManagementService.restoreLabel(labelId, actorId);
    }

    @Override
    public void deleteLabel(Long labelId, Long actorId) {
        labelManagementService.deleteLabel(labelId, actorId);
    }

    @Override
    public List<LabelResponse> getLabels(Long projectId, Long actorId) {
        return labelManagementService.getLabels(projectId, actorId);
    }

    @Override
    public List<LabelResponse> searchLabels(Long projectId, String query, Long actorId) {
        return labelManagementService.searchLabels(projectId, query, actorId);
    }

    // Task Label Assignment
    @Override
    public void assignLabel(Long taskId, Long labelId, Long actorId) {
        taskLabelService.assignLabel(taskId, labelId, actorId);
    }

    @Override
    public void assignLabels(Long taskId, List<Long> labelIds, Long actorId) {
        taskLabelService.assignLabels(taskId, labelIds, actorId);
    }

    @Override
    public void removeLabel(Long taskId, Long labelId, Long actorId) {
        taskLabelService.removeLabel(taskId, labelId, actorId);
    }

    @Override
    public void removeAllLabels(Long taskId, Long actorId) {
        taskLabelService.removeAllLabels(taskId, actorId);
    }

    @Override
    public List<LabelResponse> getTaskLabels(Long taskId, Long actorId) {
        return taskLabelService.getTaskLabels(taskId, actorId);
    }

    @Override
    public List<TaskResponse> getTasksByLabel(Long labelId, Long actorId) {
        return taskLabelService.getTasksByLabel(labelId, actorId);
    }

    @Override
    public boolean hasLabel(Long taskId, Long labelId) {
        return taskLabelService.hasLabel(taskId, labelId);
    }

    @Override
    public long countTasksUsingLabel(Long labelId) {
        return taskLabelService.countTasksUsingLabel(labelId);
    }
}