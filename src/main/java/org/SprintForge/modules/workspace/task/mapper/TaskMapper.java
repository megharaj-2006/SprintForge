package org.SprintForge.modules.workspace.task.mapper;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.task.dto.request.TaskCreateRequest;
import org.SprintForge.modules.workspace.task.dto.request.TaskUpdateRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskDetailResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskSummaryResponse;
import org.SprintForge.modules.workspace.task.entity.Task;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "taskNumber", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "loggedHours", ignore = true)
    @Mapping(target = "progressPercentage", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "isArchived", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    Task toEntity(TaskCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "taskNumber", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(TaskUpdateRequest dto, @MappingTarget Task entity);

    TaskResponse toResponse(Task entity);

    @Mapping(target = "assigneeName", ignore = true)
    TaskSummaryResponse toSummaryResponse(Task entity);

    @Mapping(target = "projectName", ignore = true)
    @Mapping(target = "sprintName", ignore = true)
    @Mapping(target = "epicName", ignore = true)
    @Mapping(target = "statusName", ignore = true)
    @Mapping(target = "priorityName", ignore = true)
    @Mapping(target = "reporterName", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    @Mapping(target = "assigneeName", ignore = true)
    @Mapping(target = "assigneeAvatarUrl", ignore = true)
    @Mapping(target = "subtaskCount", ignore = true)
    @Mapping(target = "attachmentCount", ignore = true)
    @Mapping(target = "commentCount", ignore = true)
    @Mapping(target = "labels", ignore = true)
    TaskDetailResponse toDetailResponse(Task entity);

    List<TaskResponse> toResponseList(List<Task> entities);
}
