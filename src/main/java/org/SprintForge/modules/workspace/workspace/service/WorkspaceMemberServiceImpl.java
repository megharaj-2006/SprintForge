package org.SprintForge.modules.workspace.workspace.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.workspace.dto.request.AddWorkspaceMemberRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceMemberResponse;
import org.SprintForge.modules.workspace.workspace.dto.response.MemberSearchResponse;
import org.SprintForge.modules.workspace.workspace.entity.Workspace;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceRole;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
import org.SprintForge.modules.workspace.workspace.event.*;
import org.SprintForge.modules.workspace.workspace.mapper.WorkspaceMapper;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRoleRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceMemberServiceImpl implements WorkspaceMemberService {

    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceRoleRepository workspaceRoleRepository;
    private final UserRepository userRepository;
    private final WorkspacePermissionService workspacePermissionService;
    private final WorkspaceMapper workspaceMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public WorkspaceMemberResponse addMember(AddWorkspaceMemberRequest request, Long actorId) {
        Long workspaceId = request.getWorkspaceId();
        if (!workspacePermissionService.canInviteMembers(workspaceId, actorId)) {
            throw new BusinessRuleException("Access Denied: You do not have permission to invite members to this workspace.");
        }

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .filter(w -> !w.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found."));

        if (workspace.isArchived()) {
            throw new BusinessRuleException("Cannot add members to an archived workspace.");
        }

        // Limit check
        long activeCount = workspaceMemberRepository.countByWorkspaceIdAndStatusAndIsDeletedFalse(workspaceId, WorkspaceMemberStatus.ACTIVE);
        if (workspace.getMaxMembers() != null && activeCount >= workspace.getMaxMembers()) {
            throw new BusinessRuleException("Cannot exceed workspace member limit of " + workspace.getMaxMembers());
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        // Already member check
        Optional<WorkspaceMember> existingMemberOpt = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, user.getId());

        if (existingMemberOpt.isPresent()) {
            WorkspaceMember existingMember = existingMemberOpt.get();
            if (existingMember.getStatus() == WorkspaceMemberStatus.ACTIVE) {
                throw new BusinessRuleException("User is already a member of this workspace.");
            } else if (existingMember.getStatus() == WorkspaceMemberStatus.INVITED) {
                throw new BusinessRuleException("User is already invited to this workspace.");
            } else {
                // If suspended or removed, reactivate
                existingMember.setStatus(WorkspaceMemberStatus.ACTIVE);
                existingMember.setRoleId(request.getRoleId());
                existingMember.setJoinedAt(LocalDateTime.now());
                WorkspaceMember saved = workspaceMemberRepository.save(existingMember);
                eventPublisher.publishEvent(new WorkspaceMemberAddedEvent(
                        workspaceId, saved.getUserId(), saved.getRoleId(), actorId, LocalDateTime.now()));
                return mapToResponse(saved);
            }
        }

        WorkspaceRole role = workspaceRoleRepository.findById(request.getRoleId())
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace role not found."));

        if (!role.getWorkspaceId().equals(workspaceId)) {
            throw new BusinessRuleException("Cannot assign invalid role: Role does not belong to this workspace.");
        }

        WorkspaceMember member = new WorkspaceMember();
        member.setWorkspaceId(workspaceId);
        member.setUserId(user.getId());
        member.setRoleId(role.getId());
        member.setStatus(WorkspaceMemberStatus.ACTIVE);
        member.setJoinedAt(LocalDateTime.now());

        WorkspaceMember saved = workspaceMemberRepository.save(member);

        eventPublisher.publishEvent(new WorkspaceMemberAddedEvent(
                workspaceId, saved.getUserId(), saved.getRoleId(), actorId, LocalDateTime.now()));

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public void removeMember(Long workspaceId, Long userId, Long actorId) {
        if (!workspacePermissionService.canManageWorkspace(workspaceId, actorId)) {
            throw new BusinessRuleException("Access Denied: You do not have permission to remove members from this workspace.");
        }

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found."));

        if (workspace.getOwnerId().equals(userId)) {
            throw new BusinessRuleException("Cannot remove the workspace owner from the workspace.");
        }

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member not found."));

        // Cannot remove last admin
        WorkspaceRole role = workspaceRoleRepository.findById(member.getRoleId()).orElse(null);
        if (role != null && "ADMIN".equalsIgnoreCase(role.getName())) {
            checkLastAdminConstraint(workspaceId, userId);
        }

        member.setStatus(WorkspaceMemberStatus.REMOVED);
        member.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        workspaceMemberRepository.save(member);

        eventPublisher.publishEvent(new WorkspaceMemberRemovedEvent(
                workspaceId, userId, actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public void leaveWorkspace(Long workspaceId, Long userId, Long actorId) {
        if (actorId != null && !userId.equals(actorId)) {
            throw new BusinessRuleException("Access Denied: You cannot make another user leave the workspace.");
        }

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found."));

        if (workspace.getOwnerId().equals(userId)) {
            throw new BusinessRuleException("Workspace Owner cannot leave the workspace. Please transfer ownership first.");
        }

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member not found."));

        // Cannot remove last admin
        WorkspaceRole role = workspaceRoleRepository.findById(member.getRoleId()).orElse(null);
        if (role != null && "ADMIN".equalsIgnoreCase(role.getName())) {
            checkLastAdminConstraint(workspaceId, userId);
        }

        member.setStatus(WorkspaceMemberStatus.REMOVED);
        member.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        workspaceMemberRepository.save(member);

        eventPublisher.publishEvent(new WorkspaceMemberRemovedEvent(
                workspaceId, userId, actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public WorkspaceMemberResponse suspendMember(Long workspaceId, Long userId, Long actorId) {
        if (!workspacePermissionService.canManageWorkspace(workspaceId, actorId)) {
            throw new BusinessRuleException("Access Denied: You do not have permission to suspend members in this workspace.");
        }

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found."));

        if (workspace.getOwnerId().equals(userId)) {
            throw new BusinessRuleException("Cannot suspend the workspace owner.");
        }

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member not found."));

        member.setStatus(WorkspaceMemberStatus.SUSPENDED);
        WorkspaceMember saved = workspaceMemberRepository.save(member);

        eventPublisher.publishEvent(new WorkspaceMemberSuspendedEvent(
                workspaceId, userId, actorId, LocalDateTime.now()));

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public WorkspaceMemberResponse reactivateMember(Long workspaceId, Long userId, Long actorId) {
        if (!workspacePermissionService.canManageWorkspace(workspaceId, actorId)) {
            throw new BusinessRuleException("Access Denied: You do not have permission to reactivate members in this workspace.");
        }

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member not found."));

        if (member.getStatus() != WorkspaceMemberStatus.SUSPENDED) {
            throw new BusinessRuleException("Member is not suspended.");
        }

        member.setStatus(WorkspaceMemberStatus.ACTIVE);
        WorkspaceMember saved = workspaceMemberRepository.save(member);

        eventPublisher.publishEvent(new WorkspaceMemberReactivatedEvent(
                workspaceId, userId, actorId, LocalDateTime.now()));

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public WorkspaceMemberResponse changeMemberRole(Long workspaceId, Long userId, Long roleId, Long actorId) {
        if (!workspacePermissionService.canAssignRoles(workspaceId, actorId)) {
            throw new BusinessRuleException("Access Denied: You do not have permission to assign roles in this workspace.");
        }

        WorkspaceRole role = workspaceRoleRepository.findById(roleId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace role not found."));

        if (!role.getWorkspaceId().equals(workspaceId)) {
            throw new BusinessRuleException("Cannot assign invalid role: Role does not belong to this workspace.");
        }

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member not found."));

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found."));

        if (workspace.getOwnerId().equals(userId)) {
            throw new BusinessRuleException("Cannot change the role of the workspace owner.");
        }

        // Check if removing last admin
        WorkspaceRole currentRole = workspaceRoleRepository.findById(member.getRoleId()).orElse(null);
        if (currentRole != null && "ADMIN".equalsIgnoreCase(currentRole.getName()) && !"ADMIN".equalsIgnoreCase(role.getName())) {
            checkLastAdminConstraint(workspaceId, userId);
        }

        member.setRoleId(role.getId());
        WorkspaceMember saved = workspaceMemberRepository.save(member);

        eventPublisher.publishEvent(new WorkspaceRoleAssignedEvent(
                workspaceId, userId, roleId, actorId, LocalDateTime.now()));

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceMemberResponse> getMembers(Long workspaceId) {
        return workspaceMemberRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceMemberResponse> getActiveMembers(Long workspaceId) {
        return workspaceMemberRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId)
                .stream()
                .filter(m -> m.getStatus() == WorkspaceMemberStatus.ACTIVE)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceMemberResponse> getPendingMembers(Long workspaceId) {
        return workspaceMemberRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId)
                .stream()
                .filter(m -> m.getStatus() == WorkspaceMemberStatus.INVITED)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceMemberResponse> getWorkspaceAdmins(Long workspaceId) {
        List<WorkspaceRole> roles = workspaceRoleRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId);
        List<Long> adminRoleIds = roles.stream()
                .filter(r -> "ADMIN".equalsIgnoreCase(r.getName()))
                .map(WorkspaceRole::getId)
                .toList();

        return workspaceMemberRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId)
                .stream()
                .filter(m -> adminRoleIds.contains(m.getRoleId()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WorkspaceMemberResponse getWorkspaceOwner(Long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found."));

        WorkspaceMember ownerMember = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, workspace.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace owner member record not found."));

        return mapToResponse(ownerMember);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isWorkspaceMember(Long workspaceId, Long userId) {
        return workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, userId)
                .map(m -> m.getStatus() == WorkspaceMemberStatus.ACTIVE)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public long countMembers(Long workspaceId) {
        return workspaceMemberRepository.countByWorkspaceIdAndStatusAndIsDeletedFalse(workspaceId, WorkspaceMemberStatus.ACTIVE);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberSearchResponse searchMembers(Long workspaceId, String query, int page, int size) {
        Page<WorkspaceMember> resultPage = workspaceMemberRepository.searchWorkspaceMembers(
                workspaceId, query, PageRequest.of(page, size));

        List<WorkspaceMemberResponse> responseList = resultPage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return MemberSearchResponse.builder()
                .members(responseList)
                .totalElements(resultPage.getTotalElements())
                .totalPages(resultPage.getTotalPages())
                .currentPage(resultPage.getNumber())
                .build();
    }

    private void checkLastAdminConstraint(Long workspaceId, Long userIdExcluding) {
        WorkspaceRole adminRole = workspaceRoleRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId)
                .stream()
                .filter(r -> "ADMIN".equalsIgnoreCase(r.getName()))
                .findFirst()
                .orElse(null);

        if (adminRole == null) return;

        List<WorkspaceMember> admins = workspaceMemberRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId)
                .stream()
                .filter(m -> adminRole.getId().equals(m.getRoleId()))
                .filter(m -> !m.getUserId().equals(userIdExcluding))
                .toList();

        if (admins.isEmpty()) {
            throw new BusinessRuleException("Cannot remove the last administrator from the workspace.");
        }
    }

    private WorkspaceMemberResponse mapToResponse(WorkspaceMember member) {
        WorkspaceMemberResponse response = workspaceMapper.toResponse(member);

        userRepository.findById(member.getUserId()).ifPresent(user -> {
            response.setUserName(user.getFullName() != null ? user.getFullName() : user.getUsername());
            response.setUserEmail(user.getEmail());
            response.setAvatarUrl(user.getProfilePicture());
        });

        if (member.getRoleId() != null) {
            workspaceRoleRepository.findById(member.getRoleId()).ifPresent(role -> {
                response.setRoleName(role.getName());
            });
        }

        return response;
    }
}
