package org.SprintForge.modules.workspace.workspace.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ResourceNotFoundException;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkspaceRoleServiceImpl implements WorkspaceRoleService {

    private final WorkspaceRoleRepository workspaceRoleRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspacePermissionService workspacePermissionService;
    private final WorkspaceMapper workspaceMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public WorkspaceRoleResponse createRole(WorkspaceRoleCreateRequest request, Long actorId) {
        Long workspaceId = request.getWorkspaceId();
        if (!workspacePermissionService.canManageWorkspace(workspaceId, actorId)) {
            throw new BusinessRuleException("Access Denied: You do not have permission to manage roles in this workspace.");
        }

        // Check duplicate name
        Optional<WorkspaceRole> existing = workspaceRoleRepository
                .findByWorkspaceIdAndNameAndIsDeletedFalse(workspaceId, request.getName());
        if (existing.isPresent()) {
            throw new BusinessRuleException("Role name already exists in this workspace: " + request.getName());
        }

        WorkspaceRole role = workspaceMapper.toEntity(request);
        role.setIsSystemRole(false);
        if (role.getIsDefaultRole() == null) {
            role.setIsDefaultRole(false);
        }

        if (role.getIsDefaultRole()) {
            clearDefaultRoles(workspaceId);
        }

        WorkspaceRole saved = workspaceRoleRepository.save(role);

        if (saved.getIsDefaultRole()) {
            updateWorkspaceDefaultRoleId(workspaceId, saved.getId());
        }

        eventPublisher.publishEvent(new WorkspaceRoleCreatedEvent(
                workspaceId, saved.getId(), saved.getName(), actorId, LocalDateTime.now()));

        return workspaceMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public WorkspaceRoleResponse updateRole(Long roleId, UpdateWorkspaceRoleRequest request, Long actorId) {
        WorkspaceRole role = workspaceRoleRepository.findById(roleId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace role not found."));

        Long workspaceId = role.getWorkspaceId();
        if (!workspacePermissionService.canManageWorkspace(workspaceId, actorId)) {
            throw new BusinessRuleException("Access Denied: You do not have permission to manage roles in this workspace.");
        }

        // Validate name change
        if (request.getName() != null && !request.getName().equals(role.getName())) {
            if (role.getIsSystemRole()) {
                throw new BusinessRuleException("Cannot rename system roles.");
            }
            Optional<WorkspaceRole> existing = workspaceRoleRepository
                    .findByWorkspaceIdAndNameAndIsDeletedFalse(workspaceId, request.getName());
            if (existing.isPresent()) {
                throw new BusinessRuleException("Role name already exists in this workspace: " + request.getName());
            }
            role.setName(request.getName());
        }

        if (request.getDescription() != null) {
            role.setDescription(request.getDescription());
        }
        if (request.getColor() != null) {
            role.setColor(request.getColor());
        }
        if (request.getPriority() != null) {
            role.setPriority(request.getPriority());
        }
        if (request.getPermissions() != null) {
            role.setPermissions(request.getPermissions());
        }

        if (request.getIsDefaultRole() != null && request.getIsDefaultRole() != role.getIsDefaultRole()) {
            if (role.getIsSystemRole() && "ADMIN".equalsIgnoreCase(role.getName()) && request.getIsDefaultRole()) {
                throw new BusinessRuleException("ADMIN role cannot be the default role.");
            }
            role.setIsDefaultRole(request.getIsDefaultRole());
            if (role.getIsDefaultRole()) {
                clearDefaultRoles(workspaceId);
                updateWorkspaceDefaultRoleId(workspaceId, role.getId());
            }
        }

        WorkspaceRole saved = workspaceRoleRepository.save(role);

        eventPublisher.publishEvent(new WorkspacePermissionChangedEvent(
                workspaceId, saved.getId(), actorId, LocalDateTime.now()));

        return workspaceMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteRole(Long roleId, Long actorId) {
        WorkspaceRole role = workspaceRoleRepository.findById(roleId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace role not found."));

        Long workspaceId = role.getWorkspaceId();
        if (!workspacePermissionService.canManageWorkspace(workspaceId, actorId)) {
            throw new BusinessRuleException("Access Denied: You do not have permission to manage roles in this workspace.");
        }

        if (role.getIsDefaultRole()) {
            throw new BusinessRuleException("Cannot delete the default workspace role.");
        }

        if (role.getIsSystemRole()) {
            throw new BusinessRuleException("Cannot delete system roles.");
        }

        // Get default role for reassigning members
        WorkspaceRole defaultRole = workspaceRoleRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId)
                .stream()
                .filter(WorkspaceRole::getIsDefaultRole)
                .findFirst()
                .orElseThrow(() -> new BusinessRuleException("No default role found for this workspace. Cannot delete role."));

        // Reassign members
        List<WorkspaceMember> members = workspaceMemberRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId);
        for (WorkspaceMember member : members) {
            if (role.getId().equals(member.getRoleId())) {
                member.setRoleId(defaultRole.getId());
                workspaceMemberRepository.save(member);
            }
        }

        role.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        workspaceRoleRepository.save(role);

        eventPublisher.publishEvent(new WorkspaceRoleDeletedEvent(
                workspaceId, roleId, actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public WorkspaceMemberResponse assignRole(AssignWorkspaceRoleRequest request, Long actorId) {
        Long workspaceId = request.getWorkspaceId();
        if (!workspacePermissionService.canAssignRoles(workspaceId, actorId)) {
            throw new BusinessRuleException("Access Denied: You do not have permission to assign roles in this workspace.");
        }

        WorkspaceRole role = workspaceRoleRepository.findById(request.getRoleId())
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace role not found."));

        if (!role.getWorkspaceId().equals(workspaceId)) {
            throw new BusinessRuleException("Cannot assign invalid role: Role does not belong to this workspace.");
        }

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member not found."));

        // Business rules: cannot change owner's role
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found."));
        if (workspace.getOwnerId().equals(request.getUserId())) {
            throw new BusinessRuleException("Cannot assign a different role to the workspace owner.");
        }

        // Cannot remove last admin role
        if ("ADMIN".equalsIgnoreCase(role.getName())) {
            // That's fine, we are making them admin. But what if we are removing an admin?
        } else {
            // We are changing someone's role *from* ADMIN to something else
            WorkspaceRole currentRole = workspaceRoleRepository.findById(member.getRoleId()).orElse(null);
            if (currentRole != null && "ADMIN".equalsIgnoreCase(currentRole.getName())) {
                checkLastAdminConstraint(workspaceId, request.getUserId());
            }
        }

        member.setRoleId(role.getId());
        WorkspaceMember saved = workspaceMemberRepository.save(member);

        eventPublisher.publishEvent(new WorkspaceRoleAssignedEvent(
                workspaceId, member.getUserId(), role.getId(), actorId, LocalDateTime.now()));

        return mapToMemberResponse(saved);
    }

    @Override
    @Transactional
    public WorkspaceMemberResponse removeRole(Long workspaceId, Long userId, Long actorId) {
        if (!workspacePermissionService.canAssignRoles(workspaceId, actorId)) {
            throw new BusinessRuleException("Access Denied: You do not have permission to assign roles in this workspace.");
        }

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member not found."));

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found."));
        if (workspace.getOwnerId().equals(userId)) {
            throw new BusinessRuleException("Cannot remove the role of the workspace owner.");
        }

        WorkspaceRole currentRole = workspaceRoleRepository.findById(member.getRoleId()).orElse(null);
        if (currentRole != null && "ADMIN".equalsIgnoreCase(currentRole.getName())) {
            checkLastAdminConstraint(workspaceId, userId);
        }

        WorkspaceRole defaultRole = workspaceRoleRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId)
                .stream()
                .filter(WorkspaceRole::getIsDefaultRole)
                .findFirst()
                .orElseThrow(() -> new BusinessRuleException("No default role found for this workspace."));

        member.setRoleId(defaultRole.getId());
        WorkspaceMember saved = workspaceMemberRepository.save(member);

        eventPublisher.publishEvent(new WorkspaceRoleAssignedEvent(
                workspaceId, userId, defaultRole.getId(), actorId, LocalDateTime.now()));

        return mapToMemberResponse(saved);
    }

    @Override
    @Transactional
    public WorkspaceRoleResponse duplicateRole(Long roleId, String newName, Long actorId) {
        WorkspaceRole role = workspaceRoleRepository.findById(roleId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace role not found."));

        Long workspaceId = role.getWorkspaceId();
        if (!workspacePermissionService.canManageWorkspace(workspaceId, actorId)) {
            throw new BusinessRuleException("Access Denied: You do not have permission to manage roles in this workspace.");
        }

        String finalName = newName != null && !newName.isBlank() ? newName : "Copy of " + role.getName();
        Optional<WorkspaceRole> existing = workspaceRoleRepository
                .findByWorkspaceIdAndNameAndIsDeletedFalse(workspaceId, finalName);
        if (existing.isPresent()) {
            throw new BusinessRuleException("Role name already exists in this workspace: " + finalName);
        }

        WorkspaceRole copy = new WorkspaceRole();
        copy.setWorkspaceId(workspaceId);
        copy.setName(finalName);
        copy.setDescription(role.getDescription());
        copy.setColor(role.getColor());
        copy.setPriority(role.getPriority() != null ? role.getPriority() + 1 : 1);
        copy.setIsSystemRole(false);
        copy.setIsDefaultRole(false);
        copy.setPermissions(role.getPermissions());

        WorkspaceRole saved = workspaceRoleRepository.save(copy);

        eventPublisher.publishEvent(new WorkspaceRoleCreatedEvent(
                workspaceId, saved.getId(), saved.getName(), actorId, LocalDateTime.now()));

        return workspaceMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public List<WorkspaceRoleResponse> reorderRoles(Long workspaceId, List<Long> roleIds, Long actorId) {
        if (!workspacePermissionService.canManageWorkspace(workspaceId, actorId)) {
            throw new BusinessRuleException("Access Denied: You do not have permission to manage roles in this workspace.");
        }

        List<WorkspaceRole> roles = workspaceRoleRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId);
        for (int i = 0; i < roleIds.size(); i++) {
            Long rId = roleIds.get(i);
            int priority = i + 1;
            roles.stream()
                    .filter(r -> r.getId().equals(rId))
                    .findFirst()
                    .ifPresent(r -> r.setPriority(priority));
        }

        workspaceRoleRepository.saveAll(roles);
        return workspaceMapper.toRoleResponseList(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceRoleResponse> getRoles(Long workspaceId) {
        List<WorkspaceRole> roles = workspaceRoleRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId);
        return workspaceMapper.toRoleResponseList(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkspaceRoleResponse getRole(Long roleId) {
        WorkspaceRole role = workspaceRoleRepository.findById(roleId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace role not found."));
        return workspaceMapper.toResponse(role);
    }

    @Override
    @Transactional
    public WorkspaceRoleResponse setDefaultRole(Long workspaceId, Long roleId, Long actorId) {
        if (!workspacePermissionService.canManageWorkspace(workspaceId, actorId)) {
            throw new BusinessRuleException("Access Denied: You do not have permission to manage roles in this workspace.");
        }

        WorkspaceRole role = workspaceRoleRepository.findById(roleId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace role not found."));

        if (!role.getWorkspaceId().equals(workspaceId)) {
            throw new BusinessRuleException("Workspace role does not belong to this workspace.");
        }

        if (role.getIsSystemRole() && "ADMIN".equalsIgnoreCase(role.getName())) {
            throw new BusinessRuleException("ADMIN role cannot be the default role.");
        }

        clearDefaultRoles(workspaceId);
        role.setIsDefaultRole(true);
        WorkspaceRole saved = workspaceRoleRepository.save(role);

        updateWorkspaceDefaultRoleId(workspaceId, saved.getId());

        return workspaceMapper.toResponse(saved);
    }

    private void clearDefaultRoles(Long workspaceId) {
        List<WorkspaceRole> roles = workspaceRoleRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId);
        for (WorkspaceRole r : roles) {
            if (r.getIsDefaultRole()) {
                r.setIsDefaultRole(false);
                workspaceRoleRepository.save(r);
            }
        }
    }

    private void updateWorkspaceDefaultRoleId(Long workspaceId, Long defaultRoleId) {
        Workspace w = workspaceRepository.findById(workspaceId).orElse(null);
        if (w != null) {
            w.setDefaultRoleId(defaultRoleId);
            workspaceRepository.save(w);
        }
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

    private WorkspaceMemberResponse mapToMemberResponse(WorkspaceMember member) {
        WorkspaceMemberResponse resp = workspaceMapper.toResponse(member);
        // Load roleName if possible
        if (member.getRoleId() != null) {
            workspaceRoleRepository.findById(member.getRoleId())
                    .ifPresent(r -> resp.setRoleName(r.getName()));
        }
        return resp;
    }
}
