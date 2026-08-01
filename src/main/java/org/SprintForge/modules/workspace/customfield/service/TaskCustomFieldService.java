package org.SprintForge.modules.workspace.customfield.service;

import org.SprintForge.modules.workspace.customfield.dto.request.AssignCustomFieldRequest;
import org.SprintForge.modules.workspace.customfield.dto.request.UpdateCustomFieldValueRequest;
import org.SprintForge.modules.workspace.customfield.dto.response.TaskCustomFieldResponse;

import java.util.List;

public interface TaskCustomFieldService {
    TaskCustomFieldResponse assignValue(Long taskId, AssignCustomFieldRequest request, Long actorId);
    TaskCustomFieldResponse updateValue(Long taskId, Long fieldId, UpdateCustomFieldValueRequest request, Long actorId);
    void removeValue(Long taskId, Long fieldId, Long actorId);
    List<TaskCustomFieldResponse> getTaskFields(Long taskId, Long actorId);
    void validateValues(Long taskId, List<AssignCustomFieldRequest> requests);
}
