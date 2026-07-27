package org.SprintForge.modules.workspace.project.service.query;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.dto.request.ProjectSearchRequest;
import org.SprintForge.modules.workspace.project.dto.response.ProjectResponse;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectVisibility;
import org.SprintForge.modules.workspace.project.mapper.ProjectMapper;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.service.WorkspacePermissionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectQueryServiceImpl implements ProjectQueryService {

    private final ProjectRepository projectRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspacePermissionService workspacePermissionService;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProject(Long id, Long actorId) {
        Project project = projectRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));

        checkAccess(project, actorId);

        return projectMapper.toResponse(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getProjects(Long workspaceId, Long actorId) {
        checkWorkspaceMember(workspaceId, actorId);

        List<Project> projects = projectRepository.findByWorkspaceIdAndIsArchivedFalseAndIsDeletedFalse(workspaceId);

        List<Project> accessible = projects.stream()
                .filter(p -> hasAccess(p, actorId))
                .collect(Collectors.toList());

        return projectMapper.toResponseList(accessible);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getArchivedProjects(Long workspaceId, Long actorId) {
        checkWorkspaceMember(workspaceId, actorId);

        List<Project> projects = projectRepository.findByWorkspaceIdAndIsArchivedTrueAndIsDeletedFalse(workspaceId);

        List<Project> accessible = projects.stream()
                .filter(p -> hasAccess(p, actorId))
                .collect(Collectors.toList());

        return projectMapper.toResponseList(accessible);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> searchProjects(ProjectSearchRequest request, Long actorId) {
        if (request.getWorkspaceId() != null) {
            checkWorkspaceMember(request.getWorkspaceId(), actorId);
        }

        Specification<Project> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("isDeleted"), false));

            if (request.getWorkspaceId() != null) {
                predicates.add(cb.equal(root.get("workspaceId"), request.getWorkspaceId()));
            }

            if (request.getQuery() != null && !request.getQuery().isBlank()) {
                String searchLike = "%" + request.getQuery().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), searchLike),
                        cb.like(cb.lower(root.get("description")), searchLike),
                        cb.like(cb.lower(root.get("projectKey")), searchLike)
                ));
            }

            if (request.getVisibility() != null) {
                predicates.add(cb.equal(root.get("visibility"), request.getVisibility()));
            }

            if (request.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), request.getStatus()));
            }

            if (request.getOwnerId() != null) {
                predicates.add(cb.equal(root.get("ownerId"), request.getOwnerId()));
            }

            if (request.getIsArchived() != null) {
                predicates.add(cb.equal(root.get("isArchived"), request.getIsArchived()));
            } else {
                predicates.add(cb.equal(root.get("isArchived"), false));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // Enforce Sort Direction
        Sort.Direction direction = Sort.Direction.DESC;
        if ("ASC".equalsIgnoreCase(request.getSortDirection())) {
            direction = Sort.Direction.ASC;
        }
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(direction, request.getSortBy())
        );

        List<Project> matched = projectRepository.findAll(spec, pageable).getContent();

        List<Project> accessible = matched.stream()
                .filter(p -> hasAccess(p, actorId))
                .collect(Collectors.toList());

        return projectMapper.toResponseList(accessible);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getRecentProjects(Long userId, int limit, Long actorId) {
        // Find projects owned by user, or where user is member of the workspace and has access
        List<Project> owned = projectRepository.findByOwnerIdAndIsDeletedFalse(userId);

        // Fetch other projects
        List<Project> recent = projectRepository.findAll().stream()
                .filter(p -> !p.isDeleted() && !p.getIsArchived())
                .filter(p -> hasAccess(p, actorId))
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .limit(limit)
                .collect(Collectors.toList());

        return projectMapper.toResponseList(recent);
    }

    private void checkWorkspaceMember(Long workspaceId, Long actorId) {
        if (actorId == null) {
            throw new ForbiddenException("Access Denied: Authentication required.");
        }
        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, actorId)
                .orElseThrow(() -> new ForbiddenException("Access Denied: You are not a member of this workspace."));

        if (member.getStatus() != WorkspaceMemberStatus.ACTIVE) {
            throw new ForbiddenException("Access Denied: Your membership in this workspace is not active.");
        }
    }

    private boolean hasAccess(Project project, Long actorId) {
        try {
            checkAccess(project, actorId);
            return true;
        } catch (ForbiddenException e) {
            return false;
        }
    }

    private void checkAccess(Project project, Long actorId) {
        if (project.getVisibility() == ProjectVisibility.PUBLIC) {
            return;
        }

        if (actorId == null) {
            throw new ForbiddenException("Access Denied: Authentication required for non-public project.");
        }

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(project.getWorkspaceId(), actorId)
                .orElseThrow(() -> new ForbiddenException("Access Denied: You are not a member of the project's workspace."));

        if (member.getStatus() != WorkspaceMemberStatus.ACTIVE) {
            throw new ForbiddenException("Access Denied: Workspace membership is not active.");
        }

        if (project.getVisibility() == ProjectVisibility.PRIVATE) {
            boolean isOwner = actorId.equals(project.getOwnerId());
            boolean isWorkspaceAdmin = workspacePermissionService.hasPermission(project.getWorkspaceId(), actorId, "PROJECT_MANAGE");
            if (!isOwner && !isWorkspaceAdmin) {
                throw new ForbiddenException("Access Denied: Private project access restricted to owner/admins.");
            }
        }
    }
}
