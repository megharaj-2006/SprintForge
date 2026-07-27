package org.SprintForge.modules.workspace.automation.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.automation.entity.AutomationRule;
import org.SprintForge.modules.workspace.automation.dto.request.AutomationRuleCreateRequest;
import org.SprintForge.modules.workspace.automation.dto.request.AutomationRuleUpdateRequest;
import org.SprintForge.modules.workspace.automation.dto.response.AutomationRuleResponse;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface AutomationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    AutomationRule toEntity(AutomationRuleCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "createdByUserId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(AutomationRuleUpdateRequest dto, @MappingTarget AutomationRule entity);

    AutomationRuleResponse toResponse(AutomationRule entity);

    List<AutomationRuleResponse> toResponseList(List<AutomationRule> entities);
}
