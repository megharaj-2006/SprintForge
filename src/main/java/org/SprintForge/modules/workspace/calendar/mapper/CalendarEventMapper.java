package org.SprintForge.modules.workspace.calendar.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.calendar.entity.CalendarEvent;
import org.SprintForge.modules.workspace.calendar.dto.request.CalendarEventCreateRequest;
import org.SprintForge.modules.workspace.calendar.dto.request.CalendarEventUpdateRequest;
import org.SprintForge.modules.workspace.calendar.dto.response.CalendarEventResponse;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface CalendarEventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    CalendarEvent toEntity(CalendarEventCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "taskId", ignore = true)
    @Mapping(target = "createdByUserId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(CalendarEventUpdateRequest dto, @MappingTarget CalendarEvent entity);

    CalendarEventResponse toResponse(CalendarEvent entity);

    List<CalendarEventResponse> toResponseList(List<CalendarEvent> entities);
}
