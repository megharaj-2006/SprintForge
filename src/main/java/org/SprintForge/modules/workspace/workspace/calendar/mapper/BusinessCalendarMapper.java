package org.SprintForge.modules.workspace.workspace.calendar.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;
import org.SprintForge.modules.workspace.workspace.calendar.dto.request.CreateHolidayRequest;
import org.SprintForge.modules.workspace.workspace.calendar.dto.response.HolidayResponse;
import org.SprintForge.modules.workspace.workspace.calendar.entity.BusinessHoliday;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class, builder = @org.mapstruct.Builder(disableBuilder = true))
public interface BusinessCalendarMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    BusinessHoliday toEntity(CreateHolidayRequest request);

    HolidayResponse toResponse(BusinessHoliday entity);
}
