package org.SprintForge.modules.notification.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.notification.entity.Notification;
import org.SprintForge.modules.notification.dto.NotificationResponse;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface NotificationMapper {
    // TODO: Populate entity and DTO fields first before defining detailed mappings.

    Notification toEntity(NotificationResponse dto);

    NotificationResponse toDto(Notification entity);

    List<NotificationResponse> toDtoList(List<Notification> entities);
}
