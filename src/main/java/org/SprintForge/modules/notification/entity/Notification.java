package org.SprintForge.modules.notification.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity(name = "GlobalNotification")
@Table(name = "global_notifications")
@Getter
@Setter
public class Notification extends SoftDeleteEntity {
}
