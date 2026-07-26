package org.SprintForge.modules.activity.mapper;

import org.mapstruct.*;
import org.SprintForge.modules.activity.entity.ActivityLog;
import org.SprintForge.modules.activity.dto.ActivityLogDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityMapper {
    // TODO: Populate entity and DTO fields first before defining detailed mappings.

    ActivityLog toEntity(ActivityLogDto dto);

    ActivityLogDto toDto(ActivityLog entity);

    List<ActivityLogDto> toDtoList(List<ActivityLog> entities);
}
