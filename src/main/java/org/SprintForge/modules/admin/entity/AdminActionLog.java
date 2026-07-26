package org.SprintForge.modules.admin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "admin_action_logs")
@Getter
@Setter
public class AdminActionLog extends SoftDeleteEntity {
}
