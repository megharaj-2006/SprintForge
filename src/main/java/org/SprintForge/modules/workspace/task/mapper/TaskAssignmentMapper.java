package org.SprintForge.modules.workspace.task.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.SprintForge.modules.workspace.task.dto.response.TaskAssignmentResponse;
import org.SprintForge.modules.workspace.task.entity.TaskAssignment;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface TaskAssignmentMapper {

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "projectMemberId", source = "projectMember.id")
    TaskAssignmentResponse toResponse(TaskAssignment entity);

    List<TaskAssignmentResponse> toResponseList(List<TaskAssignment> entities);
}
