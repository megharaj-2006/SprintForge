package org.SprintForge.modules.workspace.project.service.member;

import lombok.RequiredArgsConstructor;
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
import org.SprintForge.modules.workspace.workspace.entity.Workspace;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRoleRepository projectRoleRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final UserRepository userRepository;
    private final ProjectPermissionService projectPermissionService;
    private final ProjectMapper projectMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public ProjectMemberResponse addMember(Long projectId, AddProjectMemberRequest request, Long actorId) {
        // 1. Project exists
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        // 2. Workspace active
        Workspace workspace = workspaceRepository.findById(project.getWorkspaceId())
                .filter(w -> !w.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found."));

        if (workspace.isArchived()) {
            throw new BusinessRuleException("Cannot add project members in an archived workspace.");
        }

        // 3. Requester has permission
        if (!projectPermissionService.canManageMembers(projectId, actorId)) {
            throw new ForbiddenException("Access Denied: You do not have permission to manage members of this project.");
        }

        // 4. Workspace member exists and is active
        WorkspaceMember wsMember = workspaceMemberRepository.findById(request.getWorkspaceMemberId())
                .filter(m -> !m.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member not found."));

        if (wsMember.getStatus() != WorkspaceMemberStatus.ACTIVE) {
            throw new BusinessRuleException("Cannot add an inactive workspace member to the project.");
        }

        // 5. Not already project member
        if (projectMemberRepository.existsByProjectIdAndWorkspaceMemberIdAndIsDeletedFalse(projectId, request.getWorkspaceMemberId())) {
            throw new ConflictException("User is already a member of this project.");
        }

        // Find/Create ProjectRole
        ProjectRole role = getOrCreateProjectRole(projectId, request.getRoleName());

        ProjectMember projectMember = new ProjectMember();
        projectMember.setProjectId(projectId);
        projectMember.setWorkspaceMemberId(request.getWorkspaceMemberId());
        projectMember.setRoleId(role.getId());
        projectMember.setJoinedAt(LocalDateTime.now());
        projectMember.setAddedBy(actorId);
        projectMember.setStatus(ProjectMemberStatus.ACTIVE);
        projectMember.setFavorite(false);
        projectMember.setNotificationsEnabled(true);

        ProjectMember saved = projectMemberRepository.save(projectMember);

        eventPublisher.publishEvent(new ProjectMemberAddedEvent(projectId, request.getWorkspaceMemberId(), actorId, LocalDateTime.now()));

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public void removeMember(Long projectId, Long memberId, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found."));

        if (!projectPermissionService.canManageMembers(projectId, actorId)) {
            throw new ForbiddenException("Access Denied: You do not have permission to remove members of this project.");
        }

        ProjectMember member = projectMemberRepository.findById(memberId)
                .filter(m -> !m.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project member not found."));

        // Cannot remove project owner
        WorkspaceMember wsMember = workspaceMemberRepository.findById(member.getWorkspaceMemberId()).orElse(null);
        if (wsMember != null && wsMember.getUserId().equals(project.getOwnerId())) {
            throw new BusinessRuleException("Cannot remove project owner from the project. Transfer ownership first.");
        }

        member.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        member.setStatus(ProjectMemberStatus.REMOVED);
        projectMemberRepository.save(member);

        eventPublisher.publishEvent(new ProjectMemberRemovedEvent(projectId, member.getWorkspaceMemberId(), actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public void leaveProject(Long projectId, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found."));

        // Owner cannot leave
        if (actorId.equals(project.getOwnerId())) {
            throw new BusinessRuleException("Project owner cannot leave the project. Transfer ownership first.");
        }

        WorkspaceMember wsMember = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(project.getWorkspaceId(), actorId)
                .orElseThrow(() -> new ForbiddenException("User is not a member of this workspace."));

        ProjectMember member = projectMemberRepository
                .findByProjectIdAndWorkspaceMemberIdAndIsDeletedFalse(projectId, wsMember.getId())
                .orElseThrow(() -> new ResourceNotFoundException("You are not a member of this project."));

        member.markDeleted(actorId.toString());
        member.setStatus(ProjectMemberStatus.REMOVED);
        projectMemberRepository.save(member);

        eventPublisher.publishEvent(new ProjectMemberLeftEvent(projectId, member.getWorkspaceMemberId(), LocalDateTime.now()));
    }

    @Override
    @Transactional
    public ProjectMemberResponse changeRole(Long projectId, Long memberId, UpdateProjectMemberRoleRequest request, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found."));

        if (!projectPermissionService.canManageMembers(projectId, actorId)) {
            throw new ForbiddenException("Access Denied: You do not have permission to manage roles in this project.");
        }

        ProjectMember member = projectMemberRepository.findById(memberId)
                .filter(m -> !m.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project member not found."));

        // Validate changing role of owner
        WorkspaceMember wsMember = workspaceMemberRepository.findById(member.getWorkspaceMemberId()).orElse(null);
        if (wsMember != null && wsMember.getUserId().equals(project.getOwnerId()) && !"OWNER".equalsIgnoreCase(request.getRoleName())) {
            throw new BusinessRuleException("Cannot change the role of the project owner. Transfer ownership instead.");
        }

        ProjectRole newRole = getOrCreateProjectRole(projectId, request.getRoleName());
        Long oldRoleId = member.getRoleId();
        member.setRoleId(newRole.getId());
        ProjectMember saved = projectMemberRepository.save(member);

        eventPublisher.publishEvent(new ProjectMemberRoleChangedEvent(projectId, member.getWorkspaceMemberId(), oldRoleId, newRole.getId(), actorId, LocalDateTime.now()));

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public ProjectMemberResponse activateMember(Long projectId, Long memberId, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found."));

        if (!projectPermissionService.canManageMembers(projectId, actorId)) {
            throw new ForbiddenException("Access Denied: You do not have permission to activate members.");
        }

        ProjectMember member = projectMemberRepository.findById(memberId)
                .filter(m -> !m.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project member not found."));

        member.setStatus(ProjectMemberStatus.ACTIVE);
        ProjectMember saved = projectMemberRepository.save(member);

        eventPublisher.publishEvent(new ProjectMemberActivatedEvent(projectId, member.getWorkspaceMemberId(), actorId, LocalDateTime.now()));

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public ProjectMemberResponse deactivateMember(Long projectId, Long memberId, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found."));

        if (!projectPermissionService.canManageMembers(projectId, actorId)) {
            throw new ForbiddenException("Access Denied: You do not have permission to deactivate members.");
        }

        ProjectMember member = projectMemberRepository.findById(memberId)
                .filter(m -> !m.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project member not found."));

        // Cannot deactivate owner
        WorkspaceMember wsMember = workspaceMemberRepository.findById(member.getWorkspaceMemberId()).orElse(null);
        if (wsMember != null && wsMember.getUserId().equals(project.getOwnerId())) {
            throw new BusinessRuleException("Cannot deactivate the project owner.");
        }

        member.setStatus(ProjectMemberStatus.INACTIVE);
        ProjectMember saved = projectMemberRepository.save(member);

        eventPublisher.publishEvent(new ProjectMemberDeactivatedEvent(projectId, member.getWorkspaceMemberId(), actorId, LocalDateTime.now()));

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectMemberResponse> getMembers(Long projectId, Long actorId) {
        checkAccess(projectId, actorId);

        List<ProjectMember> members = projectMemberRepository.findByProjectIdAndIsDeletedFalse(projectId);
        return members.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectMemberResponse> getAdmins(Long projectId, Long actorId) {
        checkAccess(projectId, actorId);

        List<ProjectMember> members = projectMemberRepository.findByProjectIdAndIsDeletedFalse(projectId);
        return members.stream()
                .filter(m -> {
                    if (m.getRoleId() == null) return false;
                    ProjectRole role = projectRoleRepository.findById(m.getRoleId()).orElse(null);
                    return role != null && ("ADMIN".equalsIgnoreCase(role.getName()) || "OWNER".equalsIgnoreCase(role.getName()));
                })
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectMemberResponse> searchMembers(Long projectId, String query, Long actorId) {
        checkAccess(projectId, actorId);

        List<ProjectMemberResponse> allMembers = getMembers(projectId, actorId);
        if (query == null || query.isBlank()) {
            return allMembers;
        }

        String search = query.toLowerCase();
        return allMembers.stream()
                .filter(m -> (m.getUserName() != null && m.getUserName().toLowerCase().contains(search))
                        || (m.getUserEmail() != null && m.getUserEmail().toLowerCase().contains(search)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long countMembers(Long projectId, Long actorId) {
        checkAccess(projectId, actorId);
        return projectMemberRepository.countByProjectIdAndStatusAndIsDeletedFalse(projectId, ProjectMemberStatus.ACTIVE);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isProjectMember(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null || project.isDeleted()) {
            return false;
        }

        WorkspaceMember wsMember = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(project.getWorkspaceId(), userId)
                .orElse(null);
        if (wsMember == null) {
            return false;
        }

        return projectMemberRepository.existsByProjectIdAndWorkspaceMemberIdAndIsDeletedFalse(projectId, wsMember.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canAccessProject(Long projectId, Long userId) {
        return projectPermissionService.canViewProject(projectId, userId);
    }

    private void checkAccess(Long projectId, Long actorId) {
        if (!projectPermissionService.canViewProject(projectId, actorId)) {
            throw new ForbiddenException("Access Denied: You do not have permission to view this project.");
        }
    }

    private ProjectRole getOrCreateProjectRole(Long projectId, String roleName) {
        String nameUpper = roleName.toUpperCase();
        return projectRoleRepository.findByProjectIdAndNameAndIsDeletedFalse(projectId, nameUpper)
                .orElseGet(() -> {
                    ProjectRole newRole = new ProjectRole();
                    newRole.setProjectId(projectId);
                    newRole.setName(nameUpper);
                    newRole.setPermissions(getDefaultPermissions(nameUpper));
                    newRole.setColor(getDefaultColor(nameUpper));
                    newRole.setDescription("Default " + nameUpper + " role for project " + projectId);
                    return projectRoleRepository.save(newRole);
                });
    }

    private String getDefaultPermissions(String roleName) {
        switch (roleName.toUpperCase()) {
            case "OWNER":
                return "PROJECT_MEMBER_MANAGE,SPRINT_CREATE,PROJECT_DELETE,PROJECT_ARCHIVE,TASK_MANAGE,TASK_ASSIGN,PROJECT_VIEW";
            case "ADMIN":
                return "PROJECT_MEMBER_MANAGE,SPRINT_CREATE,TASK_MANAGE,TASK_ASSIGN,PROJECT_VIEW";
            case "MEMBER":
                return "SPRINT_CREATE,TASK_MANAGE,TASK_ASSIGN,PROJECT_VIEW";
            case "VIEWER":
            default:
                return "PROJECT_VIEW";
        }
    }

    private String getDefaultColor(String roleName) {
        switch (roleName.toUpperCase()) {
            case "OWNER":
                return "#FF4D4D";
            case "ADMIN":
                return "#FF9900";
            case "MEMBER":
                return "#33CC33";
            case "VIEWER":
            default:
                return "#999999";
        }
    }

    private ProjectMemberResponse mapToResponse(ProjectMember member) {
        ProjectMemberResponse resp = projectMapper.toResponse(member);

        workspaceMemberRepository.findById(member.getWorkspaceMemberId()).ifPresent(wm -> {
            resp.setWorkspaceMemberId(wm.getId());
            resp.setUserId(wm.getUserId());

            userRepository.findById(wm.getUserId()).ifPresent(user -> {
                resp.setUserName(user.getFullName() != null ? user.getFullName() : user.getUsername());
                resp.setUserEmail(user.getEmail());
                resp.setAvatarUrl(user.getProfilePicture());
            });
        });

        if (member.getRoleId() != null) {
            projectRoleRepository.findById(member.getRoleId()).ifPresent(role -> {
                resp.setRoleName(role.getName());
            });
        }

        return resp;
    }

    @Override
    @Transactional
    public ProjectMemberResponse toggleFavorite(Long projectId, Long actorId) {
        ProjectMember member = projectMemberRepository.findByProjectIdAndUserIdAndIsDeletedFalse(projectId, actorId)
                .or(() -> workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(
                                projectRepository.findById(projectId).map(Project::getWorkspaceId).orElse(0L), actorId)
                        .flatMap(wm -> projectMemberRepository.findByProjectIdAndWorkspaceMemberIdAndIsDeletedFalse(projectId, wm.getId())))
                .orElseThrow(() -> new ResourceNotFoundException("Project member not found for user: " + actorId));

        member.setFavorite(!Boolean.TRUE.equals(member.getFavorite()));
        ProjectMember saved = projectMemberRepository.save(member);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public ProjectMemberResponse changeAllocation(Long projectId, Long memberId, Double allocationPercentage, Long actorId) {
        ProjectMember member = projectMemberRepository.findById(memberId)
                .filter(m -> !m.isDeleted() && m.getProjectId().equals(projectId))
                .orElseThrow(() -> new ResourceNotFoundException("Project member not found with ID: " + memberId));

        member.setAllocationPercentage(allocationPercentage);
        ProjectMember saved = projectMemberRepository.save(member);
        return mapToResponse(saved);
    }
}
