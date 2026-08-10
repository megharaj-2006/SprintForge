package org.SprintForge.modules.workspace.workspace.preference.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.workspace.entity.WorkspacePreference;
import org.SprintForge.modules.workspace.workspace.preference.dto.request.WorkspaceGlobalPreferenceRequest;
import org.SprintForge.modules.workspace.workspace.preference.dto.response.WorkspaceGlobalPreferenceResponse;
import org.SprintForge.modules.workspace.workspace.repository.WorkspacePreferenceRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspacePreferenceAdminServiceImpl implements WorkspacePreferenceAdminService {

    private final WorkspacePreferenceRepository workspacePreferenceRepository;
    private final WorkspaceRepository workspaceRepository;

    @Override
    @Transactional(readOnly = true)
    public WorkspaceGlobalPreferenceResponse getWorkspacePreferences(Long workspaceId) {
        validateWorkspaceExists(workspaceId);
        WorkspacePreference preference = workspacePreferenceRepository.findByWorkspaceIdAndUserIdIsNull(workspaceId)
                .orElseGet(() -> WorkspacePreference.builder().workspaceId(workspaceId).build());
        return mapToResponse(preference);
    }

    @Override
    @Transactional
    public WorkspaceGlobalPreferenceResponse updateWorkspacePreferences(Long workspaceId, WorkspaceGlobalPreferenceRequest request, Long actorId) {
        validateWorkspaceExists(workspaceId);
        WorkspacePreference preference = workspacePreferenceRepository.findByWorkspaceIdAndUserIdIsNull(workspaceId)
                .orElseGet(() -> WorkspacePreference.builder().workspaceId(workspaceId).build());

        if (request.getTimezone() != null) preference.setTimezone(request.getTimezone());
        if (request.getLanguage() != null) preference.setLanguage(request.getLanguage());
        if (request.getDateFormat() != null) preference.setDateFormat(request.getDateFormat());
        if (request.getTimeFormat() != null) preference.setTimeFormat(request.getTimeFormat());
        if (request.getWeekStartsOn() != null) preference.setWeekStartsOn(request.getWeekStartsOn());
        if (request.getWorkingDays() != null) preference.setWorkingDays(String.join(",", request.getWorkingDays()));
        if (request.getBusinessHoursStart() != null) preference.setBusinessHoursStart(request.getBusinessHoursStart());
        if (request.getBusinessHoursEnd() != null) preference.setBusinessHoursEnd(request.getBusinessHoursEnd());

        WorkspacePreference saved = workspacePreferenceRepository.save(preference);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public void resetWorkspacePreferences(Long workspaceId, Long actorId) {
        validateWorkspaceExists(workspaceId);
        workspacePreferenceRepository.findByWorkspaceIdAndUserIdIsNull(workspaceId)
                .ifPresent(p -> {
                    p.setTimezone("UTC");
                    p.setLanguage("en");
                    p.setDateFormat("YYYY-MM-DD");
                    p.setTimeFormat("24H");
                    p.setWeekStartsOn("MONDAY");
                    p.setWorkingDays("MON,TUE,WED,THU,FRI");
                    p.setBusinessHoursStart("09:00");
                    p.setBusinessHoursEnd("17:00");
                    workspacePreferenceRepository.save(p);
                });
    }

    private void validateWorkspaceExists(Long workspaceId) {
        if (!workspaceRepository.existsById(workspaceId)) {
            throw new ResourceNotFoundException("Workspace not found with id: " + workspaceId);
        }
    }

    private WorkspaceGlobalPreferenceResponse mapToResponse(WorkspacePreference entity) {
        List<String> workingDays = entity.getWorkingDays() != null && !entity.getWorkingDays().isEmpty() 
            ? Arrays.asList(entity.getWorkingDays().split(",")) 
            : List.of();
            
        return WorkspaceGlobalPreferenceResponse.builder()
                .workspaceId(entity.getWorkspaceId())
                .timezone(entity.getTimezone())
                .language(entity.getLanguage())
                .dateFormat(entity.getDateFormat())
                .timeFormat(entity.getTimeFormat())
                .weekStartsOn(entity.getWeekStartsOn())
                .workingDays(workingDays)
                .businessHoursStart(entity.getBusinessHoursStart())
                .businessHoursEnd(entity.getBusinessHoursEnd())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
