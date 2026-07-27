package org.SprintForge.modules.workspace.whiteboard.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.whiteboard.entity.Whiteboard;
import org.SprintForge.modules.workspace.whiteboard.dto.request.WhiteboardCreateRequest;
import org.SprintForge.modules.workspace.whiteboard.dto.request.WhiteboardUpdateRequest;
import org.SprintForge.modules.workspace.whiteboard.dto.response.WhiteboardResponse;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface WhiteboardMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "updatedByUserId", ignore = true)
    Whiteboard toEntity(WhiteboardCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "createdByUserId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(WhiteboardUpdateRequest dto, @MappingTarget Whiteboard entity);

    WhiteboardResponse toResponse(Whiteboard entity);

    List<WhiteboardResponse> toResponseList(List<Whiteboard> entities);
}
