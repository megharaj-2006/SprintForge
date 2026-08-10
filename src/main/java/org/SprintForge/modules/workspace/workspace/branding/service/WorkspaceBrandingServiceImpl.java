package org.SprintForge.modules.workspace.workspace.branding.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.workspace.branding.dto.request.WorkspaceBrandingUpdateRequest;
import org.SprintForge.modules.workspace.workspace.branding.dto.response.WorkspaceBrandingResponse;
import org.SprintForge.modules.workspace.workspace.branding.entity.WorkspaceBranding;
import org.SprintForge.modules.workspace.workspace.branding.mapper.WorkspaceBrandingMapper;
import org.SprintForge.modules.workspace.workspace.branding.repository.WorkspaceBrandingRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("newWorkspaceBrandingService")
@RequiredArgsConstructor
public class WorkspaceBrandingServiceImpl implements WorkspaceBrandingService {

    private final WorkspaceBrandingRepository workspaceBrandingRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceBrandingMapper workspaceBrandingMapper;

    @Override
    @Transactional(readOnly = true)
    public WorkspaceBrandingResponse getBranding(Long workspaceId) {
        validateWorkspaceExists(workspaceId);
        WorkspaceBranding branding = workspaceBrandingRepository.findByWorkspaceId(workspaceId)
                .orElseGet(() -> WorkspaceBranding.builder().workspaceId(workspaceId).build());
        return workspaceBrandingMapper.toResponse(branding);
    }

    @Override
    @Transactional
    public WorkspaceBrandingResponse updateBranding(Long workspaceId, WorkspaceBrandingUpdateRequest request, Long actorId) {
        validateWorkspaceExists(workspaceId);
        WorkspaceBranding branding = workspaceBrandingRepository.findByWorkspaceId(workspaceId)
                .orElseGet(() -> WorkspaceBranding.builder().workspaceId(workspaceId).build());
        
        workspaceBrandingMapper.updateEntity(request, branding);
        WorkspaceBranding saved = workspaceBrandingRepository.save(branding);
        return workspaceBrandingMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void resetBranding(Long workspaceId, Long actorId) {
        validateWorkspaceExists(workspaceId);
        workspaceBrandingRepository.findByWorkspaceId(workspaceId)
                .ifPresent(b -> workspaceBrandingRepository.delete(b));
    }

    private void validateWorkspaceExists(Long workspaceId) {
        if (!workspaceRepository.existsById(workspaceId)) {
            throw new ResourceNotFoundException("Workspace not found with id: " + workspaceId);
        }
    }
}
