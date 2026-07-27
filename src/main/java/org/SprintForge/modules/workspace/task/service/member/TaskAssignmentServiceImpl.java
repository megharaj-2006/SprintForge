package org.SprintForge.modules.workspace.task.service.member;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.project.entity.ProjectMember;
import org.SprintForge.modules.workspace.project.entity.ProjectRole;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectMemberStatus;
import org.SprintForge.modules.workspace.project.repository.ProjectMemberRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRoleRepository;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.task.dto.response.TaskAssignmentResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskAssigneeResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.TaskAssignment;
import org.SprintForge.modules.workspace.task.event.TaskAssignedEvent;
import org.SprintForge.modules.workspace.task.event.TaskReassignedEvent;
import org.SprintForge.modules.workspace.task.event.TaskUnassignedEvent;
import org.SprintForge.modules.workspace.task.mapper.TaskAssignmentMapper;
import org.SprintForge.modules.workspace.task.mapper.TaskMapper;
import org.SprintForge.modules.workspace.task.repository.TaskAssignmentRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskAssignmentServiceImpl implements TaskAssignmentService {

    private final TaskAssignmentRepository taskAssignmentRepository;
    private final TaskRepository taskRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRoleRepository projectRoleRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final UserRepository userRepository;
    private final ProjectPermissionService projectPermissionService;
    private final TaskAssignmentMapper taskAssignmentMapper;
    private final TaskMapper taskMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public TaskAssignmentResponse assignMember(Long taskId, Long projectMemberId, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        validateTaskMutable(task);

        ProjectMember member = getProjectMemberOrThrow(projectMemberId);
        validateProjectAlignment(task, member);

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "ASSIGN_TASK")) {
            throw new ForbiddenException("User does not have permission to assign tasks.");
        }

        if (taskAssignmentRepository.existsByTaskIdAndProjectMemberIdAndIsDeletedFalse(taskId, projectMemberId)) {
            throw new ConflictException("Member is already assigned to this task.");
        }

        TaskAssignment assignment = new TaskAssignment();
        assignment.setTask(task);
        assignment.setProjectMember(member);
        assignment.setAssignedBy(actorId);
        assignment.setAssignedAt(LocalDateTime.now());

        TaskAssignment saved = taskAssignmentRepository.save(assignment);

        eventPublisher.publishEvent(new TaskAssignedEvent(taskId, projectMemberId, actorId, LocalDateTime.now()));

        return taskAssignmentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public List<TaskAssignmentResponse> assignMembers(Long taskId, List<Long> projectMemberIds, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        validateTaskMutable(task);

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "ASSIGN_TASK")) {
            throw new ForbiddenException("User does not have permission to assign tasks.");
        }

        List<TaskAssignmentResponse> responses = new ArrayList<>();

        for (Long pmId : projectMemberIds) {
            ProjectMember member = getProjectMemberOrThrow(pmId);
            validateProjectAlignment(task, member);

            // Skip if already assigned (ignore duplicates per prompt instructions)
            if (taskAssignmentRepository.existsByTaskIdAndProjectMemberIdAndIsDeletedFalse(taskId, pmId)) {
                continue;
            }

            TaskAssignment assignment = new TaskAssignment();
            assignment.setTask(task);
            assignment.setProjectMember(member);
            assignment.setAssignedBy(actorId);
            assignment.setAssignedAt(LocalDateTime.now());

            TaskAssignment saved = taskAssignmentRepository.save(assignment);
            eventPublisher.publishEvent(new TaskAssignedEvent(taskId, pmId, actorId, LocalDateTime.now()));

            responses.add(taskAssignmentMapper.toResponse(saved));
        }

        return responses;
    }

    @Override
    @Transactional
    public void unassignMember(Long taskId, Long projectMemberId, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        validateTaskMutable(task);

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "ASSIGN_TASK")) {
            throw new ForbiddenException("User does not have permission to unassign tasks.");
        }

        TaskAssignment assignment = taskAssignmentRepository.findByTaskIdAndProjectMemberIdAndIsDeletedFalse(taskId, projectMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Task assignment not found for member: " + projectMemberId));

        assignment.markDeleted(actorId.toString());
        taskAssignmentRepository.save(assignment);

        eventPublisher.publishEvent(new TaskUnassignedEvent(taskId, projectMemberId, actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public void unassignAllMembers(Long taskId, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        validateTaskMutable(task);

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "ASSIGN_TASK")) {
            throw new ForbiddenException("User does not have permission to unassign tasks.");
        }

        List<TaskAssignment> assignments = taskAssignmentRepository.findByTaskIdAndIsDeletedFalse(taskId);
        for (TaskAssignment assignment : assignments) {
            assignment.markDeleted(actorId.toString());
            taskAssignmentRepository.save(assignment);
            eventPublisher.publishEvent(new TaskUnassignedEvent(taskId, assignment.getProjectMember().getId(), actorId, LocalDateTime.now()));
        }
    }

    @Override
    @Transactional
    public List<TaskAssignmentResponse> reassignTask(Long taskId, List<Long> projectMemberIds, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        validateTaskMutable(task);

        if (!projectPermissionService.hasPermission(task.getProject().getId(), actorId, "ASSIGN_TASK")) {
            throw new ForbiddenException("User does not have permission to reassign tasks.");
        }

        // 1. Unassign all current members
        List<TaskAssignment> currentAssignments = taskAssignmentRepository.findByTaskIdAndIsDeletedFalse(taskId);
        for (TaskAssignment assignment : currentAssignments) {
            assignment.markDeleted(actorId.toString());
            taskAssignmentRepository.save(assignment);
            eventPublisher.publishEvent(new TaskUnassignedEvent(taskId, assignment.getProjectMember().getId(), actorId, LocalDateTime.now()));
        }

        // 2. Assign new members
        List<TaskAssignmentResponse> responses = new ArrayList<>();
        for (Long pmId : projectMemberIds) {
            ProjectMember member = getProjectMemberOrThrow(pmId);
            validateProjectAlignment(task, member);

            TaskAssignment assignment = new TaskAssignment();
            assignment.setTask(task);
            assignment.setProjectMember(member);
            assignment.setAssignedBy(actorId);
            assignment.setAssignedAt(LocalDateTime.now());

            TaskAssignment saved = taskAssignmentRepository.save(assignment);
            eventPublisher.publishEvent(new TaskAssignedEvent(taskId, pmId, actorId, LocalDateTime.now()));

            responses.add(taskAssignmentMapper.toResponse(saved));
        }

        // 3. Publish reassigned summary event
        eventPublisher.publishEvent(new TaskReassignedEvent(taskId, projectMemberIds, actorId, LocalDateTime.now()));

        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskAssigneeResponse> getAssignees(Long taskId, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        validateViewPermission(task.getProject().getId(), actorId);

        List<TaskAssignment> assignments = taskAssignmentRepository.findByTaskIdAndIsDeletedFalse(taskId);
        List<TaskAssigneeResponse> responses = new ArrayList<>();

        for (TaskAssignment ta : assignments) {
            ProjectMember pm = ta.getProjectMember();
            if (pm.isDeleted()) {
                continue;
            }

            WorkspaceMember wm = workspaceMemberRepository.findById(pm.getWorkspaceMemberId())
                    .orElseThrow(() -> new ResourceNotFoundException("Workspace member not found for ID: " + pm.getWorkspaceMemberId()));

            User user = userRepository.findById(wm.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found for ID: " + wm.getUserId()));

            String roleName = "Member";
            if (pm.getRoleId() != null) {
                ProjectRole role = projectRoleRepository.findById(pm.getRoleId()).orElse(null);
                if (role != null) {
                    roleName = role.getName();
                }
            }

            responses.add(TaskAssigneeResponse.builder()
                    .projectMemberId(pm.getId())
                    .userId(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .roleName(roleName)
                    .build());
        }

        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getAssignedTasks(Long projectMemberId, Long actorId) {
        ProjectMember member = getProjectMemberOrThrow(projectMemberId);
        validateViewPermission(member.getProjectId(), actorId);

        List<TaskAssignment> assignments = taskAssignmentRepository.findByProjectMemberIdAndIsDeletedFalse(projectMemberId);
        return assignments.stream()
                .map(TaskAssignment::getTask)
                .filter(t -> !t.isDeleted() && !Boolean.TRUE.equals(t.getArchived()))
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getUnassignedTasks(Long projectId, Long actorId) {
        validateViewPermission(projectId, actorId);
        List<Task> unassignedTasks = taskRepository.findUnassignedTasks(projectId);
        return taskMapper.toResponseList(unassignedTasks);
    }

    @Override
    @Transactional(readOnly = true)
    public long countAssignments(Long taskId, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        validateViewPermission(task.getProject().getId(), actorId);
        return taskAssignmentRepository.countByTaskIdAndIsDeletedFalse(taskId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isAssigned(Long taskId, Long projectMemberId) {
        return taskAssignmentRepository.existsByTaskIdAndProjectMemberIdAndIsDeletedFalse(taskId, projectMemberId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTaskAssigned(Long taskId) {
        return taskAssignmentRepository.countByTaskIdAndIsDeletedFalse(taskId) > 0;
    }

    private Task getTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
    }

    private ProjectMember getProjectMemberOrThrow(Long id) {
        ProjectMember member = projectMemberRepository.findById(id)
                .filter(m -> !m.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project member not found with ID: " + id));

        if (member.getStatus() != ProjectMemberStatus.ACTIVE) {
            throw new BusinessRuleException("Project member is not active.");
        }
        return member;
    }

    private void validateTaskMutable(Task task) {
        if (Boolean.TRUE.equals(task.getArchived())) {
            throw new BusinessRuleException("Archived tasks cannot receive assignments.");
        }
    }

    private void validateProjectAlignment(Task task, ProjectMember member) {
        if (!member.getProjectId().equals(task.getProject().getId())) {
            throw new BusinessRuleException("Task and ProjectMember must belong to the same project.");
        }
    }

    private void validateViewPermission(Long projectId, Long actorId) {
        if (!projectPermissionService.hasPermission(projectId, actorId, "PROJECT_VIEW")) {
            throw new ForbiddenException("User does not have permission to view project tasks.");
        }
    }
}
