package org.SprintForge.modules.workspace.task.service;

import org.SprintForge.modules.workspace.task.dto.request.*;
import org.SprintForge.modules.workspace.task.dto.response.TaskTemplateDetailResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskTemplateResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskTemplateSummaryResponse;

import java.util.List;

public interface TaskTemplateService {

    TaskTemplateResponse createTemplate(Long workspaceId, CreateTaskTemplateRequest request, Long actorId);

    TaskTemplateResponse updateTemplate(Long templateId, UpdateTaskTemplateRequest request, Long actorId);

    void deleteTemplate(Long templateId, Long actorId);

    TaskTemplateResponse archiveTemplate(Long templateId, Long actorId);

    TaskTemplateResponse restoreTemplate(Long templateId, Long actorId);

    TaskTemplateResponse duplicateTemplate(Long templateId, DuplicateTaskTemplateRequest request, Long actorId);

    TaskTemplateResponse favoriteTemplate(Long templateId, Long actorId);

    TaskTemplateResponse shareTemplate(Long templateId, ShareTemplateRequest request, Long actorId);

    TaskTemplateResponse getTemplateById(Long templateId, Long actorId);

    TaskTemplateDetailResponse getTemplateDetails(Long templateId, Long actorId);

    List<TaskTemplateSummaryResponse> getWorkspaceTemplates(Long workspaceId, Long actorId);

    List<TaskTemplateSummaryResponse> getProjectTemplates(Long projectId, Long actorId);

    List<TaskTemplateSummaryResponse> getPopularTemplates(Long workspaceId, int limit, Long actorId);

    List<TaskTemplateSummaryResponse> searchTemplates(Long workspaceId, String query, Long actorId);
}
