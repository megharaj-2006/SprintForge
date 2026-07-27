package org.SprintForge.modules.workspace.task.service;

import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.entity.ProjectMember;
import org.SprintForge.modules.workspace.project.entity.ProjectRole;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectMemberStatus;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectStatusType;
import org.SprintForge.modules.workspace.project.repository.ProjectMemberRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRoleRepository;
import org.SprintForge.modules.workspace.project.service.member.ProjectMemberService;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.sprint.entity.Sprint;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;
import org.SprintForge.modules.workspace.sprint.repository.SprintRepository;
import org.SprintForge.modules.workspace.task.dto.request.CreateTaskDependencyRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateSubtaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.DuplicateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.LabelResponse;
import org.SprintForge.modules.workspace.task.dto.response.SubtaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskAssignmentResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskAssigneeResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskDependencyResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskStatisticsResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.TaskAssignment;
import org.SprintForge.modules.workspace.task.entity.TaskDependency;
import org.SprintForge.modules.workspace.task.entity.enums.TaskDependencyType;
import org.SprintForge.modules.workspace.task.entity.enums.TaskPriority;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.entity.enums.TaskType;
import org.SprintForge.modules.workspace.task.event.*;
import org.SprintForge.modules.workspace.task.mapper.TaskAssignmentMapper;
import org.SprintForge.modules.workspace.task.entity.Label;
import org.SprintForge.modules.workspace.task.mapper.LabelMapper;
import org.SprintForge.modules.workspace.task.mapper.TaskDependencyMapper;
import org.SprintForge.modules.workspace.task.mapper.TaskMapper;
import org.SprintForge.modules.workspace.task.repository.LabelRepository;
import org.SprintForge.modules.workspace.task.repository.TaskAssignmentRepository;
import org.SprintForge.modules.workspace.task.repository.TaskDependencyRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.SprintForge.modules.workspace.task.service.LabelManagementServiceImpl;
import org.SprintForge.modules.workspace.task.service.TaskLabelServiceImpl;
import org.SprintForge.modules.workspace.task.service.management.TaskLifecycleServiceImpl;
import org.SprintForge.modules.workspace.task.service.management.TaskWorkflowServiceImpl;
import org.SprintForge.modules.workspace.task.service.query.TaskQueryServiceImpl;
import org.SprintForge.modules.workspace.task.service.member.TaskAssignmentServiceImpl;
import org.SprintForge.modules.workspace.task.service.relation.TaskDependencyServiceImpl;
import org.SprintForge.modules.workspace.task.service.relation.TaskHierarchyServiceImpl;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private SprintRepository sprintRepository;

    @Mock
    private ProjectMemberService projectMemberService;

    @Mock
    private ProjectPermissionService projectPermissionService;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    // Assignment mocks
    @Mock
    private TaskAssignmentRepository taskAssignmentRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Mock
    private ProjectRoleRepository projectRoleRepository;

    @Mock
    private WorkspaceMemberRepository workspaceMemberRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskAssignmentMapper taskAssignmentMapper;

    // Dependency mocks
    @Mock
    private TaskDependencyRepository taskDependencyRepository;

    @Mock
    private TaskDependencyMapper taskDependencyMapper;

    @Mock
    private LabelRepository labelRepository;

    @Mock
    private LabelMapper labelMapper;

    private TaskLifecycleServiceImpl taskLifecycleService;
    private TaskQueryServiceImpl taskQueryService;
    private TaskWorkflowServiceImpl taskWorkflowService;
    private TaskAssignmentServiceImpl taskAssignmentService;
    private TaskDependencyServiceImpl taskDependencyService;
    private TaskHierarchyServiceImpl taskHierarchyService;
    private LabelManagementServiceImpl labelManagementService;
    private TaskLabelServiceImpl taskLabelService;

    private Project project;
    private Sprint sprint;
    private Task task;
    private ProjectMember member;

    @BeforeEach
    void setUp() {
        taskLifecycleService = new TaskLifecycleServiceImpl(
                taskRepository,
                projectRepository,
                sprintRepository,
                projectMemberService,
                projectPermissionService,
                taskMapper,
                eventPublisher
        );

        taskQueryService = new TaskQueryServiceImpl(
                taskRepository,
                projectRepository,
                projectMemberService,
                projectPermissionService,
                taskMapper
        );

        taskDependencyService = new TaskDependencyServiceImpl(
                taskDependencyRepository,
                taskRepository,
                projectPermissionService,
                taskDependencyMapper,
                taskMapper,
                eventPublisher
        );

        taskWorkflowService = new TaskWorkflowServiceImpl(
                taskRepository,
                projectPermissionService,
                taskMapper,
                eventPublisher,
                taskDependencyService
        );

        taskAssignmentService = new TaskAssignmentServiceImpl(
                taskAssignmentRepository,
                taskRepository,
                projectMemberRepository,
                projectRoleRepository,
                workspaceMemberRepository,
                userRepository,
                projectPermissionService,
                taskAssignmentMapper,
                taskMapper,
                eventPublisher
        );

        taskHierarchyService = new TaskHierarchyServiceImpl(
                taskRepository,
                projectPermissionService,
                taskMapper,
                eventPublisher
        );

        labelManagementService = new LabelManagementServiceImpl(
                labelRepository,
                projectRepository,
                taskRepository,
                projectPermissionService,
                labelMapper,
                eventPublisher
        );

        taskLabelService = new TaskLabelServiceImpl(
                taskRepository,
                labelRepository,
                projectPermissionService,
                labelMapper,
                taskMapper,
                eventPublisher
        );

        project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        project.setProjectKey("TEST");
        project.setStatus(ProjectStatusType.ACTIVE);
        project.setIsArchived(false);

        sprint = new Sprint();
        sprint.setId(2L);
        sprint.setProjectId(1L);
        sprint.setName("Sprint 1");
        sprint.setStatus(SprintStatus.ACTIVE);

        task = new Task();
        task.setId(3L);
        task.setProject(project);
        task.setSprint(sprint);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setIdentifier("TEST-1");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.MEDIUM);
        task.setType(TaskType.TASK);
        task.setArchived(false);

        member = new ProjectMember();
        member.setId(4L);
        member.setProjectId(1L);
        member.setStatus(ProjectMemberStatus.ACTIVE);
        member.setWorkspaceMemberId(5L);
    }

    @Test
    void createTask_shouldSucceed() {
        CreateTaskRequest request = CreateTaskRequest.builder()
                .projectId(1L)
                .sprintId(2L)
                .title("New Task")
                .description("Desc")
                .type(TaskType.TASK)
                .priority(TaskPriority.HIGH)
                .status(TaskStatus.TODO)
                .build();

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMemberService.isProjectMember(1L, 100L)).thenReturn(true);
        when(projectPermissionService.hasPermission(1L, 100L, "CREATE_TASK")).thenReturn(true);
        when(sprintRepository.findById(2L)).thenReturn(Optional.of(sprint));
        when(taskRepository.countByProjectId(1L)).thenReturn(0L);
        when(taskRepository.existsByIdentifier("TEST-1")).thenReturn(false);

        Task mappedTask = new Task();
        mappedTask.setTitle("New Task");
        mappedTask.setDescription("Desc");
        mappedTask.setType(TaskType.TASK);
        mappedTask.setPriority(TaskPriority.HIGH);
        mappedTask.setStatus(TaskStatus.TODO);

        when(taskMapper.toEntity(request)).thenReturn(mappedTask);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse expectedResponse = TaskResponse.builder()
                .id(3L)
                .projectId(1L)
                .sprintId(2L)
                .title("Test Task")
                .identifier("TEST-1")
                .status(TaskStatus.TODO)
                .priority(TaskPriority.MEDIUM)
                .type(TaskType.TASK)
                .build();
        when(taskMapper.toResponse(any(Task.class))).thenReturn(expectedResponse);

        TaskResponse response = taskLifecycleService.createTask(request, 100L);

        assertNotNull(response);
        assertEquals(3L, response.getId());
        assertEquals("TEST-1", response.getIdentifier());
        verify(eventPublisher, times(1)).publishEvent(any(TaskCreatedEvent.class));
    }

    @Test
    void createTask_projectArchived_shouldThrowBusinessRuleException() {
        project.setIsArchived(true);
        CreateTaskRequest request = CreateTaskRequest.builder()
                .projectId(1L)
                .title("New Task")
                .build();

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        assertThrows(BusinessRuleException.class, () -> taskLifecycleService.createTask(request, 100L));
    }

    @Test
    void createTask_notProjectMember_shouldThrowForbiddenException() {
        CreateTaskRequest request = CreateTaskRequest.builder()
                .projectId(1L)
                .title("New Task")
                .build();

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMemberService.isProjectMember(1L, 100L)).thenReturn(false);

        assertThrows(ForbiddenException.class, () -> taskLifecycleService.createTask(request, 100L));
    }

    @Test
    void updateTask_shouldSucceed() {
        UpdateTaskRequest request = UpdateTaskRequest.builder()
                .title("Updated Title")
                .build();

        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "TASK_MANAGE")).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        taskLifecycleService.updateTask(3L, request, 100L);

        verify(taskMapper, times(1)).updateEntity(request, task);
        verify(eventPublisher, times(1)).publishEvent(any(TaskUpdatedEvent.class));
    }

    @Test
    void updateTask_archivedTask_shouldThrowBusinessRuleException() {
        task.setArchived(true);
        UpdateTaskRequest request = UpdateTaskRequest.builder().title("Updated").build();

        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));

        assertThrows(BusinessRuleException.class, () -> taskLifecycleService.updateTask(3L, request, 100L));
    }

    @Test
    void moveTaskToSprint_completedSprint_shouldThrowBusinessRuleException() {
        sprint.setStatus(SprintStatus.COMPLETED);
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "TASK_MANAGE")).thenReturn(true);
        when(sprintRepository.findById(2L)).thenReturn(Optional.of(sprint));

        assertThrows(BusinessRuleException.class, () -> taskLifecycleService.moveTaskToSprint(3L, 2L, 100L));
    }

    @Test
    void changeStatus_shouldSucceed() {
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "UPDATE_TASK")).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        taskWorkflowService.changeStatus(3L, TaskStatus.IN_PROGRESS, 100L);

        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
        verify(eventPublisher, times(1)).publishEvent(any(TaskStatusChangedEvent.class));
    }

    @Test
    void changeStatus_invalidTransition_shouldThrowBusinessRuleException() {
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "UPDATE_TASK")).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> taskWorkflowService.changeStatus(3L, TaskStatus.DONE, 100L));
    }

    @Test
    void startTask_shouldSucceed() {
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "UPDATE_TASK")).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        taskWorkflowService.startTask(3L, 100L);

        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
        verify(eventPublisher, times(1)).publishEvent(any(TaskStartedEvent.class));
    }

    @Test
    void sendForReview_shouldSucceed() {
        task.setStatus(TaskStatus.IN_PROGRESS);
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "UPDATE_TASK")).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        taskWorkflowService.sendForReview(3L, 100L);

        assertEquals(TaskStatus.IN_REVIEW, task.getStatus());
        verify(eventPublisher, times(1)).publishEvent(any(TaskReviewRequestedEvent.class));
    }

    @Test
    void completeTask_shouldSucceed() {
        task.setStatus(TaskStatus.IN_REVIEW);
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "UPDATE_TASK")).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        taskWorkflowService.completeTask(3L, 100L);

        assertEquals(TaskStatus.DONE, task.getStatus());
        verify(eventPublisher, times(1)).publishEvent(any(TaskCompletedEvent.class));
    }

    @Test
    void cancelTask_shouldSucceed() {
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "UPDATE_TASK")).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        taskWorkflowService.cancelTask(3L, 100L);

        assertEquals(TaskStatus.CANCELLED, task.getStatus());
        verify(eventPublisher, times(1)).publishEvent(any(TaskCancelledEvent.class));
    }

    @Test
    void reopenTask_doneToInProgress_shouldSucceed() {
        task.setStatus(TaskStatus.DONE);
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "UPDATE_TASK")).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        taskWorkflowService.reopenTask(3L, TaskStatus.IN_PROGRESS, 100L);

        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
        verify(eventPublisher, times(1)).publishEvent(any(TaskReopenedEvent.class));
    }

    @Test
    void assignMember_shouldSucceed() {
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectMemberRepository.findById(4L)).thenReturn(Optional.of(member));
        when(projectPermissionService.hasPermission(1L, 100L, "ASSIGN_TASK")).thenReturn(true);
        when(taskAssignmentRepository.existsByTaskIdAndProjectMemberIdAndIsDeletedFalse(3L, 4L)).thenReturn(false);

        TaskAssignment assignment = new TaskAssignment();
        assignment.setId(10L);
        assignment.setTask(task);
        assignment.setProjectMember(member);
        assignment.setAssignedBy(100L);

        when(taskAssignmentRepository.save(any(TaskAssignment.class))).thenReturn(assignment);
        when(taskAssignmentMapper.toResponse(any(TaskAssignment.class))).thenReturn(
                TaskAssignmentResponse.builder()
                        .id(10L)
                        .taskId(3L)
                        .projectMemberId(4L)
                        .assignedBy(100L)
                        .build()
        );

        TaskAssignmentResponse response = taskAssignmentService.assignMember(3L, 4L, 100L);

        assertNotNull(response);
        assertEquals(10L, response.getId());
        verify(eventPublisher, times(1)).publishEvent(any(TaskAssignedEvent.class));
    }

    @Test
    void assignMember_alreadyAssigned_shouldThrowConflictException() {
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectMemberRepository.findById(4L)).thenReturn(Optional.of(member));
        when(projectPermissionService.hasPermission(1L, 100L, "ASSIGN_TASK")).thenReturn(true);
        when(taskAssignmentRepository.existsByTaskIdAndProjectMemberIdAndIsDeletedFalse(3L, 4L)).thenReturn(true);

        assertThrows(ConflictException.class, () -> taskAssignmentService.assignMember(3L, 4L, 100L));
    }

    @Test
    void assignMember_archivedTask_shouldThrowBusinessRuleException() {
        task.setArchived(true);
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));

        assertThrows(BusinessRuleException.class, () -> taskAssignmentService.assignMember(3L, 4L, 100L));
    }

    @Test
    void unassignMember_shouldSucceed() {
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "ASSIGN_TASK")).thenReturn(true);

        TaskAssignment assignment = new TaskAssignment();
        assignment.setId(10L);
        assignment.setTask(task);
        assignment.setProjectMember(member);

        when(taskAssignmentRepository.findByTaskIdAndProjectMemberIdAndIsDeletedFalse(3L, 4L)).thenReturn(Optional.of(assignment));

        taskAssignmentService.unassignMember(3L, 4L, 100L);

        verify(taskAssignmentRepository, times(1)).save(assignment);
        assertTrue(assignment.isDeleted());
        verify(eventPublisher, times(1)).publishEvent(any(TaskUnassignedEvent.class));
    }

    @Test
    void reassignTask_shouldSucceed() {
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "ASSIGN_TASK")).thenReturn(true);

        TaskAssignment oldAssignment = new TaskAssignment();
        oldAssignment.setId(10L);
        oldAssignment.setTask(task);
        oldAssignment.setProjectMember(member);

        when(taskAssignmentRepository.findByTaskIdAndIsDeletedFalse(3L)).thenReturn(List.of(oldAssignment));
        when(projectMemberRepository.findById(4L)).thenReturn(Optional.of(member));

        TaskAssignment newAssignment = new TaskAssignment();
        newAssignment.setTask(task);
        newAssignment.setProjectMember(member);

        when(taskAssignmentRepository.save(any(TaskAssignment.class))).thenReturn(newAssignment);

        taskAssignmentService.reassignTask(3L, List.of(4L), 100L);

        verify(taskAssignmentRepository, times(1)).save(oldAssignment);
        assertTrue(oldAssignment.isDeleted());
        verify(eventPublisher, times(1)).publishEvent(any(TaskReassignedEvent.class));
    }

    @Test
    void addDependency_shouldSucceed() {
        Task predecessor = new Task();
        predecessor.setId(11L);
        predecessor.setProject(project);

        when(taskRepository.findById(11L)).thenReturn(Optional.of(predecessor));
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "UPDATE_TASK")).thenReturn(true);
        when(taskDependencyRepository.existsByPredecessorTaskIdAndSuccessorTaskIdAndIsDeletedFalse(11L, 3L)).thenReturn(false);

        TaskDependency dependency = new TaskDependency();
        dependency.setId(20L);
        dependency.setPredecessorTask(predecessor);
        dependency.setSuccessorTask(task);
        dependency.setType(TaskDependencyType.FINISH_TO_START);

        when(taskDependencyRepository.save(any(TaskDependency.class))).thenReturn(dependency);
        when(taskDependencyMapper.toResponse(any(TaskDependency.class))).thenReturn(
                TaskDependencyResponse.builder()
                        .id(20L)
                        .predecessorTaskId(11L)
                        .successorTaskId(3L)
                        .type(TaskDependencyType.FINISH_TO_START)
                        .build()
        );

        CreateTaskDependencyRequest req = CreateTaskDependencyRequest.builder()
                .predecessorTaskId(11L)
                .successorTaskId(3L)
                .type(TaskDependencyType.FINISH_TO_START)
                .build();

        TaskDependencyResponse response = taskDependencyService.addDependency(req, 100L);

        assertNotNull(response);
        assertEquals(20L, response.getId());
        verify(eventPublisher, times(1)).publishEvent(any(TaskDependencyCreatedEvent.class));
    }

    @Test
    void addDependency_circularDependency_shouldThrowBusinessRuleException() {
        Task predecessor = new Task();
        predecessor.setId(11L);
        predecessor.setProject(project);

        when(taskRepository.findById(11L)).thenReturn(Optional.of(predecessor));
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "UPDATE_TASK")).thenReturn(true);

        // Successor (3) already has path to Predecessor (11)
        TaskDependency existing = new TaskDependency();
        existing.setPredecessorTask(task);
        existing.setSuccessorTask(predecessor);
        when(taskDependencyRepository.findByPredecessorTaskIdAndIsDeletedFalse(3L)).thenReturn(List.of(existing));

        CreateTaskDependencyRequest req = CreateTaskDependencyRequest.builder()
                .predecessorTaskId(11L)
                .successorTaskId(3L)
                .type(TaskDependencyType.FINISH_TO_START)
                .build();

        assertThrows(BusinessRuleException.class, () -> taskDependencyService.addDependency(req, 100L));
    }

    @Test
    void startTask_finishToStartDependencyUnfinished_shouldThrowBusinessRuleException() {
        Task predecessor = new Task();
        predecessor.setId(11L);
        predecessor.setStatus(TaskStatus.TODO);

        TaskDependency dependency = new TaskDependency();
        dependency.setPredecessorTask(predecessor);
        dependency.setSuccessorTask(task);
        dependency.setType(TaskDependencyType.FINISH_TO_START);

        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "UPDATE_TASK")).thenReturn(true);
        when(taskDependencyRepository.findBySuccessorTaskIdAndIsDeletedFalse(3L)).thenReturn(List.of(dependency));

        assertThrows(BusinessRuleException.class, () -> taskWorkflowService.startTask(3L, 100L));
    }

    @Test
    void createSubtask_shouldSucceed() {
        CreateSubtaskRequest request = CreateSubtaskRequest.builder()
                .title("Subtask Title")
                .build();

        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "CREATE_TASK")).thenReturn(true);
        when(taskRepository.countByProjectId(1L)).thenReturn(1L);
        when(taskRepository.existsByIdentifierAndIsDeletedFalse("TEST-2")).thenReturn(false);

        Task child = new Task();
        child.setId(40L);
        child.setProject(project);
        child.setParentTask(task);
        child.setTitle("Subtask Title");
        child.setIdentifier("TEST-2");

        when(taskMapper.toEntity(request)).thenReturn(child);
        when(taskRepository.save(any(Task.class))).thenReturn(child);
        when(taskMapper.toSubtaskResponse(any(Task.class))).thenReturn(
                SubtaskResponse.builder()
                        .id(40L)
                        .parentTaskId(3L)
                        .projectId(1L)
                        .title("Subtask Title")
                        .identifier("TEST-2")
                        .build()
        );

        SubtaskResponse response = taskHierarchyService.createSubtask(3L, request, 100L);

        assertNotNull(response);
        assertEquals(40L, response.getId());
        assertEquals(3L, response.getParentTaskId());
        verify(eventPublisher, times(1)).publishEvent(any(SubtaskCreatedEvent.class));
    }

    @Test
    void moveSubtask_circularParenting_shouldThrowBusinessRuleException() {
        Task child = new Task();
        child.setId(40L);
        child.setProject(project);

        when(taskRepository.findById(40L)).thenReturn(Optional.of(child));
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "UPDATE_TASK")).thenReturn(true);

        // Child (40) is already a parent of Parent (3)
        task.setParentTask(child);

        assertThrows(BusinessRuleException.class, () -> taskHierarchyService.moveSubtask(40L, 3L, 100L));
    }

    @Test
    void completeParentTask_uncompletedSubtask_shouldThrowBusinessRuleException() {
        Task child = new Task();
        child.setId(40L);
        child.setStatus(TaskStatus.TODO);

        task.setStatus(TaskStatus.IN_REVIEW);

        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(projectPermissionService.hasPermission(1L, 100L, "UPDATE_TASK")).thenReturn(true);
        when(taskRepository.findByParentTaskIdAndIsDeletedFalse(3L)).thenReturn(List.of(child));

        assertThrows(BusinessRuleException.class, () -> taskWorkflowService.completeTask(3L, 100L));
    }

    @Test
    void createLabel_shouldSucceed() {
        CreateLabelRequest request = CreateLabelRequest.builder()
                .name("Bug")
                .color("#FF0000")
                .description("Defects found")
                .build();

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectPermissionService.hasPermission(1L, 100L, "MANAGE_LABELS")).thenReturn(true);
        when(labelRepository.existsByProjectIdAndNameAndIsDeletedFalse(1L, "Bug")).thenReturn(false);

        Label label = new Label();
        label.setId(10L);
        label.setProject(project);
        label.setName("Bug");
        label.setColor("#FF0000");
        label.setDescription("Defects found");

        when(labelMapper.toEntity(request)).thenReturn(label);
        when(labelRepository.save(any(Label.class))).thenReturn(label);
        when(labelMapper.toResponse(any(Label.class))).thenReturn(
                LabelResponse.builder()
                        .id(10L)
                        .projectId(1L)
                        .name("Bug")
                        .color("#FF0000")
                        .description("Defects found")
                        .build()
        );

        LabelResponse response = labelManagementService.createLabel(1L, request, 100L);

        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("Bug", response.getName());
        verify(eventPublisher, times(1)).publishEvent(any(LabelCreatedEvent.class));
    }

    @Test
    void assignLabel_shouldSucceed() {
        Label label = new Label();
        label.setId(10L);
        label.setProject(project);
        label.setName("Bug");
        label.setColor("#FF0000");

        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(labelRepository.findById(10L)).thenReturn(Optional.of(label));
        when(projectPermissionService.hasPermission(1L, 100L, "UPDATE_TASK")).thenReturn(true);

        taskLabelService.assignLabel(3L, 10L, 100L);

        assertTrue(task.getLabels().contains(label));
        verify(eventPublisher, times(1)).publishEvent(any(LabelAssignedEvent.class));
    }

    @Test
    void assignLabel_archivedLabel_shouldThrowBusinessRuleException() {
        Label label = new Label();
        label.setId(10L);
        label.setProject(project);
        label.setName("Bug");
        label.setArchived(true);

        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(labelRepository.findById(10L)).thenReturn(Optional.of(label));
        when(projectPermissionService.hasPermission(1L, 100L, "UPDATE_TASK")).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> taskLabelService.assignLabel(3L, 10L, 100L));
    }

    @Test
    void removeLabel_shouldSucceed() {
        Label label = new Label();
        label.setId(10L);
        label.setProject(project);
        label.setName("Bug");

        task.getLabels().add(label);

        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));
        when(labelRepository.findById(10L)).thenReturn(Optional.of(label));
        when(projectPermissionService.hasPermission(1L, 100L, "UPDATE_TASK")).thenReturn(true);

        taskLabelService.removeLabel(3L, 10L, 100L);

        assertFalse(task.getLabels().contains(label));
        verify(eventPublisher, times(1)).publishEvent(any(LabelRemovedEvent.class));
    }
}
