package org.SprintForge.modules.workspace.task.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.task.dto.request.CreateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateSubtaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskSummaryResponse;
import org.SprintForge.modules.workspace.task.dto.response.SubtaskResponse;
import org.SprintForge.modules.workspace.task.entity.Task;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "sprint", ignore = true)
    @Mapping(target = "parentTask", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    @Mapping(target = "archived", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "actualHours", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "successorDependencies", ignore = true)
    @Mapping(target = "predecessorDependencies", ignore = true)
    @Mapping(target = "subtasks", ignore = true)
    @Mapping(target = "labels", ignore = true)
    Task toEntity(CreateTaskRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "sprint", ignore = true)
    @Mapping(target = "parentTask", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    @Mapping(target = "archived", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "actualHours", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "successorDependencies", ignore = true)
    @Mapping(target = "predecessorDependencies", ignore = true)
    @Mapping(target = "subtasks", ignore = true)
    @Mapping(target = "labels", ignore = true)
    Task toEntity(CreateSubtaskRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "sprint", ignore = true)
    @Mapping(target = "parentTask", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    @Mapping(target = "archived", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "successorDependencies", ignore = true)
    @Mapping(target = "predecessorDependencies", ignore = true)
    @Mapping(target = "subtasks", ignore = true)
    @Mapping(target = "labels", ignore = true)
    void updateEntity(UpdateTaskRequest dto, @MappingTarget Task entity);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "sprintId", source = "sprint.id")
    @Mapping(target = "parentTaskId", source = "parentTask.id")
    TaskResponse toResponse(Task entity);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "sprintId", source = "sprint.id")
    @Mapping(target = "parentTaskId", source = "parentTask.id")
    SubtaskResponse toSubtaskResponse(Task entity);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "sprintId", source = "sprint.id")
    TaskSummaryResponse toSummaryResponse(Task entity);

    List<TaskResponse> toResponseList(List<Task> entities);

    List<SubtaskResponse> toSubtaskResponseList(List<Task> entities);
}
