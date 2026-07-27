package org.SprintForge.modules.workspace.workspace.service;

import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.modules.workspace.workspace.dto.request.AssignWorkspaceRoleRequest;
import org.SprintForge.modules.workspace.workspace.dto.request.UpdateWorkspaceRoleRequest;
import org.SprintForge.modules.workspace.workspace.dto.request.WorkspaceRoleCreateRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceMemberResponse;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceRoleResponse;
import org.SprintForge.modules.workspace.workspace.entity.Workspace;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceRole;
import org.SprintForge.modules.workspace.workspace.event.WorkspacePermissionChangedEvent;
import org.SprintForge.modules.workspace.workspace.event.WorkspaceRoleAssignedEvent;
import org.SprintForge.modules.workspace.workspace.event.WorkspaceRoleCreatedEvent;
import org.SprintForge.modules.workspace.workspace.event.WorkspaceRoleDeletedEvent;
import org.SprintForge.modules.workspace.workspace.mapper.WorkspaceMapper;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkspaceRoleServiceTest {

    @Mock
    private WorkspaceRoleRepository workspaceRoleRepository;

    @Mock
    private WorkspaceMemberRepository workspaceMemberRepository;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private WorkspacePermissionService workspacePermissionService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Spy
    private WorkspaceMapper workspaceMapper = org.mapstruct.factory.Mappers.getMapper(WorkspaceMapper.class);

    @InjectMocks
    private WorkspaceRoleServiceImpl roleService;

    private Workspace workspace;
    private WorkspaceRole customRole;
    private WorkspaceRole defaultRole;
    private WorkspaceRole adminRole;
    private WorkspaceMember member;

    @BeforeEach
    void setUp() {
        workspace = new Workspace();
        workspace.setId(1L);
        workspace.setOwnerId(10L);
        workspace.setDefaultRoleId(2L);

        defaultRole = new WorkspaceRole();
        defaultRole.setId(2L);
        defaultRole.setWorkspaceId(1L);
        defaultRole.setName("MEMBER");
        defaultRole.setIsDefaultRole(true);
        defaultRole.setIsSystemRole(true);
        defaultRole.setDeleted(false);

        adminRole = new WorkspaceRole();
        adminRole.setId(3L);
        adminRole.setWorkspaceId(1L);
        adminRole.setName("ADMIN");
        adminRole.setIsDefaultRole(false);
        adminRole.setIsSystemRole(true);
        adminRole.setDeleted(false);

        customRole = new WorkspaceRole();
        customRole.setId(20L);
        customRole.setWorkspaceId(1L);
        customRole.setName("DEVELOPER");
        customRole.setIsDefaultRole(false);
        customRole.setIsSystemRole(false);
        customRole.setDeleted(false);

        member = new WorkspaceMember();
        member.setId(100L);
        member.setWorkspaceId(1L);
        member.setUserId(5L);
        member.setRoleId(2L);
        member.setDeleted(false);
    }

    @Test
    void createRole_shouldSucceedWhenAuthorizedAndUnique() {
        WorkspaceRoleCreateRequest request = WorkspaceRoleCreateRequest.builder()
                .workspaceId(1L)
                .name("TESTER")
                .description("Test Description")
                .isDefaultRole(false)
                .build();

        when(workspacePermissionService.canManageWorkspace(1L, 10L)).thenReturn(true);
        when(workspaceRoleRepository.findByWorkspaceIdAndNameAndIsDeletedFalse(1L, "TESTER"))
                .thenReturn(Optional.empty());
        when(workspaceRoleRepository.save(any(WorkspaceRole.class))).thenAnswer(invocation -> {
            WorkspaceRole r = invocation.getArgument(0);
            r.setId(50L);
            return r;
        });

        WorkspaceRoleResponse response = roleService.createRole(request, 10L);

        assertNotNull(response);
        assertEquals(50L, response.getId());
        assertEquals("TESTER", response.getName());
        verify(eventPublisher, times(1)).publishEvent(any(WorkspaceRoleCreatedEvent.class));
    }

    @Test
    void createRole_shouldThrowWhenDuplicateName() {
        WorkspaceRoleCreateRequest request = WorkspaceRoleCreateRequest.builder()
                .workspaceId(1L)
                .name("MEMBER")
                .build();

        when(workspacePermissionService.canManageWorkspace(1L, 10L)).thenReturn(true);
        when(workspaceRoleRepository.findByWorkspaceIdAndNameAndIsDeletedFalse(1L, "MEMBER"))
                .thenReturn(Optional.of(defaultRole));

        assertThrows(BusinessRuleException.class, () -> roleService.createRole(request, 10L));
    }

    @Test
    void updateRole_shouldThrowWhenRenamingSystemRole() {
        UpdateWorkspaceRoleRequest request = UpdateWorkspaceRoleRequest.builder()
                .name("NEW_ADMIN_NAME")
                .build();

        when(workspaceRoleRepository.findById(3L)).thenReturn(Optional.of(adminRole));
        when(workspacePermissionService.canManageWorkspace(1L, 10L)).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> roleService.updateRole(3L, request, 10L));
    }

    @Test
    void deleteRole_shouldThrowWhenDefaultRole() {
        when(workspaceRoleRepository.findById(2L)).thenReturn(Optional.of(defaultRole));
        when(workspacePermissionService.canManageWorkspace(1L, 10L)).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> roleService.deleteRole(2L, 10L));
    }

    @Test
    void deleteRole_shouldSucceedForCustomRoleAndReassignMembers() {
        List<WorkspaceMember> members = List.of(member);
        member.setRoleId(20L); // assigned to customRole

        when(workspaceRoleRepository.findById(20L)).thenReturn(Optional.of(customRole));
        when(workspacePermissionService.canManageWorkspace(1L, 10L)).thenReturn(true);
        when(workspaceRoleRepository.findByWorkspaceIdAndIsDeletedFalse(1L)).thenReturn(List.of(defaultRole, customRole));
        when(workspaceMemberRepository.findByWorkspaceIdAndIsDeletedFalse(1L)).thenReturn(members);

        roleService.deleteRole(20L, 10L);

        assertTrue(customRole.isDeleted());
        assertEquals(2L, member.getRoleId()); // reassigned to member defaultRole
        verify(workspaceRoleRepository, times(1)).save(customRole);
        verify(workspaceMemberRepository, times(1)).save(member);
        verify(eventPublisher, times(1)).publishEvent(any(WorkspaceRoleDeletedEvent.class));
    }

    @Test
    void assignRole_shouldSucceedForValidCustomRole() {
        AssignWorkspaceRoleRequest request = AssignWorkspaceRoleRequest.builder()
                .workspaceId(1L)
                .userId(5L)
                .roleId(20L)
                .build();

        when(workspacePermissionService.canAssignRoles(1L, 10L)).thenReturn(true);
        when(workspaceRoleRepository.findById(20L)).thenReturn(Optional.of(customRole));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 5L))
                .thenReturn(Optional.of(member));
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.save(any(WorkspaceMember.class))).thenAnswer(inv -> inv.getArgument(0));

        WorkspaceMemberResponse response = roleService.assignRole(request, 10L);

        assertNotNull(response);
        assertEquals(20L, response.getRoleId());
        verify(eventPublisher, times(1)).publishEvent(any(WorkspaceRoleAssignedEvent.class));
    }

    @Test
    void assignRole_shouldThrowIfUserIsOwner() {
        AssignWorkspaceRoleRequest request = AssignWorkspaceRoleRequest.builder()
                .workspaceId(1L)
                .userId(10L) // owner id
                .roleId(20L)
                .build();

        when(workspacePermissionService.canAssignRoles(1L, 10L)).thenReturn(true);
        when(workspaceRoleRepository.findById(20L)).thenReturn(Optional.of(customRole));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 10L))
                .thenReturn(Optional.of(member));
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));

        assertThrows(BusinessRuleException.class, () -> roleService.assignRole(request, 10L));
    }

    @Test
    void assignRole_shouldThrowIfRemovingLastAdmin() {
        // member is currently ADMIN
        member.setRoleId(3L);

        AssignWorkspaceRoleRequest request = AssignWorkspaceRoleRequest.builder()
                .workspaceId(1L)
                .userId(5L)
                .roleId(2L) // assigning to MEMBER role
                .build();

        when(workspacePermissionService.canAssignRoles(1L, 10L)).thenReturn(true);
        when(workspaceRoleRepository.findById(2L)).thenReturn(Optional.of(defaultRole));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 5L))
                .thenReturn(Optional.of(member));
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceRoleRepository.findById(3L)).thenReturn(Optional.of(adminRole));

        // When checking last admin, we list all admins except the target user. If empty, throw error.
        when(workspaceRoleRepository.findByWorkspaceIdAndIsDeletedFalse(1L)).thenReturn(List.of(adminRole, defaultRole));
        when(workspaceMemberRepository.findByWorkspaceIdAndIsDeletedFalse(1L)).thenReturn(List.of(member));

        assertThrows(BusinessRuleException.class, () -> roleService.assignRole(request, 10L));
    }
}
