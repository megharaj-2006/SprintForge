package org.SprintForge.modules.workspace.template.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.template.entity.WorkspaceAnnouncement;
import org.SprintForge.modules.workspace.template.dto.request.AnnouncementCreateRequest;
import org.SprintForge.modules.workspace.template.dto.response.AnnouncementResponse;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface AnnouncementMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    WorkspaceAnnouncement toEntity(AnnouncementCreateRequest dto);

    AnnouncementResponse toResponse(WorkspaceAnnouncement entity);

    List<AnnouncementResponse> toResponseList(List<WorkspaceAnnouncement> entities);
}
