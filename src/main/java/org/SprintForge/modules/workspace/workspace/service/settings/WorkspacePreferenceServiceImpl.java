package org.SprintForge.modules.workspace.workspace.service.settings;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.dto.request.WorkspacePreferenceRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspacePreferenceResponse;
import org.SprintForge.modules.workspace.workspace.entity.WorkspacePreference;
import org.SprintForge.modules.workspace.workspace.exception.WorkspaceException;
import org.SprintForge.modules.workspace.workspace.mapper.WorkspaceMapper;
import org.SprintForge.modules.workspace.workspace.repository.WorkspacePreferenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkspacePreferenceServiceImpl implements WorkspacePreferenceService {

    private final WorkspacePreferenceRepository workspacePreferenceRepository;
    private final WorkspaceMapper workspaceMapper;

    @Override
    @Transactional(readOnly = true)
    public WorkspacePreferenceResponse getPreferences(Long workspaceId, Long userId, Long actorId) {
        checkSelf(userId, actorId);
        WorkspacePreference preference = workspacePreferenceRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, userId)
                .orElseGet(() -> createDefaultPreference(workspaceId, userId));
        return workspaceMapper.toResponse(preference);
    }

    @Override
    @Transactional
    public WorkspacePreferenceResponse updatePreferences(Long workspaceId, Long userId, WorkspacePreferenceRequest request, Long actorId) {
        checkSelf(userId, actorId);
        WorkspacePreference preference = workspacePreferenceRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(workspaceId, userId)
                .orElseGet(() -> createDefaultPreference(workspaceId, userId));

        workspaceMapper.updateEntity(request, preference);
        WorkspacePreference saved = workspacePreferenceRepository.save(preference);
        return workspaceMapper.toResponse(saved);
    }

    private WorkspacePreference createDefaultPreference(Long workspaceId, Long userId) {
        WorkspacePreference preference = WorkspacePreference.builder()
                .workspaceId(workspaceId)
                .userId(userId)
                .theme("LIGHT")
                .emailNotifications(true)
                .pushNotifications(true)
                .inAppNotifications(true)
                .sidebarCollapsed(false)
                .build();
        return workspacePreferenceRepository.save(preference);
    }

    private void checkSelf(Long userId, Long actorId) {
        if (actorId != null && !userId.equals(actorId)) {
            throw new WorkspaceException("Access Denied: You can only view or manage your own preferences.");
        }
    }
}
