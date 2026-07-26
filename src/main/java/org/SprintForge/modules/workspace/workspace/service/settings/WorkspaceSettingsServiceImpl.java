package org.SprintForge.modules.workspace.workspace.service.settings;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.dto.request.WorkspaceSettingsUpdateRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceSettingsResponse;
import org.SprintForge.modules.workspace.workspace.entity.Workspace;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceRole;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceSettings;
import org.SprintForge.modules.workspace.workspace.exception.WorkspaceException;
import org.SprintForge.modules.workspace.workspace.mapper.WorkspaceMapper;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRoleRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceSettingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkspaceSettingsServiceImpl implements WorkspaceSettingsService {

    private final WorkspaceSettingsRepository workspaceSettingsRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceRoleRepository workspaceRoleRepository;
    private final WorkspaceMapper workspaceMapper;

    @Override
    @Transactional(readOnly = true)
    public WorkspaceSettingsResponse getSettings(Long workspaceId, Long actorId) {
        checkIsMember(workspaceId, actorId);
        WorkspaceSettings settings = workspaceSettingsRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId)
                .orElseThrow(() -> new WorkspaceException("Workspace settings not found for workspace ID: " + workspaceId));
        return workspaceMapper.toResponse(settings);
    }

    @Override
    @Transactional
    public WorkspaceSettingsResponse updateSettings(Long workspaceId, WorkspaceSettingsUpdateRequest request, Long actorId) {
        checkIsOwnerOrAdmin(workspaceId, actorId);
        WorkspaceSettings settings = workspaceSettingsRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId)
                .orElseThrow(() -> new WorkspaceException("Workspace settings not found for workspace ID: " + workspaceId));

        workspaceMapper.updateEntity(request, settings);
        WorkspaceSettings saved = workspaceSettingsRepository.save(settings);
        return workspaceMapper.toResponse(saved);
    }

    private void checkIsMember(Long workspaceId, Long actorId) {
        if (actorId == null) return;
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));
        if (workspace.getOwnerId().equals(actorId)) return;

        boolean exists = workspaceMemberRepository.existsByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, actorId);
        if (!exists) {
            throw new WorkspaceException("Access Denied: Actor is not a member of the workspace.");
        }
    }

    private void checkIsOwnerOrAdmin(Long workspaceId, Long actorId) {
        if (actorId == null) return;
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceException("Workspace not found."));
        if (workspace.getOwnerId().equals(actorId)) return;

        WorkspaceMember member = workspaceMemberRepository.findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, actorId)
                .orElseThrow(() -> new WorkspaceException("Access Denied: Actor is not a member of the workspace."));

        WorkspaceRole role = workspaceRoleRepository.findById(member.getRoleId())
                .orElseThrow(() -> new WorkspaceException("Role not found."));

        if (!"ADMIN".equalsIgnoreCase(role.getName())) {
            throw new WorkspaceException("Access Denied: Only Owner or Admin can perform this operation.");
        }
    }
}
