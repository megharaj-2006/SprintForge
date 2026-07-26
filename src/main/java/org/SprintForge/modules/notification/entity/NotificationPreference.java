package org.SprintForge.modules.notification.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "notification_preferences")
@Getter
@Setter
public class NotificationPreference extends SoftDeleteEntity {
}
