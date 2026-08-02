package org.SprintForge.modules.workspace.project.service;

import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.dto.request.ProjectCreateRequest;
import org.SprintForge.modules.workspace.project.dto.request.ProjectUpdateRequest;
import org.SprintForge.modules.workspace.project.dto.response.ProjectResponse;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.entity.ProjectMember;
import org.SprintForge.modules.workspace.project.entity.ProjectRole;
import org.SprintForge.modules.workspace.project.entity.ProjectSettings;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectStatusType;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectVisibility;
import org.SprintForge.modules.workspace.project.event.*;
import org.SprintForge.modules.workspace.project.mapper.ProjectMapper;
import org.SprintForge.modules.workspace.project.repository.ProjectMemberRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRoleRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectSettingsRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.SprintForge.modules.workspace.project.service.management.ProjectLifecycleServiceImpl;
import org.SprintForge.modules.workspace.project.service.query.ProjectQueryServiceImpl;
import org.SprintForge.modules.workspace.workspace.entity.Workspace;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceSubscription;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceSubscriptionRepository;
import org.SprintForge.modules.workspace.workspace.service.WorkspacePermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectSettingsRepository projectSettingsRepository;

    @Mock
    private ProjectRoleRepository projectRoleRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private WorkspaceMemberRepository workspaceMemberRepository;

    @Mock
    private WorkspaceSubscriptionRepository workspaceSubscriptionRepository;

    @Mock
    private WorkspacePermissionService workspacePermissionService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private ProjectLifecycleServiceImpl projectLifecycleService;
    private ProjectQueryServiceImpl projectQueryService;

    private Workspace workspace;
    private WorkspaceMember activeMember;
    private Project project;

    @BeforeEach
    void setUp() {
        projectLifecycleService = new ProjectLifecycleServiceImpl(
                projectRepository,
                projectSettingsRepository,
                projectRoleRepository,
                projectMemberRepository,
                workspaceRepository,
                workspaceMemberRepository,
                workspaceSubscriptionRepository,
                workspacePermissionService,
                taskRepository,
                projectMapper,
                eventPublisher
        );

        projectQueryService = new ProjectQueryServiceImpl(
                projectRepository,
                workspaceMemberRepository,
                workspacePermissionService,
                projectMapper
        );

        workspace = new Workspace();
        workspace.setId(1L);
        workspace.setName("Test Workspace");
        workspace.setDeleted(false);
        workspace.setArchived(false);

        activeMember = new WorkspaceMember();
        activeMember.setId(10L);
        activeMember.setWorkspaceId(1L);
        activeMember.setUserId(100L);
        activeMember.setStatus(WorkspaceMemberStatus.ACTIVE);

        project = new Project();
        project.setId(2L);
        project.setWorkspaceId(1L);
        project.setName("Test Project");
        project.setProjectKey("TEST");
        project.setOwnerId(100L);
        project.setVisibility(ProjectVisibility.WORKSPACE);
        project.setIsArchived(false);
    }

    @Test
    void createProject_shouldSucceed() {
        ProjectCreateRequest request = ProjectCreateRequest.builder()
                .name("Test Project")
                .projectKey("TEST")
                .build();

        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 100L))
                .thenReturn(Optional.of(activeMember));
        when(workspacePermissionService.canCreateProjects(1L, 100L)).thenReturn(true);
        when(projectRepository.existsByWorkspaceIdAndNameAndIsDeletedFalse(1L, "Test Project")).thenReturn(false);
        when(projectRepository.existsByWorkspaceIdAndProjectKeyAndIsDeletedFalse(1L, "TEST")).thenReturn(false);
        when(workspaceSubscriptionRepository.findByWorkspaceIdAndIsDeletedFalse(1L)).thenReturn(Optional.empty());

        Project unsavedProject = new Project();
        unsavedProject.setName("Test Project");
        unsavedProject.setProjectKey("TEST");

        when(projectMapper.toEntity(request)).thenReturn(unsavedProject);
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectRoleRepository.save(any(ProjectRole.class))).thenAnswer(invocation -> {
            ProjectRole r = invocation.getArgument(0);
            r.setId(50L);
            return r;
        });
        when(projectMemberRepository.save(any(ProjectMember.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(projectMapper.toResponse(project)).thenReturn(ProjectResponse.builder().id(2L).name("Test Project").build());

        ProjectResponse response = projectLifecycleService.createProject(1L, request, 100L);

        assertNotNull(response);
        assertEquals(2L, response.getId());
        verify(projectRepository, times(1)).save(any(Project.class));
        verify(projectSettingsRepository, times(1)).save(any(ProjectSettings.class));
        verify(eventPublisher, times(1)).publishEvent(any(ProjectCreatedEvent.class));
    }

    @Test
    void createProject_shouldFailWhenWorkspaceArchived() {
        workspace.setArchived(true);
        ProjectCreateRequest request = ProjectCreateRequest.builder()
                .name("Test Project")
                .projectKey("TEST")
                .build();

        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));

        assertThrows(BusinessRuleException.class, () ->
                projectLifecycleService.createProject(1L, request, 100L));
    }

    @Test
    void createProject_shouldFailWhenUserNotMember() {
        ProjectCreateRequest request = ProjectCreateRequest.builder()
                .name("Test Project")
                .projectKey("TEST")
                .build();

        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 100L))
                .thenReturn(Optional.empty());

        assertThrows(ForbiddenException.class, () ->
                projectLifecycleService.createProject(1L, request, 100L));
    }

    @Test
    void createProject_shouldFailWhenNoPermission() {
        ProjectCreateRequest request = ProjectCreateRequest.builder()
                .name("Test Project")
                .projectKey("TEST")
                .build();

        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 100L))
                .thenReturn(Optional.of(activeMember));
        when(workspacePermissionService.canCreateProjects(1L, 100L)).thenReturn(false);

        assertThrows(ForbiddenException.class, () ->
                projectLifecycleService.createProject(1L, request, 100L));
    }

    @Test
    void createProject_shouldFailWhenNameNotUnique() {
        ProjectCreateRequest request = ProjectCreateRequest.builder()
                .name("Test Project")
                .projectKey("TEST")
                .build();

        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 100L))
                .thenReturn(Optional.of(activeMember));
        when(workspacePermissionService.canCreateProjects(1L, 100L)).thenReturn(true);
        when(projectRepository.existsByWorkspaceIdAndNameAndIsDeletedFalse(1L, "Test Project")).thenReturn(true);

        assertThrows(ConflictException.class, () ->
                projectLifecycleService.createProject(1L, request, 100L));
    }

    @Test
    void createProject_shouldFailWhenProjectLimitExceeded() {
        ProjectCreateRequest request = ProjectCreateRequest.builder()
                .name("Test Project")
                .projectKey("TEST")
                .build();

        WorkspaceSubscription sub = new WorkspaceSubscription();
        sub.setMaxProjects(5);

        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 100L))
                .thenReturn(Optional.of(activeMember));
        when(workspacePermissionService.canCreateProjects(1L, 100L)).thenReturn(true);
        when(projectRepository.existsByWorkspaceIdAndNameAndIsDeletedFalse(1L, "Test Project")).thenReturn(false);
        when(projectRepository.existsByWorkspaceIdAndProjectKeyAndIsDeletedFalse(1L, "TEST")).thenReturn(false);
        when(workspaceSubscriptionRepository.findByWorkspaceIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(sub));
        when(projectRepository.countByWorkspaceIdAndIsDeletedFalse(1L)).thenReturn(5L);

        assertThrows(BusinessRuleException.class, () ->
                projectLifecycleService.createProject(1L, request, 100L));
    }

    @Test
    void updateProject_shouldSucceed() {
        ProjectUpdateRequest request = ProjectUpdateRequest.builder()
                .name("Updated Name")
                .build();

        when(projectRepository.findById(2L)).thenReturn(Optional.of(project));
        when(workspacePermissionService.canManageProjects(1L, 100L)).thenReturn(true);
        when(projectRepository.existsByWorkspaceIdAndNameAndIsDeletedFalse(1L, "Updated Name")).thenReturn(false);
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toResponse(project)).thenReturn(ProjectResponse.builder().id(2L).name("Updated Name").build());

        ProjectResponse response = projectLifecycleService.updateProject(2L, request, 100L);

        assertNotNull(response);
        assertEquals("Updated Name", response.getName());
        verify(projectMapper, times(1)).updateEntity(request, project);
        verify(eventPublisher, times(1)).publishEvent(any(ProjectUpdatedEvent.class));
    }

    @Test
    void archiveProject_shouldSucceed() {
        when(projectRepository.findById(2L)).thenReturn(Optional.of(project));
        when(workspacePermissionService.canManageProjects(1L, 100L)).thenReturn(true);
        when(projectRepository.save(project)).thenReturn(project);

        projectLifecycleService.archiveProject(2L, 100L);

        assertTrue(project.getIsArchived());
        assertEquals(ProjectStatusType.ARCHIVED, project.getStatus());
        verify(eventPublisher, times(1)).publishEvent(any(ProjectArchivedEvent.class));
    }

    @Test
    void restoreProject_shouldSucceed() {
        project.setIsArchived(true);
        project.setStatus(ProjectStatusType.ARCHIVED);

        when(projectRepository.findById(2L)).thenReturn(Optional.of(project));
        when(workspacePermissionService.canManageProjects(1L, 100L)).thenReturn(true);
        when(projectRepository.save(project)).thenReturn(project);

        projectLifecycleService.restoreProject(2L, 100L);

        assertFalse(project.getIsArchived());
        assertEquals(ProjectStatusType.ACTIVE, project.getStatus());
        verify(eventPublisher, times(1)).publishEvent(any(ProjectRestoredEvent.class));
    }

    @Test
    void deleteProject_shouldSucceed() {
        when(projectRepository.findById(2L)).thenReturn(Optional.of(project));
        when(workspacePermissionService.canManageProjects(1L, 100L)).thenReturn(true);
        when(projectSettingsRepository.findByProjectIdAndIsDeletedFalse(2L)).thenReturn(Optional.empty());

        projectLifecycleService.deleteProject(2L, 100L);

        assertTrue(project.isDeleted());
        verify(projectRepository, times(1)).save(project);
        verify(eventPublisher, times(1)).publishEvent(any(ProjectDeletedEvent.class));
    }

    @Test
    void duplicateProject_shouldSucceed() {
        when(projectRepository.findById(2L)).thenReturn(Optional.of(project));
        when(workspacePermissionService.canManageProjects(1L, 100L)).thenReturn(true);
        when(projectRepository.existsByWorkspaceIdAndNameAndIsDeletedFalse(1L, "Copy of Test Project")).thenReturn(false);
        when(projectRepository.existsByWorkspaceIdAndProjectKeyAndIsDeletedFalse(1L, "TESTC")).thenReturn(false);
        when(projectSettingsRepository.findByProjectIdAndIsDeletedFalse(2L)).thenReturn(Optional.empty());
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 100L)).thenReturn(Optional.of(activeMember));
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(projectRoleRepository.save(any(ProjectRole.class))).thenAnswer(invocation -> {
            ProjectRole r = invocation.getArgument(0);
            r.setId(50L);
            return r;
        });
        when(projectMemberRepository.save(any(ProjectMember.class))).thenAnswer(invocation -> invocation.getArgument(0));

        projectLifecycleService.duplicateProject(2L, 100L);

        verify(projectRepository, times(1)).save(any(Project.class)); // Duplicate project save (settings are saved to projectSettingsRepository)
        verify(eventPublisher, times(1)).publishEvent(any(ProjectDuplicatedEvent.class));
    }

    @Test
    void getProject_shouldFailWhenAccessDenied() {
        project.setVisibility(ProjectVisibility.PRIVATE);
        project.setOwnerId(200L); // different owner

        when(projectRepository.findById(2L)).thenReturn(Optional.of(project));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 100L))
                .thenReturn(Optional.of(activeMember));
        when(workspacePermissionService.hasPermission(1L, 100L, "PROJECT_MANAGE")).thenReturn(false);

        assertThrows(ForbiddenException.class, () ->
                projectQueryService.getProject(2L, 100L));
    }
}
