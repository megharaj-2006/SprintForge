package org.SprintForge.modules.notification.mapper;

import org.mapstruct.*;
import org.SprintForge.modules.notification.entity.Notification;
import org.SprintForge.modules.notification.dto.NotificationDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    // TODO: Populate entity and DTO fields first before defining detailed mappings.

    Notification toEntity(NotificationDto dto);

    NotificationDto toDto(Notification entity);

    List<NotificationDto> toDtoList(List<Notification> entities);
}
