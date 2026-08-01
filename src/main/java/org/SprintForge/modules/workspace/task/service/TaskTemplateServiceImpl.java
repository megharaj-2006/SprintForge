package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.DuplicateResourceException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.InvalidOperationException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.task.dto.request.*;
import org.SprintForge.modules.workspace.task.dto.response.TaskTemplateDetailResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskTemplateResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskTemplateSummaryResponse;
import org.SprintForge.modules.workspace.task.entity.*;
import org.SprintForge.modules.workspace.task.event.*;
import org.SprintForge.modules.workspace.task.repository.TaskTemplateFavoriteRepository;
import org.SprintForge.modules.workspace.task.repository.TaskTemplateRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskTemplateServiceImpl implements TaskTemplateService {

    private final TaskTemplateRepository templateRepository;
    private final TaskTemplateFavoriteRepository favoriteRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public TaskTemplateResponse createTemplate(Long workspaceId, CreateTaskTemplateRequest request, Long actorId) {
        log.info("Creating task template '{}' in workspace {} by user {}", request.getName(), workspaceId, actorId);

        if (templateRepository.existsByNameAndWorkspaceIdAndIsDeletedFalse(request.getName(), workspaceId)) {
            throw new DuplicateResourceException("Template with name '" + request.getName() + "' already exists in this workspace");
        }

        TaskTemplate template = new TaskTemplate();
        template.setWorkspaceId(workspaceId);
        template.setProjectId(request.getProjectId());
        template.setName(request.getName());
        template.setDescription(request.getDescription());
        template.setDefaultStatusId(request.getDefaultStatusId());
        template.setDefaultPriorityId(request.getDefaultPriorityId());
        template.setEstimatedHours(request.getEstimatedHours());
        template.setStoryPoints(request.getStoryPoints());
        template.setIsPublic(request.getIsPublic() != null ? request.getIsPublic() : false);
        template.setCreatedByUserId(actorId);
        template.setIsArchived(false);
        template.setUsageCount(0);
        template.setFavoritedCount(0);

        mapSubElements(template, request);

        TaskTemplate saved = templateRepository.save(template);
        eventPublisher.publishEvent(new TaskTemplateCreatedEvent(saved.getId(), workspaceId, actorId));

        return mapToResponse(saved, actorId);
    }

    @Override
    @Transactional
    public TaskTemplateResponse updateTemplate(Long templateId, UpdateTaskTemplateRequest request, Long actorId) {
        TaskTemplate template = findTemplateOrThrow(templateId);
        validateTemplateEditAccess(template, actorId);

        if (Boolean.TRUE.equals(template.getIsArchived())) {
            throw new InvalidOperationException("Archived template cannot be updated");
        }

        if (request.getName() != null && !request.getName().equals(template.getName())) {
            if (templateRepository.existsByNameAndWorkspaceIdAndIsDeletedFalse(request.getName(), template.getWorkspaceId())) {
                throw new DuplicateResourceException("Template with name '" + request.getName() + "' already exists in this workspace");
            }
            template.setName(request.getName());
        }

        if (request.getDescription() != null) template.setDescription(request.getDescription());
        if (request.getDefaultStatusId() != null) template.setDefaultStatusId(request.getDefaultStatusId());
        if (request.getDefaultPriorityId() != null) template.setDefaultPriorityId(request.getDefaultPriorityId());
        if (request.getEstimatedHours() != null) template.setEstimatedHours(request.getEstimatedHours());
        if (request.getStoryPoints() != null) template.setStoryPoints(request.getStoryPoints());
        if (request.getIsPublic() != null) template.setIsPublic(request.getIsPublic());

        if (request.getChecklists() != null || request.getLabels() != null || request.getAttachments() != null || request.getCustomFields() != null || request.getWatcherUserIds() != null) {
            template.getChecklists().clear();
            template.getLabels().clear();
            template.getAttachments().clear();
            template.getCustomFields().clear();
            template.getWatchers().clear();

            CreateTaskTemplateRequest helperReq = CreateTaskTemplateRequest.builder()
                    .checklists(request.getChecklists())
                    .labels(request.getLabels())
                    .attachments(request.getAttachments())
                    .customFields(request.getCustomFields())
                    .watcherUserIds(request.getWatcherUserIds())
                    .build();
            mapSubElements(template, helperReq);
        }

        TaskTemplate updated = templateRepository.save(template);
        eventPublisher.publishEvent(new TaskTemplateUpdatedEvent(updated.getId(), actorId));

        return mapToResponse(updated, actorId);
    }

    @Override
    @Transactional
    public void deleteTemplate(Long templateId, Long actorId) {
        TaskTemplate template = findTemplateOrThrow(templateId);
        validateTemplateEditAccess(template, actorId);

        template.setDeleted(true);
        templateRepository.save(template);

        eventPublisher.publishEvent(new TaskTemplateDeletedEvent(templateId, actorId));
    }

    @Override
    @Transactional
    public TaskTemplateResponse archiveTemplate(Long templateId, Long actorId) {
        TaskTemplate template = findTemplateOrThrow(templateId);
        validateTemplateEditAccess(template, actorId);

        template.setIsArchived(true);
        TaskTemplate saved = templateRepository.save(template);

        eventPublisher.publishEvent(new TaskTemplateArchivedEvent(templateId, actorId));
        return mapToResponse(saved, actorId);
    }

    @Override
    @Transactional
    public TaskTemplateResponse restoreTemplate(Long templateId, Long actorId) {
        TaskTemplate template = findTemplateOrThrow(templateId);
        validateTemplateEditAccess(template, actorId);

        if (template.isDeleted()) {
            throw new InvalidOperationException("Deleted template cannot be restored");
        }

        template.setIsArchived(false);
        TaskTemplate saved = templateRepository.save(template);
        return mapToResponse(saved, actorId);
    }

    @Override
    @Transactional
    public TaskTemplateResponse duplicateTemplate(Long templateId, DuplicateTaskTemplateRequest request, Long actorId) {
        TaskTemplate source = findTemplateOrThrow(templateId);
        Long targetWorkspaceId = request.getTargetWorkspaceId() != null ? request.getTargetWorkspaceId() : source.getWorkspaceId();

        if (templateRepository.existsByNameAndWorkspaceIdAndIsDeletedFalse(request.getNewName(), targetWorkspaceId)) {
            throw new DuplicateResourceException("Template with name '" + request.getNewName() + "' already exists in target workspace");
        }

        TaskTemplate copy = new TaskTemplate();
        copy.setWorkspaceId(targetWorkspaceId);
        copy.setProjectId(source.getProjectId());
        copy.setName(request.getNewName());
        copy.setDescription(source.getDescription());
        copy.setDefaultStatusId(source.getDefaultStatusId());
        copy.setDefaultPriorityId(source.getDefaultPriorityId());
        copy.setEstimatedHours(source.getEstimatedHours());
        copy.setStoryPoints(source.getStoryPoints());
        copy.setCreatedByUserId(actorId);
        copy.setIsPublic(source.getIsPublic());
        copy.setIsArchived(false);
        copy.setUsageCount(0);
        copy.setFavoritedCount(0);

        for (TaskTemplateChecklist c : source.getChecklists()) {
            TaskTemplateChecklist cCopy = new TaskTemplateChecklist();
            cCopy.setTaskTemplate(copy);
            cCopy.setTitle(c.getTitle());
            cCopy.setPosition(c.getPosition());
            for (TaskTemplateChecklistItem item : c.getItems()) {
                TaskTemplateChecklistItem itemCopy = new TaskTemplateChecklistItem();
                itemCopy.setChecklist(cCopy);
                itemCopy.setTitle(item.getTitle());
                itemCopy.setDescription(item.getDescription());
                itemCopy.setPosition(item.getPosition());
                cCopy.getItems().add(itemCopy);
            }
            copy.getChecklists().add(cCopy);
        }

        for (TaskTemplateLabel l : source.getLabels()) {
            TaskTemplateLabel lCopy = new TaskTemplateLabel();
            lCopy.setTaskTemplate(copy);
            lCopy.setLabelId(l.getLabelId());
            lCopy.setName(l.getName());
            lCopy.setColor(l.getColor());
            copy.getLabels().add(lCopy);
        }

        for (TaskTemplateAttachment a : source.getAttachments()) {
            TaskTemplateAttachment aCopy = new TaskTemplateAttachment();
            aCopy.setTaskTemplate(copy);
            aCopy.setFileName(a.getFileName());
            aCopy.setFileUrl(a.getFileUrl());
            aCopy.setFileSize(a.getFileSize());
            aCopy.setFileType(a.getFileType());
            copy.getAttachments().add(aCopy);
        }

        for (TaskTemplateCustomField cf : source.getCustomFields()) {
            TaskTemplateCustomField cfCopy = new TaskTemplateCustomField();
            cfCopy.setTaskTemplate(copy);
            cfCopy.setCustomFieldId(cf.getCustomFieldId());
            cfCopy.setFieldName(cf.getFieldName());
            cfCopy.setFieldValue(cf.getFieldValue());
            copy.getCustomFields().add(cfCopy);
        }

        for (TaskTemplateWatcher w : source.getWatchers()) {
            TaskTemplateWatcher wCopy = new TaskTemplateWatcher();
            wCopy.setTaskTemplate(copy);
            wCopy.setUserId(w.getUserId());
            copy.getWatchers().add(wCopy);
        }

        TaskTemplate savedCopy = templateRepository.save(copy);
        eventPublisher.publishEvent(new TaskTemplateCreatedEvent(savedCopy.getId(), targetWorkspaceId, actorId));

        return mapToResponse(savedCopy, actorId);
    }

    @Override
    @Transactional
    public TaskTemplateResponse favoriteTemplate(Long templateId, Long actorId) {
        TaskTemplate template = findTemplateOrThrow(templateId);
        Optional<TaskTemplateFavorite> existing = favoriteRepository.findByUserIdAndTaskTemplateIdAndIsDeletedFalse(actorId, templateId);

        boolean isFavorited;
        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
            template.setFavoritedCount(Math.max(0, template.getFavoritedCount() - 1));
            isFavorited = false;
        } else {
            TaskTemplateFavorite favorite = new TaskTemplateFavorite(actorId, templateId);
            favoriteRepository.save(favorite);
            template.setFavoritedCount(template.getFavoritedCount() + 1);
            isFavorited = true;
        }

        TaskTemplate saved = templateRepository.save(template);
        eventPublisher.publishEvent(new TaskTemplateFavoritedEvent(templateId, actorId, isFavorited));

        return mapToResponse(saved, actorId);
    }

    @Override
    @Transactional
    public TaskTemplateResponse shareTemplate(Long templateId, ShareTemplateRequest request, Long actorId) {
        TaskTemplate template = findTemplateOrThrow(templateId);
        validateTemplateEditAccess(template, actorId);

        template.setIsPublic(request.getIsPublic());
        TaskTemplate saved = templateRepository.save(template);

        return mapToResponse(saved, actorId);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskTemplateResponse getTemplateById(Long templateId, Long actorId) {
        TaskTemplate template = findTemplateOrThrow(templateId);
        return mapToResponse(template, actorId);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskTemplateDetailResponse getTemplateDetails(Long templateId, Long actorId) {
        TaskTemplate template = findTemplateOrThrow(templateId);
        TaskTemplateResponse res = mapToResponse(template, actorId);

        return TaskTemplateDetailResponse.builder()
                .template(res)
                .checklistCount(template.getChecklists().size())
                .labelCount(template.getLabels().size())
                .attachmentCount(template.getAttachments().size())
                .customFieldCount(template.getCustomFields().size())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskTemplateSummaryResponse> getWorkspaceTemplates(Long workspaceId, Long actorId) {
        List<TaskTemplate> templates = templateRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId);
        return templates.stream().map(t -> mapToSummary(t, actorId)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskTemplateSummaryResponse> getProjectTemplates(Long projectId, Long actorId) {
        List<TaskTemplate> templates = templateRepository.findByProjectIdAndIsDeletedFalse(projectId);
        return templates.stream().map(t -> mapToSummary(t, actorId)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskTemplateSummaryResponse> getPopularTemplates(Long workspaceId, int limit, Long actorId) {
        List<TaskTemplate> templates = templateRepository.findPopularTemplates(workspaceId, PageRequest.of(0, limit));
        return templates.stream().map(t -> mapToSummary(t, actorId)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskTemplateSummaryResponse> searchTemplates(Long workspaceId, String query, Long actorId) {
        List<TaskTemplate> templates = templateRepository.searchTemplates(workspaceId, query);
        return templates.stream().map(t -> mapToSummary(t, actorId)).collect(Collectors.toList());
    }

    private TaskTemplate findTemplateOrThrow(Long templateId) {
        TaskTemplate template = templateRepository.findById(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("Task template not found with ID: " + templateId));
        if (template.isDeleted()) {
            throw new ResourceNotFoundException("Task template not found with ID: " + templateId);
        }
        return template;
    }

    private void validateTemplateEditAccess(TaskTemplate template, Long actorId) {
        if (actorId != null && !actorId.equals(template.getCreatedByUserId())) {
            // Future extension: Check if user is Workspace Admin
        }
    }

    private void mapSubElements(TaskTemplate template, CreateTaskTemplateRequest request) {
        if (request.getChecklists() != null) {
            for (CreateTaskTemplateRequest.ChecklistDTO cDto : request.getChecklists()) {
                TaskTemplateChecklist checklist = new TaskTemplateChecklist();
                checklist.setTaskTemplate(template);
                checklist.setTitle(cDto.getTitle());
                checklist.setPosition(cDto.getPosition());
                if (cDto.getItems() != null) {
                    for (CreateTaskTemplateRequest.ChecklistItemDTO itemDto : cDto.getItems()) {
                        TaskTemplateChecklistItem item = new TaskTemplateChecklistItem();
                        item.setChecklist(checklist);
                        item.setTitle(itemDto.getTitle());
                        item.setDescription(itemDto.getDescription());
                        item.setPosition(itemDto.getPosition());
                        checklist.getItems().add(item);
                    }
                }
                template.getChecklists().add(checklist);
            }
        }

        if (request.getLabels() != null) {
            for (CreateTaskTemplateRequest.LabelDTO lDto : request.getLabels()) {
                TaskTemplateLabel label = new TaskTemplateLabel();
                label.setTaskTemplate(template);
                label.setLabelId(lDto.getLabelId());
                label.setName(lDto.getName());
                label.setColor(lDto.getColor());
                template.getLabels().add(label);
            }
        }

        if (request.getAttachments() != null) {
            for (CreateTaskTemplateRequest.AttachmentDTO aDto : request.getAttachments()) {
                TaskTemplateAttachment attachment = new TaskTemplateAttachment();
                attachment.setTaskTemplate(template);
                attachment.setFileName(aDto.getFileName());
                attachment.setFileUrl(aDto.getFileUrl());
                attachment.setFileSize(aDto.getFileSize());
                attachment.setFileType(aDto.getFileType());
                template.getAttachments().add(attachment);
            }
        }

        if (request.getCustomFields() != null) {
            for (CreateTaskTemplateRequest.CustomFieldDTO cfDto : request.getCustomFields()) {
                TaskTemplateCustomField cf = new TaskTemplateCustomField();
                cf.setTaskTemplate(template);
                cf.setCustomFieldId(cfDto.getCustomFieldId());
                cf.setFieldName(cfDto.getFieldName());
                cf.setFieldValue(cfDto.getFieldValue());
                template.getCustomFields().add(cf);
            }
        }

        if (request.getWatcherUserIds() != null) {
            for (Long uid : request.getWatcherUserIds()) {
                TaskTemplateWatcher watcher = new TaskTemplateWatcher();
                watcher.setTaskTemplate(template);
                watcher.setUserId(uid);
                template.getWatchers().add(watcher);
            }
        }
    }

    private TaskTemplateResponse mapToResponse(TaskTemplate template, Long actorId) {
        boolean isFav = actorId != null && favoriteRepository.existsByUserIdAndTaskTemplateIdAndIsDeletedFalse(actorId, template.getId());

        List<TaskTemplateResponse.ChecklistResponse> checklists = template.getChecklists().stream().map(c ->
                TaskTemplateResponse.ChecklistResponse.builder()
                        .id(c.getId())
                        .title(c.getTitle())
                        .position(c.getPosition())
                        .items(c.getItems().stream().map(i ->
                                TaskTemplateResponse.ChecklistItemResponse.builder()
                                        .id(i.getId())
                                        .title(i.getTitle())
                                        .description(i.getDescription())
                                        .position(i.getPosition())
                                        .build()).collect(Collectors.toList()))
                        .build()).collect(Collectors.toList());

        List<TaskTemplateResponse.LabelResponse> labels = template.getLabels().stream().map(l ->
                TaskTemplateResponse.LabelResponse.builder()
                        .id(l.getId())
                        .labelId(l.getLabelId())
                        .name(l.getName())
                        .color(l.getColor())
                        .build()).collect(Collectors.toList());

        List<TaskTemplateResponse.AttachmentResponse> attachments = template.getAttachments().stream().map(a ->
                TaskTemplateResponse.AttachmentResponse.builder()
                        .id(a.getId())
                        .fileName(a.getFileName())
                        .fileUrl(a.getFileUrl())
                        .fileSize(a.getFileSize())
                        .fileType(a.getFileType())
                        .build()).collect(Collectors.toList());

        List<TaskTemplateResponse.CustomFieldResponse> customFields = template.getCustomFields().stream().map(cf ->
                TaskTemplateResponse.CustomFieldResponse.builder()
                        .id(cf.getId())
                        .customFieldId(cf.getCustomFieldId())
                        .fieldName(cf.getFieldName())
                        .fieldValue(cf.getFieldValue())
                        .build()).collect(Collectors.toList());

        List<Long> watchers = template.getWatchers().stream().map(TaskTemplateWatcher::getUserId).collect(Collectors.toList());

        return TaskTemplateResponse.builder()
                .id(template.getId())
                .workspaceId(template.getWorkspaceId())
                .projectId(template.getProjectId())
                .name(template.getName())
                .description(template.getDescription())
                .defaultStatusId(template.getDefaultStatusId())
                .defaultPriorityId(template.getDefaultPriorityId())
                .estimatedHours(template.getEstimatedHours())
                .storyPoints(template.getStoryPoints())
                .createdByUserId(template.getCreatedByUserId())
                .isPublic(template.getIsPublic())
                .isArchived(template.getIsArchived())
                .usageCount(template.getUsageCount())
                .favoritedCount(template.getFavoritedCount())
                .isFavoritedByCurrentUser(isFav)
                .createdAt(template.getCreatedAt())
                .updatedAt(template.getUpdatedAt())
                .checklists(checklists)
                .labels(labels)
                .attachments(attachments)
                .customFields(customFields)
                .watcherUserIds(watchers)
                .build();
    }

    private TaskTemplateSummaryResponse mapToSummary(TaskTemplate template, Long actorId) {
        boolean isFav = actorId != null && favoriteRepository.existsByUserIdAndTaskTemplateIdAndIsDeletedFalse(actorId, template.getId());
        return TaskTemplateSummaryResponse.builder()
                .id(template.getId())
                .workspaceId(template.getWorkspaceId())
                .projectId(template.getProjectId())
                .name(template.getName())
                .description(template.getDescription())
                .isPublic(template.getIsPublic())
                .isArchived(template.getIsArchived())
                .usageCount(template.getUsageCount())
                .favoritedCount(template.getFavoritedCount())
                .isFavoritedByCurrentUser(isFav)
                .createdAt(template.getCreatedAt())
                .build();
    }
}
