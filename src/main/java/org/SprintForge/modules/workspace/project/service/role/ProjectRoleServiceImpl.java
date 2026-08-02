package org.SprintForge.modules.workspace.project.service.role;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.dto.request.CreateProjectRoleRequest;
import org.SprintForge.modules.workspace.project.dto.response.ProjectRoleResponse;
import org.SprintForge.modules.workspace.project.entity.ProjectMember;
import org.SprintForge.modules.workspace.project.entity.ProjectRole;
import org.SprintForge.modules.workspace.project.repository.ProjectMemberRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectRoleServiceImpl implements ProjectRoleService {

    private final ProjectRoleRepository roleRepository;
    private final ProjectMemberRepository memberRepository;

    @Override
    @Transactional
    public ProjectRoleResponse createRole(Long projectId, CreateProjectRoleRequest request, Long actorId) {
        if (roleRepository.existsByProjectIdAndNameAndIsDeletedFalse(projectId, request.getName())) {
            throw new ConflictException("Role with name '" + request.getName() + "' already exists in this project.");
        }

        ProjectRole role = new ProjectRole();
        role.setProjectId(projectId);
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        role.setColor(request.getColor() != null ? request.getColor() : "#6B7280");
        if (request.getPermissions() != null) {
            role.setPermissions(String.join(",", request.getPermissions()));
        }

        ProjectRole saved = roleRepository.save(role);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectRoleResponse> getRoles(Long projectId) {
        return roleRepository.findByProjectIdAndIsDeletedFalse(projectId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectRoleResponse getRole(Long roleId) {
        ProjectRole role = roleRepository.findById(roleId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project role not found with ID: " + roleId));
        return toResponse(role);
    }

    @Override
    @Transactional
    public void deleteRole(Long roleId, Long actorId) {
        ProjectRole role = roleRepository.findById(roleId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project role not found with ID: " + roleId));
        role.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public ProjectRoleResponse assignRole(Long projectId, Long memberId, Long roleId, Long actorId) {
        ProjectMember member = memberRepository.findById(memberId)
                .filter(m -> !m.isDeleted() && m.getProjectId().equals(projectId))
                .orElseThrow(() -> new ResourceNotFoundException("Project member not found with ID: " + memberId));

        ProjectRole role = roleRepository.findById(roleId)
                .filter(r -> !r.isDeleted() && r.getProjectId().equals(projectId))
                .orElseThrow(() -> new ResourceNotFoundException("Project role not found with ID: " + roleId));

        member.setRoleId(roleId);
        memberRepository.save(member);
        return toResponse(role);
    }

    @Override
    @Transactional
    public ProjectRoleResponse cloneRole(Long roleId, String newRoleName, Long actorId) {
        ProjectRole source = roleRepository.findById(roleId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project role not found with ID: " + roleId));

        String targetName = (newRoleName != null && !newRoleName.isBlank()) ? newRoleName : "Copy of " + source.getName();
        if (roleRepository.existsByProjectIdAndNameAndIsDeletedFalse(source.getProjectId(), targetName)) {
            throw new ConflictException("Role name '" + targetName + "' already exists in this project.");
        }

        ProjectRole cloned = new ProjectRole();
        cloned.setProjectId(source.getProjectId());
        cloned.setName(targetName);
        cloned.setDescription("Cloned from " + source.getName());
        cloned.setColor(source.getColor());
        cloned.setPermissions(source.getPermissions());

        ProjectRole saved = roleRepository.save(cloned);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public ProjectRoleResponse duplicatePermissions(Long sourceRoleId, Long targetRoleId, Long actorId) {
        ProjectRole source = roleRepository.findById(sourceRoleId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Source role not found with ID: " + sourceRoleId));

        ProjectRole target = roleRepository.findById(targetRoleId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Target role not found with ID: " + targetRoleId));

        target.setPermissions(source.getPermissions());
        ProjectRole saved = roleRepository.save(target);
        return toResponse(saved);
    }

    private ProjectRoleResponse toResponse(ProjectRole role) {
        return ProjectRoleResponse.builder()
                .id(role.getId())
                .projectId(role.getProjectId())
                .name(role.getName())
                .description(role.getDescription())
                .color(role.getColor())
                .permissions(role.getPermissions())
                .build();
    }
}
