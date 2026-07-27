package org.SprintForge.modules.workspace.project.service;

import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.project.dto.request.AddProjectMemberRequest;
import org.SprintForge.modules.workspace.project.dto.request.UpdateProjectMemberRoleRequest;
import org.SprintForge.modules.workspace.project.dto.response.ProjectMemberResponse;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.entity.ProjectMember;
import org.SprintForge.modules.workspace.project.entity.ProjectRole;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectMemberStatus;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectVisibility;
import org.SprintForge.modules.workspace.project.event.*;
import org.SprintForge.modules.workspace.project.mapper.ProjectMapper;
import org.SprintForge.modules.workspace.project.repository.ProjectMemberRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRoleRepository;
import org.SprintForge.modules.workspace.project.service.member.ProjectMemberServiceImpl;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionServiceImpl;
import org.SprintForge.modules.workspace.workspace.entity.Workspace;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRepository;
import org.SprintForge.modules.workspace.workspace.service.WorkspacePermissionService;
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
class ProjectMemberServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Mock
    private ProjectRoleRepository projectRoleRepository;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private WorkspaceMemberRepository workspaceMemberRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WorkspacePermissionService workspacePermissionService;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private ProjectPermissionServiceImpl projectPermissionService;
    private ProjectMemberServiceImpl projectMemberService;

    private Project project;
    private Workspace workspace;
    private WorkspaceMember requesterWsMember;
    private WorkspaceMember targetWsMember;
    private User targetUser;
    private ProjectRole adminRole;
    private ProjectRole memberRole;
    private ProjectMember existingMember;

    @BeforeEach
    void setUp() {
        projectPermissionService = new ProjectPermissionServiceImpl(
                projectRepository,
                projectMemberRepository,
                projectRoleRepository,
                workspaceMemberRepository,
                workspacePermissionService
        );

        projectMemberService = new ProjectMemberServiceImpl(
                projectRepository,
                projectMemberRepository,
                projectRoleRepository,
                workspaceRepository,
                workspaceMemberRepository,
                userRepository,
                projectPermissionService,
                projectMapper,
                eventPublisher
        );

        workspace = new Workspace();
        workspace.setId(10L);
        workspace.setName("Workspace X");
        workspace.setArchived(false);
        workspace.setDeleted(false);

        project = new Project();
        project.setId(20L);
        project.setWorkspaceId(10L);
        project.setName("Project Y");
        project.setOwnerId(100L); // Requester Owner
        project.setVisibility(ProjectVisibility.WORKSPACE);
        project.setIsArchived(false);
        project.setDeleted(false);

        requesterWsMember = new WorkspaceMember();
        requesterWsMember.setId(101L);
        requesterWsMember.setWorkspaceId(10L);
        requesterWsMember.setUserId(100L);
        requesterWsMember.setStatus(WorkspaceMemberStatus.ACTIVE);

        targetWsMember = new WorkspaceMember();
        targetWsMember.setId(102L);
        targetWsMember.setWorkspaceId(10L);
        targetWsMember.setUserId(200L);
        targetWsMember.setStatus(WorkspaceMemberStatus.ACTIVE);

        targetUser = new User();
        targetUser.setId(200L);
        targetUser.setUsername("targetuser");
        targetUser.setEmail("target@sprintforge.com");

        adminRole = new ProjectRole();
        adminRole.setId(5L);
        adminRole.setProjectId(20L);
        adminRole.setName("ADMIN");
        adminRole.setPermissions("PROJECT_MEMBER_MANAGE,SPRINT_CREATE,TASK_MANAGE,TASK_ASSIGN,PROJECT_VIEW");

        memberRole = new ProjectRole();
        memberRole.setId(6L);
        memberRole.setProjectId(20L);
        memberRole.setName("MEMBER");
        memberRole.setPermissions("SPRINT_CREATE,TASK_MANAGE,TASK_ASSIGN,PROJECT_VIEW");

        existingMember = new ProjectMember();
        existingMember.setId(500L);
        existingMember.setProjectId(20L);
        existingMember.setWorkspaceMemberId(102L);
        existingMember.setRoleId(6L);
        existingMember.setStatus(ProjectMemberStatus.ACTIVE);
    }

    @Test
    void addMember_shouldSucceed() {
        AddProjectMemberRequest request = AddProjectMemberRequest.builder()
                .workspaceMemberId(102L)
                .roleName("MEMBER")
                .build();

        when(projectRepository.findById(20L)).thenReturn(Optional.of(project));
        when(workspaceRepository.findById(10L)).thenReturn(Optional.of(workspace));
        // Requester has OWNER privilege by default on hasPermission
        when(workspaceMemberRepository.findById(102L)).thenReturn(Optional.of(targetWsMember));
        when(projectMemberRepository.existsByProjectIdAndWorkspaceMemberIdAndIsDeletedFalse(20L, 102L)).thenReturn(false);
        when(projectRoleRepository.findByProjectIdAndNameAndIsDeletedFalse(20L, "MEMBER")).thenReturn(Optional.of(memberRole));

        when(projectMemberRepository.save(any(ProjectMember.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(projectMapper.toResponse(any(ProjectMember.class))).thenReturn(new ProjectMemberResponse());

        ProjectMemberResponse response = projectMemberService.addMember(20L, request, 100L);

        assertNotNull(response);
        verify(projectMemberRepository, times(1)).save(any(ProjectMember.class));
        verify(eventPublisher, times(1)).publishEvent(any(ProjectMemberAddedEvent.class));
    }

    @Test
    void addMember_shouldFailWhenWorkspaceMemberInactive() {
        AddProjectMemberRequest request = AddProjectMemberRequest.builder()
                .workspaceMemberId(102L)
                .roleName("MEMBER")
                .build();

        targetWsMember.setStatus(WorkspaceMemberStatus.SUSPENDED);

        when(projectRepository.findById(20L)).thenReturn(Optional.of(project));
        when(workspaceRepository.findById(10L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.findById(102L)).thenReturn(Optional.of(targetWsMember));

        assertThrows(BusinessRuleException.class, () ->
                projectMemberService.addMember(20L, request, 100L));
    }

    @Test
    void removeMember_shouldSucceed() {
        when(projectRepository.findById(20L)).thenReturn(Optional.of(project));
        when(projectMemberRepository.findById(500L)).thenReturn(Optional.of(existingMember));
        when(workspaceMemberRepository.findById(102L)).thenReturn(Optional.of(targetWsMember));

        projectMemberService.removeMember(20L, 500L, 100L);

        assertTrue(existingMember.isDeleted());
        assertEquals(ProjectMemberStatus.REMOVED, existingMember.getStatus());
        verify(eventPublisher, times(1)).publishEvent(any(ProjectMemberRemovedEvent.class));
    }

    @Test
    void removeMember_shouldFailWhenMemberIsOwner() {
        // Create an owner member
        ProjectMember ownerMember = new ProjectMember();
        ownerMember.setId(501L);
        ownerMember.setProjectId(20L);
        ownerMember.setWorkspaceMemberId(101L); // represents requester (owner)

        when(projectRepository.findById(20L)).thenReturn(Optional.of(project));
        when(projectMemberRepository.findById(501L)).thenReturn(Optional.of(ownerMember));
        when(workspaceMemberRepository.findById(101L)).thenReturn(Optional.of(requesterWsMember));

        assertThrows(BusinessRuleException.class, () ->
                projectMemberService.removeMember(20L, 501L, 100L));
    }

    @Test
    void leaveProject_shouldSucceed() {
        // Non-owner leaves project
        project.setOwnerId(300L); // owner is user 300

        when(projectRepository.findById(20L)).thenReturn(Optional.of(project));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(10L, 100L))
                .thenReturn(Optional.of(requesterWsMember));
        when(projectMemberRepository.findByProjectIdAndWorkspaceMemberIdAndIsDeletedFalse(20L, 101L))
                .thenReturn(Optional.of(existingMember)); // requester member

        projectMemberService.leaveProject(20L, 100L);

        assertTrue(existingMember.isDeleted());
        assertEquals(ProjectMemberStatus.REMOVED, existingMember.getStatus());
        verify(eventPublisher, times(1)).publishEvent(any(ProjectMemberLeftEvent.class));
    }

    @Test
    void leaveProject_shouldFailWhenOwnerLeaves() {
        // project owner is user 100
        when(projectRepository.findById(20L)).thenReturn(Optional.of(project));

        assertThrows(BusinessRuleException.class, () ->
                projectMemberService.leaveProject(20L, 100L));
    }

    @Test
    void changeRole_shouldSucceed() {
        UpdateProjectMemberRoleRequest request = UpdateProjectMemberRoleRequest.builder()
                .roleName("ADMIN")
                .build();

        when(projectRepository.findById(20L)).thenReturn(Optional.of(project));
        when(projectMemberRepository.findById(500L)).thenReturn(Optional.of(existingMember));
        when(projectRoleRepository.findByProjectIdAndNameAndIsDeletedFalse(20L, "ADMIN")).thenReturn(Optional.of(adminRole));
        when(projectMemberRepository.save(any(ProjectMember.class))).thenAnswer(invocation -> invocation.getArgument(0));

        projectMemberService.changeRole(20L, 500L, request, 100L);

        assertEquals(5L, existingMember.getRoleId());
        verify(eventPublisher, times(1)).publishEvent(any(ProjectMemberRoleChangedEvent.class));
    }

    @Test
    void hasPermission_shouldReturnTrueForProjectOwner() {
        when(projectRepository.findById(20L)).thenReturn(Optional.of(project));
        boolean result = projectPermissionService.hasPermission(20L, 100L, "SPRINT_CREATE");
        assertTrue(result);
    }

    @Test
    void hasPermission_shouldReturnTrueForWorkspaceOwner() {
        // project owner is user 100, but request is for user 300 (workspace owner)
        when(projectRepository.findById(20L)).thenReturn(Optional.of(project));
        when(workspacePermissionService.hasPermission(10L, 300L, WorkspacePermissionService.PROJECT_MANAGE)).thenReturn(true);

        boolean result = projectPermissionService.hasPermission(20L, 300L, "SPRINT_CREATE");
        assertTrue(result);
    }

    @Test
    void hasPermission_shouldReturnTrueForMemberWithAdminRole() {
        existingMember.setRoleId(5L); // ADMIN role
        when(projectRepository.findById(20L)).thenReturn(Optional.of(project));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(10L, 200L))
                .thenReturn(Optional.of(targetWsMember));
        when(projectMemberRepository.findByProjectIdAndWorkspaceMemberIdAndIsDeletedFalse(20L, 102L))
                .thenReturn(Optional.of(existingMember));
        when(projectRoleRepository.findById(5L)).thenReturn(Optional.of(adminRole));

        boolean result = projectPermissionService.hasPermission(20L, 200L, "PROJECT_MEMBER_MANAGE");
        assertTrue(result);
    }

    @Test
    void hasPermission_shouldReturnFalseForMemberWithoutPermission() {
        existingMember.setRoleId(6L); // MEMBER role (does not have PROJECT_MEMBER_MANAGE)
        when(projectRepository.findById(20L)).thenReturn(Optional.of(project));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(10L, 200L))
                .thenReturn(Optional.of(targetWsMember));
        when(projectMemberRepository.findByProjectIdAndWorkspaceMemberIdAndIsDeletedFalse(20L, 102L))
                .thenReturn(Optional.of(existingMember));
        when(projectRoleRepository.findById(6L)).thenReturn(Optional.of(memberRole));

        boolean result = projectPermissionService.hasPermission(20L, 200L, "PROJECT_MEMBER_MANAGE");
        assertFalse(result);
    }
}
