package org.SprintForge.modules.workspace.workspace.service;

import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.workspace.dto.request.AddWorkspaceMemberRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceMemberResponse;
import org.SprintForge.modules.workspace.workspace.entity.Workspace;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceRole;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
import org.SprintForge.modules.workspace.workspace.event.WorkspaceMemberAddedEvent;
import org.SprintForge.modules.workspace.workspace.event.WorkspaceMemberRemovedEvent;
import org.SprintForge.modules.workspace.workspace.event.WorkspaceMemberSuspendedEvent;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkspaceMemberServiceTest {

    @Mock
    private WorkspaceMemberRepository workspaceMemberRepository;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private WorkspaceRoleRepository workspaceRoleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WorkspacePermissionService workspacePermissionService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Spy
    private WorkspaceMapper workspaceMapper = org.mapstruct.factory.Mappers.getMapper(WorkspaceMapper.class);

    @InjectMocks
    private WorkspaceMemberServiceImpl memberService;

    private Workspace workspace;
    private WorkspaceRole memberRole;
    private WorkspaceRole adminRole;
    private WorkspaceMember member;
    private User user;

    @BeforeEach
    void setUp() {
        workspace = new Workspace();
        workspace.setId(1L);
        workspace.setOwnerId(10L);
        workspace.setMaxMembers(10);
        workspace.setArchived(false);

        memberRole = new WorkspaceRole();
        memberRole.setId(2L);
        memberRole.setWorkspaceId(1L);
        memberRole.setName("MEMBER");
        memberRole.setIsDefaultRole(true);
        memberRole.setDeleted(false);

        adminRole = new WorkspaceRole();
        adminRole.setId(3L);
        adminRole.setWorkspaceId(1L);
        adminRole.setName("ADMIN");
        adminRole.setDeleted(false);

        user = new User();
        user.setId(5L);
        user.setUsername("developer");
        user.setEmail("dev@example.com");

        member = new WorkspaceMember();
        member.setId(100L);
        member.setWorkspaceId(1L);
        member.setUserId(5L);
        member.setRoleId(2L);
        member.setStatus(WorkspaceMemberStatus.ACTIVE);
        member.setDeleted(false);
    }

    @Test
    void addMember_shouldSucceedWhenUnderLimitAndNotAlreadyMember() {
        AddWorkspaceMemberRequest request = AddWorkspaceMemberRequest.builder()
                .workspaceId(1L)
                .email("dev@example.com")
                .roleId(2L)
                .build();

        when(workspacePermissionService.canInviteMembers(1L, 10L)).thenReturn(true);
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.countByWorkspaceIdAndStatusAndIsDeletedFalse(1L, WorkspaceMemberStatus.ACTIVE))
                .thenReturn(1L);
        when(userRepository.findByEmail("dev@example.com")).thenReturn(Optional.of(user));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 5L))
                .thenReturn(Optional.empty());
        when(workspaceRoleRepository.findById(2L)).thenReturn(Optional.of(memberRole));
        when(workspaceMemberRepository.save(any(WorkspaceMember.class))).thenAnswer(inv -> inv.getArgument(0));

        WorkspaceMemberResponse response = memberService.addMember(request, 10L);

        assertNotNull(response);
        assertEquals(5L, response.getUserId());
        assertEquals(2L, response.getRoleId());
        verify(eventPublisher, times(1)).publishEvent(any(WorkspaceMemberAddedEvent.class));
    }

    @Test
    void addMember_shouldThrowIfWorkspaceArchived() {
        workspace.setArchived(true);
        AddWorkspaceMemberRequest request = AddWorkspaceMemberRequest.builder()
                .workspaceId(1L)
                .email("dev@example.com")
                .roleId(2L)
                .build();

        when(workspacePermissionService.canInviteMembers(1L, 10L)).thenReturn(true);
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));

        assertThrows(BusinessRuleException.class, () -> memberService.addMember(request, 10L));
    }

    @Test
    void addMember_shouldThrowIfMaxMembersExceeded() {
        workspace.setMaxMembers(2);
        AddWorkspaceMemberRequest request = AddWorkspaceMemberRequest.builder()
                .workspaceId(1L)
                .email("dev@example.com")
                .roleId(2L)
                .build();

        when(workspacePermissionService.canInviteMembers(1L, 10L)).thenReturn(true);
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.countByWorkspaceIdAndStatusAndIsDeletedFalse(1L, WorkspaceMemberStatus.ACTIVE))
                .thenReturn(2L); // Limit reached

        assertThrows(BusinessRuleException.class, () -> memberService.addMember(request, 10L));
    }

    @Test
    void addMember_shouldThrowIfUserAlreadyActiveMember() {
        AddWorkspaceMemberRequest request = AddWorkspaceMemberRequest.builder()
                .workspaceId(1L)
                .email("dev@example.com")
                .roleId(2L)
                .build();

        when(workspacePermissionService.canInviteMembers(1L, 10L)).thenReturn(true);
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(userRepository.findByEmail("dev@example.com")).thenReturn(Optional.of(user));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 5L))
                .thenReturn(Optional.of(member)); // Already active member

        assertThrows(BusinessRuleException.class, () -> memberService.addMember(request, 10L));
    }

    @Test
    void removeMember_shouldThrowIfRemovingOwner() {
        // Attempt to remove owner (ID 10)
        when(workspacePermissionService.canManageWorkspace(1L, 10L)).thenReturn(true);
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));

        assertThrows(BusinessRuleException.class, () -> memberService.removeMember(1L, 10L, 10L));
    }

    @Test
    void removeMember_shouldSucceedForNormalMember() {
        when(workspacePermissionService.canManageWorkspace(1L, 10L)).thenReturn(true);
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 5L))
                .thenReturn(Optional.of(member));

        memberService.removeMember(1L, 5L, 10L);

        assertEquals(WorkspaceMemberStatus.REMOVED, member.getStatus());
        assertTrue(member.isDeleted());
        verify(workspaceMemberRepository, times(1)).save(member);
        verify(eventPublisher, times(1)).publishEvent(any(WorkspaceMemberRemovedEvent.class));
    }

    @Test
    void leaveWorkspace_shouldThrowForOwner() {
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));

        assertThrows(BusinessRuleException.class, () -> memberService.leaveWorkspace(1L, 10L, 10L));
    }

    @Test
    void suspendMember_shouldSucceed() {
        when(workspacePermissionService.canManageWorkspace(1L, 10L)).thenReturn(true);
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(1L, 5L))
                .thenReturn(Optional.of(member));
        when(workspaceMemberRepository.save(any(WorkspaceMember.class))).thenAnswer(inv -> inv.getArgument(0));

        WorkspaceMemberResponse response = memberService.suspendMember(1L, 5L, 10L);

        assertNotNull(response);
        assertEquals(WorkspaceMemberStatus.SUSPENDED, response.getStatus());
        verify(eventPublisher, times(1)).publishEvent(any(WorkspaceMemberSuspendedEvent.class));
    }
}
