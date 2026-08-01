package org.SprintForge.modules.workspace.task.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.SprintForge.modules.workspace.task.dto.response.ChecklistResponse;
import org.SprintForge.modules.workspace.task.dto.response.ChecklistItemResponse;
import org.SprintForge.modules.workspace.task.entity.Checklist;
import org.SprintForge.modules.workspace.task.entity.ChecklistItem;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface ChecklistMapper {

    @Mapping(target = "items", source = "items")
    ChecklistResponse toResponse(Checklist entity, List<ChecklistItemResponse> items);

    @Mapping(target = "items", ignore = true)
    ChecklistResponse toResponse(Checklist entity);

    ChecklistItemResponse toResponse(ChecklistItem entity);

    List<ChecklistItemResponse> toResponseList(List<ChecklistItem> entities);
}
