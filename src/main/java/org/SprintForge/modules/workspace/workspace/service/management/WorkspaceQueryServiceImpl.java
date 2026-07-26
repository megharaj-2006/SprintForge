package org.SprintForge.modules.workspace.workspace.service.management;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.workspace.dto.request.WorkspaceSearchRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.*;
import org.SprintForge.modules.workspace.workspace.entity.Workspace;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceRole;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceSettings;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceVisibility;
import org.SprintForge.modules.workspace.workspace.exception.WorkspaceException;
import org.SprintForge.modules.workspace.workspace.mapper.WorkspaceMapper;
import org.SprintForge.modules.workspace.workspace.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceQueryServiceImpl implements WorkspaceQueryService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceRoleRepository workspaceRoleRepository;
    private final WorkspaceSettingsRepository workspaceSettingsRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final WorkspaceMapper workspaceMapper;

    @Override
    @Transactional(readOnly = true)
    public WorkspaceResponse getWorkspace(Long id, Long actorId) {
        Workspace workspace = workspaceRepository.findById(id)
                .filter(w -> !w.isDeleted())
                .orElseThrow(() -> new WorkspaceException("Workspace not found with ID: " + id));
        checkAccess(workspace, actorId);
        return workspaceMapper.toResponse(workspace);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkspaceResponse getWorkspaceBySlug(String slug, Long actorId) {
        Workspace workspace = workspaceRepository.findBySlug(slug)
                .filter(w -> !w.isDeleted())
                .orElseThrow(() -> new WorkspaceException("Workspace not found with slug: " + slug));
        checkAccess(workspace, actorId);
        return workspaceMapper.toResponse(workspace);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkspaceDetailResponse getWorkspaceDetails(Long id, Long actorId) {
        Workspace workspace = workspaceRepository.findById(id)
                .filter(w -> !w.isDeleted())
                .orElseThrow(() -> new WorkspaceException("Workspace not found with ID: " + id));
        checkAccess(workspace, actorId);

        WorkspaceDetailResponse detail = workspaceMapper.toDetailResponse(workspace);

        // Set Owner details
        userRepository.findById(workspace.getOwnerId()).ifPresent(owner -> {
            detail.setOwnerName(owner.getFullName() != null ? owner.getFullName() : owner.getUsername());
            detail.setOwnerEmail(owner.getEmail());
        });

        // Set active member count
        long activeMemberCount = workspaceMemberRepository.countByWorkspaceIdAndStatusAndIsDeletedFalse(id, WorkspaceMemberStatus.ACTIVE);
        detail.setActiveMemberCount((int) activeMemberCount);

        // Set project count
        long projectCount = projectRepository.countByWorkspaceIdAndIsDeletedFalse(id);
        detail.setProjectCount((int) projectCount);

        // Set settings
        workspaceSettingsRepository.findByWorkspaceIdAndIsDeletedFalse(id).ifPresent(settings -> {
            detail.setSettings(workspaceMapper.toResponse(settings));
        });

        // Set roles
        List<WorkspaceRole> roles = workspaceRoleRepository.findByWorkspaceIdAndIsDeletedFalse(id);
        detail.setRoles(workspaceMapper.toRoleResponseList(roles));

        return detail;
    }

    @Override
    @Transactional(readOnly = true)
    public WorkspaceSummaryResponse getWorkspaceSummary(Long id, Long actorId) {
        Workspace workspace = workspaceRepository.findById(id)
                .filter(w -> !w.isDeleted())
                .orElseThrow(() -> new WorkspaceException("Workspace not found with ID: " + id));
        checkAccess(workspace, actorId);

        WorkspaceSummaryResponse summary = workspaceMapper.toSummaryResponse(workspace);
        long memberCount = workspaceMemberRepository.countByWorkspaceIdAndStatusAndIsDeletedFalse(id, WorkspaceMemberStatus.ACTIVE);
        summary.setMemberCount((int) memberCount);

        return summary;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceResponse> listUserWorkspaces(Long userId, Long actorId) {
        // Enforce that a user can list their own workspaces, or system
        if (actorId != null && !userId.equals(actorId)) {
            throw new WorkspaceException("Access Denied: You cannot view workspaces for other users.");
        }
        List<Workspace> workspaces = workspaceRepository.findByOwnerIdAndIsDeletedFalse(userId);

        // Also add workspaces where the user is an active member
        List<WorkspaceMember> memberships = workspaceMemberRepository.findByUserIdAndIsDeletedFalse(userId);
        for (WorkspaceMember member : memberships) {
            if (member.getStatus() == WorkspaceMemberStatus.ACTIVE) {
                workspaceRepository.findById(member.getWorkspaceId())
                        .filter(w -> !w.isDeleted() && workspaces.stream().noneMatch(x -> x.getId().equals(w.getId())))
                        .ifPresent(workspaces::add);
            }
        }

        return workspaceMapper.toResponseList(workspaces);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceResponse> listArchivedWorkspaces(Long actorId) {
        // For security, only return archived workspaces where the actor is the owner or an admin
        // For simplicity, we filter the list of all archived workspaces
        List<Workspace> archived = workspaceRepository.findAll().stream()
                .filter(w -> w.isArchived() && !w.isDeleted())
                .filter(w -> {
                    if (actorId == null) return true;
                    if (w.getOwnerId().equals(actorId)) return true;
                    return workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(w.getId(), actorId)
                            .map(m -> {
                                WorkspaceRole role = workspaceRoleRepository.findById(m.getRoleId()).orElse(null);
                                return role != null && "ADMIN".equalsIgnoreCase(role.getName());
                            }).orElse(false);
                })
                .collect(Collectors.toList());

        return workspaceMapper.toResponseList(archived);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceResponse> searchWorkspaces(WorkspaceSearchRequest request, Long actorId) {
        Specification<Workspace> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("isDeleted"), false));

            if (request.getQuery() != null && !request.getQuery().isBlank()) {
                String searchLike = "%" + request.getQuery().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), searchLike),
                        cb.like(cb.lower(root.get("description")), searchLike),
                        cb.like(cb.lower(root.get("slug")), searchLike)
                ));
            }

            if (request.getVisibility() != null) {
                predicates.add(cb.equal(root.get("visibility"), request.getVisibility()));
            }

            if (Boolean.TRUE.equals(request.getIsArchived())) {
                predicates.add(cb.equal(root.get("isArchived"), true));
            } else {
                predicates.add(cb.equal(root.get("isArchived"), false));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        List<Workspace> matched = workspaceRepository.findAll(spec);
        // Filter out private workspaces that the actor doesn't have access to
        List<Workspace> accessible = matched.stream()
                .filter(w -> {
                    try {
                        checkAccess(w, actorId);
                        return true;
                    } catch (WorkspaceException e) {
                        return false;
                    }
                }).collect(Collectors.toList());

        return workspaceMapper.toResponseList(accessible);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceResponse> recentWorkspaces(Long userId, int limit, Long actorId) {
        // Enforce security
        if (actorId != null && !userId.equals(actorId)) {
            throw new WorkspaceException("Access Denied: You cannot view recent workspaces for other users.");
        }
        // Since there is no recent tracking entity yet, we return user workspaces ordered by updatedAt
        List<Workspace> workspaces = workspaceRepository.findByOwnerIdAndIsDeletedFalse(userId);
        List<WorkspaceMember> memberships = workspaceMemberRepository.findByUserIdAndIsDeletedFalse(userId);
        for (WorkspaceMember member : memberships) {
            if (member.getStatus() == WorkspaceMemberStatus.ACTIVE) {
                workspaceRepository.findById(member.getWorkspaceId())
                        .filter(w -> !w.isDeleted() && workspaces.stream().noneMatch(x -> x.getId().equals(w.getId())))
                        .ifPresent(workspaces::add);
            }
        }

        workspaces.sort((w1, w2) -> {
            if (w1.getUpdatedAt() == null && w2.getUpdatedAt() == null) return 0;
            if (w1.getUpdatedAt() == null) return 1;
            if (w2.getUpdatedAt() == null) return -1;
            return w2.getUpdatedAt().compareTo(w1.getUpdatedAt());
        });

        int resultLimit = Math.min(limit, workspaces.size());
        return workspaceMapper.toResponseList(workspaces.subList(0, resultLimit));
    }

    @Override
    @Transactional(readOnly = true)
    public WorkspaceStatisticsResponse getWorkspaceStatistics(Long id, Long actorId) {
        Workspace workspace = workspaceRepository.findById(id)
                .filter(w -> !w.isDeleted())
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));
        checkAccess(workspace, actorId);

        long projectCount = projectRepository.countByWorkspaceIdAndIsDeletedFalse(id);
        long activeMemberCount = workspaceMemberRepository.countByWorkspaceIdAndStatusAndIsDeletedFalse(id, WorkspaceMemberStatus.ACTIVE);

        // Populate statistics with mock metrics for other counts
        double storagePercent = 0.0;
        if (workspace.getStorageLimit() != null && workspace.getStorageLimit() > 0) {
            storagePercent = (double) workspace.getStorageUsed() / workspace.getStorageLimit() * 100.0;
        }

        return WorkspaceStatisticsResponse.builder()
                .workspaceId(id)
                .totalProjects(projectCount)
                .activeProjects(projectCount)
                .totalMembers(activeMemberCount)
                .activeMembers(activeMemberCount)
                .totalSprints(0L)
                .activeSprints(0L)
                .totalTasks(0L)
                .completedTasks(0L)
                .storageUsed(workspace.getStorageUsed())
                .storageLimit(workspace.getStorageLimit())
                .storagePercentageUsed(storagePercent)
                .build();
    }

    @Override
    @Transactional
    public void recalculateWorkspaceStatistics(Long id, Long actorId) {
        Workspace workspace = workspaceRepository.findById(id)
                .filter(w -> !w.isDeleted())
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));
        checkAccess(workspace, actorId);

        // Here we can run a count check on projects, sum storage, etc.
        // For simplicity:
        long projectCount = projectRepository.countByWorkspaceIdAndIsDeletedFalse(id);
        workspace.setStorageUsed(projectCount * 1024L * 1024L); // Dummy calculation: 1MB per project
        workspaceRepository.save(workspace);
    }

    private void checkAccess(Workspace workspace, Long actorId) {
        if (actorId == null) return;
        if (workspace.getVisibility() == WorkspaceVisibility.PRIVATE && !workspace.getOwnerId().equals(actorId)) {
            boolean isMember = workspaceMemberRepository.existsByWorkspaceIdAndUserIdAndIsDeletedFalse(workspace.getId(), actorId);
            if (!isMember) {
                throw new WorkspaceException("Access Denied: You do not have access to this workspace.");
            }
        }
    }
}
