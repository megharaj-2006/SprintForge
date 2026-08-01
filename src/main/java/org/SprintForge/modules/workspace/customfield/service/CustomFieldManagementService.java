package org.SprintForge.modules.workspace.customfield.service;

import org.SprintForge.modules.workspace.customfield.dto.request.CreateCustomFieldRequest;
import org.SprintForge.modules.workspace.customfield.dto.request.UpdateCustomFieldRequest;
import org.SprintForge.modules.workspace.customfield.dto.response.CustomFieldResponse;

import java.util.List;

public interface CustomFieldManagementService {
    CustomFieldResponse createField(Long projectId, CreateCustomFieldRequest request, Long actorId);
    CustomFieldResponse updateField(Long fieldId, UpdateCustomFieldRequest request, Long actorId);
    CustomFieldResponse archiveField(Long fieldId, Long actorId);
    CustomFieldResponse restoreField(Long fieldId, Long actorId);
    void deleteField(Long fieldId, Long actorId);
    List<CustomFieldResponse> getProjectFields(Long projectId, Long actorId);
    List<CustomFieldResponse> searchFields(Long projectId, String query, Long actorId);
}
