package org.SprintForge.modules.workspace.timelog.mapper;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.timelog.entity.TimeEntry;
import org.SprintForge.modules.workspace.timelog.dto.request.TimeEntryCreateRequest;
import org.SprintForge.modules.workspace.timelog.dto.request.TimeEntryUpdateRequest;
import org.SprintForge.modules.workspace.timelog.dto.response.TimeEntryResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeEntryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    TimeEntry toEntity(TimeEntryCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "taskId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(TimeEntryUpdateRequest dto, @MappingTarget TimeEntry entity);

    TimeEntryResponse toResponse(TimeEntry entity);

    List<TimeEntryResponse> toResponseList(List<TimeEntry> entities);
}
