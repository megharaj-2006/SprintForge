package org.SprintForge.modules.workspace.sprint.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "sprint_statisticss")
@Getter
@Setter
public class SprintStatistics extends SoftDeleteEntity {
}
