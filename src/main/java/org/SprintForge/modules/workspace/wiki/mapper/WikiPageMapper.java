package org.SprintForge.modules.workspace.wiki.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.wiki.entity.WikiPage;
import org.SprintForge.modules.workspace.wiki.dto.request.WikiPageCreateRequest;
import org.SprintForge.modules.workspace.wiki.dto.request.WikiPageUpdateRequest;
import org.SprintForge.modules.workspace.wiki.dto.response.WikiPageResponse;
import org.SprintForge.modules.workspace.wiki.dto.response.WikiPageSummaryResponse;
import org.SprintForge.modules.workspace.wiki.dto.response.WikiPageDetailResponse;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface WikiPageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "pageVersion", ignore = true)
    @Mapping(target = "updatedByUserId", ignore = true)
    WikiPage toEntity(WikiPageCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "pageVersion", ignore = true)
    @Mapping(target = "createdByUserId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(WikiPageUpdateRequest dto, @MappingTarget WikiPage entity);

    WikiPageResponse toResponse(WikiPage entity);

    WikiPageSummaryResponse toSummaryResponse(WikiPage entity);

    @Mapping(target = "projectName", ignore = true)
    @Mapping(target = "parentPageTitle", ignore = true)
    @Mapping(target = "createdByUserName", ignore = true)
    @Mapping(target = "updatedByUserName", ignore = true)
    @Mapping(target = "childPages", ignore = true)
    WikiPageDetailResponse toDetailResponse(WikiPage entity);

    List<WikiPageResponse> toResponseList(List<WikiPage> entities);
}
