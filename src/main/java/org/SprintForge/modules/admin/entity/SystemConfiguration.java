package org.SprintForge.modules.admin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "system_configurations")
@Getter
@Setter
public class SystemConfiguration extends SoftDeleteEntity {
}
