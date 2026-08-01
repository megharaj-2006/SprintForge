package org.SprintForge.modules.workspace.task.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.SprintForge.modules.workspace.task.dto.response.TaskWatcherResponse;
import org.SprintForge.modules.workspace.task.dto.response.WatcherSummaryResponse;
import org.SprintForge.modules.workspace.task.entity.TaskWatcher;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface TaskWatcherMapper {

    @Mapping(target = "username", source = "username")
    TaskWatcherResponse toResponse(TaskWatcher entity, String username);

    @Mapping(target = "username", source = "username")
    WatcherSummaryResponse toSummaryResponse(TaskWatcher entity, String username);
}
