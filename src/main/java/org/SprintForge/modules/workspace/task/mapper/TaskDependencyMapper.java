package org.SprintForge.modules.workspace.task.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.SprintForge.modules.workspace.task.dto.response.TaskDependencyResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskDependencySummaryResponse;
import org.SprintForge.modules.workspace.task.entity.TaskDependency;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface TaskDependencyMapper {

    @Mapping(target = "predecessorTaskId", source = "predecessorTask.id")
    @Mapping(target = "successorTaskId", source = "successorTask.id")
    TaskDependencyResponse toResponse(TaskDependency entity);

    List<TaskDependencyResponse> toResponseList(List<TaskDependency> entities);

    @Mapping(target = "predecessorTaskId", source = "predecessorTask.id")
    @Mapping(target = "predecessorTitle", source = "predecessorTask.title")
    @Mapping(target = "successorTaskId", source = "successorTask.id")
    @Mapping(target = "successorTitle", source = "successorTask.title")
    TaskDependencySummaryResponse toSummaryResponse(TaskDependency entity);

    List<TaskDependencySummaryResponse> toSummaryResponseList(List<TaskDependency> entities);
}
