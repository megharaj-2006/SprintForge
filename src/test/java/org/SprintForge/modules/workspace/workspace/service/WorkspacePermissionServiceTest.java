package org.SprintForge.modules.workspace.workspace.service;

import org.SprintForge.modules.workspace.workspace.entity.Workspace;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceRole;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkspacePermissionServiceTest {

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private WorkspaceMemberRepository workspaceMemberRepository;

    @Mock
    private WorkspaceRoleRepository workspaceRoleRepository;

    @InjectMocks
    private WorkspacePermissionServiceImpl permissionService;

    private Workspace workspace;
    private WorkspaceMember activeMember;
    private WorkspaceRole customRole;

    @BeforeEach
    void setUp() {
        workspace = new Workspace();
        workspace.setId(1L);
        workspace.setOwnerId(10L);
        workspace.setDeleted(false);

        activeMember = new WorkspaceMember();
        activeMember.setId(100L);
        activeMember.setWorkspaceId(1L);
        activeMember.setUserId(2L);
        activeMember.setRoleId(20L);
        activeMember.setStatus(WorkspaceMemberStatus.ACTIVE);

        customRole = new WorkspaceRole();
        customRole.setId(20L);
        customRole.setWorkspaceId(1L);
        customRole.setName("DEVELOPER");
        customRole.setPermissions("PROJECT_CREATE,TASK_DELETE");
        customRole.setDeleted(false);
    }

    @Test
    void hasPermission_shouldReturnTrueForOwner() {
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));

        boolean result = permissionService.hasPermission(1L, 10L, "ANY_PERMISSION");

        assertTrue(result);
        verify(workspaceMemberRepository, never()).findByWorkspaceIdAndUserIdAndIsDeletedFalse(anyLong(), anyLong());
    }

    @Test
    void hasPermission_shouldReturnTrueForAdminRoleMember() {
        WorkspaceRole adminRole = new WorkspaceRole();
        adminRole.setId(30L);
        adminRole.setName("ADMIN");
        adminRole.setDeleted(false);

        activeMember.setRoleId(30L);

        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 2L))
                .thenReturn(Optional.of(activeMember));
        when(workspaceRoleRepository.findById(30L)).thenReturn(Optional.of(adminRole));

        boolean result = permissionService.hasPermission(1L, 2L, "SOME_RANDOM_PERMISSION");

        assertTrue(result);
    }

    @Test
    void hasPermission_shouldReturnTrueWhenPermissionGranted() {
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 2L))
                .thenReturn(Optional.of(activeMember));
        when(workspaceRoleRepository.findById(20L)).thenReturn(Optional.of(customRole));

        boolean canCreate = permissionService.hasPermission(1L, 2L, "PROJECT_CREATE");
        boolean canDelete = permissionService.hasPermission(1L, 2L, "TASK_DELETE");

        assertTrue(canCreate);
        assertTrue(canDelete);
    }

    @Test
    void hasPermission_shouldReturnFalseWhenPermissionNotGranted() {
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 2L))
                .thenReturn(Optional.of(activeMember));
        when(workspaceRoleRepository.findById(20L)).thenReturn(Optional.of(customRole));

        boolean result = permissionService.hasPermission(1L, 2L, "WORKSPACE_MANAGE");

        assertFalse(result);
    }

    @Test
    void hasPermission_shouldReturnFalseForSuspendedMember() {
        activeMember.setStatus(WorkspaceMemberStatus.SUSPENDED);

        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 2L))
                .thenReturn(Optional.of(activeMember));

        boolean result = permissionService.hasPermission(1L, 2L, "PROJECT_CREATE");

        assertFalse(result);
        verify(workspaceRoleRepository, never()).findById(anyLong());
    }

    @Test
    void helperMethods_shouldDelegateCorrectly() {
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 2L))
                .thenReturn(Optional.of(activeMember));
        when(workspaceRoleRepository.findById(20L)).thenReturn(Optional.of(customRole));

        assertTrue(permissionService.canCreateProjects(1L, 2L));
        assertTrue(permissionService.canDeleteTasks(1L, 2L));
        assertFalse(permissionService.canManageWorkspace(1L, 2L));
    }
}
