package org.SprintForge.modules.workspace.task.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;
import org.SprintForge.modules.workspace.task.dto.response.TaskHistoryResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskHistorySummaryResponse;
import org.SprintForge.modules.workspace.task.entity.TaskHistory;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface TaskHistoryMapper {

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "performedById", source = "performedBy.id")
    @Mapping(target = "performedByUsername", source = "performedBy.username")
    @Mapping(target = "performedByName", expression = "java(entity.getPerformedBy() != null ? (entity.getPerformedBy().getFullName() != null ? entity.getPerformedBy().getFullName() : entity.getPerformedBy().getUsername()) : \"System\")")
    TaskHistoryResponse toResponse(TaskHistory entity);

    @Mapping(target = "performedByName", expression = "java(entity.getPerformedBy() != null ? (entity.getPerformedBy().getFullName() != null ? entity.getPerformedBy().getFullName() : entity.getPerformedBy().getUsername()) : \"System\")")
    TaskHistorySummaryResponse toSummaryResponse(TaskHistory entity);

    List<TaskHistoryResponse> toResponseList(List<TaskHistory> entities);

    List<TaskHistorySummaryResponse> toSummaryResponseList(List<TaskHistory> entities);
}
