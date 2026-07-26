package org.SprintForge.modules.activity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
public class AuditLog extends SoftDeleteEntity {
}
