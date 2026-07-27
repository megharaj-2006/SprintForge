package org.SprintForge.modules.activity.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.activity.entity.ActivityLog;
import org.SprintForge.modules.activity.dto.ActivityLogResponse;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface ActivityMapper {
    // TODO: Populate entity and DTO fields first before defining detailed mappings.

    ActivityLog toEntity(ActivityLogResponse dto);

    ActivityLogResponse toDto(ActivityLog entity);

    List<ActivityLogResponse> toDtoList(List<ActivityLog> entities);
}
