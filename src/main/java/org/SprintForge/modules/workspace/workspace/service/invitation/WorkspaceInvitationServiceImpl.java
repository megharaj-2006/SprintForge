package org.SprintForge.modules.workspace.workspace.service.invitation;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.workspace.dto.request.InviteMemberRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceInvitationResponse;
import org.SprintForge.modules.workspace.workspace.entity.Workspace;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceInvitation;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceRole;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceInvitationStatus;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
import org.SprintForge.modules.workspace.workspace.event.*;
import org.SprintForge.modules.workspace.workspace.mapper.WorkspaceMapper;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceInvitationRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRoleRepository;
import org.SprintForge.modules.workspace.workspace.service.WorkspacePermissionService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceInvitationServiceImpl implements WorkspaceInvitationService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceRoleRepository workspaceRoleRepository;
    private final WorkspaceInvitationRepository workspaceInvitationRepository;
    private final UserRepository userRepository;
    private final WorkspacePermissionService workspacePermissionService;
    private final WorkspaceMapper workspaceMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public WorkspaceInvitationResponse inviteMember(Long workspaceId, InviteMemberRequest request, Long actorId) {
        // 1. Workspace exists
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .filter(w -> !w.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found."));

        // 2. Workspace isn't archived
        if (workspace.isArchived()) {
            throw new BusinessRuleException("Cannot invite members to an archived workspace.");
        }

        // 3. Inviter is member
        WorkspaceMember inviter = workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, actorId)
                .orElseThrow(() -> new ForbiddenException("Inviter is not a member of this workspace."));
        if (inviter.getStatus() != WorkspaceMemberStatus.ACTIVE) {
            throw new ForbiddenException("Inviter is not an active member.");
        }

        // 4. Inviter has MEMBER_INVITE permission
        if (!workspacePermissionService.canInviteMembers(workspaceId, actorId)) {
            throw new ForbiddenException("You do not have permission to invite members.");
        }

        // 5. Look up user by email to verify if already a member
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user != null) {
            Optional<WorkspaceMember> existingMemberOpt = workspaceMemberRepository
                    .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, user.getId());
            if (existingMemberOpt.isPresent() && existingMemberOpt.get().getStatus() == WorkspaceMemberStatus.ACTIVE) {
                throw new BusinessRuleException("User is already a member of this workspace.");
            }
        }

        // 6. User isn't already invited (check if active pending invitation exists)
        boolean alreadyInvited = workspaceInvitationRepository
                .existsByWorkspaceIdAndEmailAndStatusAndIsDeletedFalse(workspaceId, request.getEmail(), WorkspaceInvitationStatus.PENDING);
        if (alreadyInvited) {
            throw new BusinessRuleException("User is already invited to this workspace.");
        }

        // 7. Role exists
        WorkspaceRole role = workspaceRoleRepository.findById(request.getRoleId())
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found."));

        // 8. Member limit check
        long activeCount = workspaceMemberRepository.countByWorkspaceIdAndStatusAndIsDeletedFalse(workspaceId, WorkspaceMemberStatus.ACTIVE);
        if (workspace.getMaxMembers() != null && activeCount >= workspace.getMaxMembers()) {
            throw new BusinessRuleException("Cannot exceed workspace member limit of " + workspace.getMaxMembers());
        }

        // 9. Create invitation
        WorkspaceInvitation invitation = new WorkspaceInvitation();
        invitation.setWorkspaceId(workspaceId);
        invitation.setEmail(request.getEmail());
        invitation.setInvitedBy(actorId);
        invitation.setRoleId(request.getRoleId());
        invitation.setInvitedUserId(user != null ? user.getId() : null);
        invitation.setStatus(WorkspaceInvitationStatus.PENDING);
        invitation.setInviteToken(UUID.randomUUID().toString());
        invitation.setMessage(request.getMessage());
        int expDays = request.getExpirationDays() != null ? request.getExpirationDays() : 7;
        invitation.setExpiresAt(LocalDateTime.now().plusDays(expDays));

        WorkspaceInvitation saved = workspaceInvitationRepository.save(invitation);

        // 10. Publish event
        eventPublisher.publishEvent(new WorkspaceInvitationCreatedEvent(
                saved.getId(), saved.getWorkspaceId(), saved.getEmail(), saved.getInvitedBy(), LocalDateTime.now()));

        return populateDetails(saved, role, user, workspace);
    }

    @Override
    @Transactional
    public WorkspaceInvitationResponse acceptInvitation(String token, Long userId) {
        // 1. Invitation exists
        WorkspaceInvitation invitation = workspaceInvitationRepository.findByInviteTokenAndIsDeletedFalse(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found."));

        // 2. Not expired
        if (invitation.getExpiresAt().isBefore(LocalDateTime.now()) || invitation.getStatus() == WorkspaceInvitationStatus.EXPIRED) {
            invitation.setStatus(WorkspaceInvitationStatus.EXPIRED);
            workspaceInvitationRepository.save(invitation);
            throw new BusinessRuleException("Invitation has expired.");
        }

        // 3. Status == PENDING
        if (invitation.getStatus() != WorkspaceInvitationStatus.PENDING) {
            throw new BusinessRuleException("Invitation is not pending.");
        }

        // 4. Email matches logged-in user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        if (!user.getEmail().equalsIgnoreCase(invitation.getEmail())) {
            throw new BusinessRuleException("Logged-in user email does not match the invitation email.");
        }

        // 5. Workspace still exists
        Workspace workspace = workspaceRepository.findById(invitation.getWorkspaceId())
                .filter(w -> !w.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found."));

        // 6. Workspace not archived
        if (workspace.isArchived()) {
            throw new BusinessRuleException("Cannot join an archived workspace.");
        }

        // 7. Role still exists
        WorkspaceRole role = workspaceRoleRepository.findById(invitation.getRoleId())
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Assigned role not found."));

        // 8. Member limit check
        long activeCount = workspaceMemberRepository.countByWorkspaceIdAndStatusAndIsDeletedFalse(workspace.getId(), WorkspaceMemberStatus.ACTIVE);
        if (workspace.getMaxMembers() != null && activeCount >= workspace.getMaxMembers()) {
            throw new BusinessRuleException("Cannot join workspace: member limit exceeded.");
        }

        // 9. Create WorkspaceMember
        Optional<WorkspaceMember> existingMemberOpt = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspace.getId(), user.getId());
        WorkspaceMember member;
        if (existingMemberOpt.isPresent()) {
            member = existingMemberOpt.get();
            member.setStatus(WorkspaceMemberStatus.ACTIVE);
            member.setRoleId(role.getId());
            member.setJoinedAt(LocalDateTime.now());
        } else {
            member = new WorkspaceMember();
            member.setWorkspaceId(workspace.getId());
            member.setUserId(user.getId());
            member.setRoleId(role.getId());
            member.setStatus(WorkspaceMemberStatus.ACTIVE);
            member.setJoinedViaInvite(true);
            member.setJoinedAt(LocalDateTime.now());
        }
        workspaceMemberRepository.save(member);

        // 10. Mark invitation ACCEPTED
        invitation.setStatus(WorkspaceInvitationStatus.ACCEPTED);
        invitation.setAcceptedAt(LocalDateTime.now());
        invitation.setInvitedUserId(user.getId());
        WorkspaceInvitation saved = workspaceInvitationRepository.save(invitation);

        // 11. Publish event
        eventPublisher.publishEvent(new WorkspaceInvitationAcceptedEvent(
                saved.getId(), saved.getWorkspaceId(), saved.getEmail(), user.getId(), LocalDateTime.now()));

        return populateDetails(saved, role, user, workspace);
    }

    @Override
    @Transactional
    public WorkspaceInvitationResponse rejectInvitation(String token, Long userId) {
        WorkspaceInvitation invitation = workspaceInvitationRepository.findByInviteTokenAndIsDeletedFalse(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found."));

        if (invitation.getExpiresAt().isBefore(LocalDateTime.now()) || invitation.getStatus() == WorkspaceInvitationStatus.EXPIRED) {
            invitation.setStatus(WorkspaceInvitationStatus.EXPIRED);
            workspaceInvitationRepository.save(invitation);
            throw new BusinessRuleException("Invitation has expired.");
        }

        if (invitation.getStatus() != WorkspaceInvitationStatus.PENDING) {
            throw new BusinessRuleException("Invitation is not pending.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        if (!user.getEmail().equalsIgnoreCase(invitation.getEmail())) {
            throw new BusinessRuleException("Logged-in user email does not match the invitation email.");
        }

        invitation.setStatus(WorkspaceInvitationStatus.REJECTED);
        invitation.setRejectedAt(LocalDateTime.now());
        WorkspaceInvitation saved = workspaceInvitationRepository.save(invitation);

        eventPublisher.publishEvent(new WorkspaceInvitationRejectedEvent(
                saved.getId(), saved.getWorkspaceId(), saved.getEmail(), LocalDateTime.now()));

        return populateDetails(saved, null, user, null);
    }

    @Override
    @Transactional
    public void cancelInvitation(String token, Long actorId) {
        WorkspaceInvitation invitation = workspaceInvitationRepository.findByInviteTokenAndIsDeletedFalse(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found."));

        if (invitation.getStatus() != WorkspaceInvitationStatus.PENDING) {
            throw new BusinessRuleException("Only pending invitations can be cancelled.");
        }

        if (!invitation.getInvitedBy().equals(actorId) && !workspacePermissionService.canInviteMembers(invitation.getWorkspaceId(), actorId)) {
            throw new ForbiddenException("You do not have permission to cancel this invitation.");
        }

        invitation.setStatus(WorkspaceInvitationStatus.CANCELLED);
        invitation.markDeleted(String.valueOf(actorId));
        workspaceInvitationRepository.save(invitation);

        eventPublisher.publishEvent(new WorkspaceInvitationCancelledEvent(
                invitation.getId(), invitation.getWorkspaceId(), invitation.getEmail(), actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public WorkspaceInvitationResponse resendInvitation(String token, Long actorId) {
        WorkspaceInvitation invitation = workspaceInvitationRepository.findByInviteTokenAndIsDeletedFalse(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found."));

        if (!invitation.getInvitedBy().equals(actorId) && !workspacePermissionService.canInviteMembers(invitation.getWorkspaceId(), actorId)) {
            throw new ForbiddenException("You do not have permission to resend this invitation.");
        }

        invitation.setStatus(WorkspaceInvitationStatus.PENDING);
        invitation.setInviteToken(UUID.randomUUID().toString());
        invitation.setExpiresAt(LocalDateTime.now().plusDays(7));
        WorkspaceInvitation saved = workspaceInvitationRepository.save(invitation);

        eventPublisher.publishEvent(new WorkspaceInvitationResentEvent(
                saved.getId(), saved.getWorkspaceId(), saved.getEmail(), actorId, LocalDateTime.now()));

        return populateDetails(saved, null, null, null);
    }

    @Override
    @Transactional
    public void expireInvitation(String token) {
        WorkspaceInvitation invitation = workspaceInvitationRepository.findByInviteTokenAndIsDeletedFalse(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found."));

        invitation.setStatus(WorkspaceInvitationStatus.EXPIRED);
        workspaceInvitationRepository.save(invitation);

        eventPublisher.publishEvent(new WorkspaceInvitationExpiredEvent(
                invitation.getId(), invitation.getWorkspaceId(), invitation.getEmail(), LocalDateTime.now()));
    }

    @Override
    @Transactional(readOnly = true)
    public WorkspaceInvitationResponse getInvitation(String token) {
        WorkspaceInvitation invitation = workspaceInvitationRepository.findByInviteTokenAndIsDeletedFalse(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found."));
        return populateDetails(invitation, null, null, null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceInvitationResponse> getWorkspaceInvitations(Long workspaceId, Long actorId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .filter(w -> !w.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found."));

        boolean isMember = workspaceMemberRepository.existsByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, actorId)
                || actorId.equals(workspace.getOwnerId());
        if (!isMember) {
            throw new ForbiddenException("You are not a member of this workspace.");
        }

        List<WorkspaceInvitation> invitations = workspaceInvitationRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId);
        return invitations.stream()
                .map(i -> populateDetails(i, null, null, workspace))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceInvitationResponse> getPendingInvitations(Long workspaceId, Long actorId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .filter(w -> !w.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found."));

        boolean isMember = workspaceMemberRepository.existsByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, actorId)
                || actorId.equals(workspace.getOwnerId());
        if (!isMember) {
            throw new ForbiddenException("You are not a member of this workspace.");
        }

        List<WorkspaceInvitation> invitations = workspaceInvitationRepository
                .findByWorkspaceIdAndStatusAndIsDeletedFalse(workspaceId, WorkspaceInvitationStatus.PENDING);
        return invitations.stream()
                .map(i -> populateDetails(i, null, null, workspace))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceInvitationResponse> getUserInvitations(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        List<WorkspaceInvitation> invitations = workspaceInvitationRepository.findByEmailAndIsDeletedFalse(user.getEmail());
        return invitations.stream()
                .map(i -> populateDetails(i, null, user, null))
                .collect(Collectors.toList());
    }

    private WorkspaceInvitationResponse populateDetails(WorkspaceInvitation i, WorkspaceRole role, User user, Workspace workspace) {
        WorkspaceInvitationResponse res = workspaceMapper.toResponse(i);

        // Map inviteToken explicitly because DTO names it token
        res.setToken(i.getInviteToken());

        if (role == null && i.getRoleId() != null) {
            role = workspaceRoleRepository.findById(i.getRoleId()).orElse(null);
        }
        if (role != null) {
            res.setRoleName(role.getName());
        }

        if (i.getInvitedBy() != null) {
            User inviter = userRepository.findById(i.getInvitedBy()).orElse(null);
            if (inviter != null) {
                res.setInviterName(inviter.getUsername());
            }
        }

        return res;
    }
}
