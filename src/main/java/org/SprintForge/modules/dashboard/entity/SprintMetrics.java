package org.SprintForge.modules.dashboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "sprint_metricss")
@Getter
@Setter
public class SprintMetrics extends SoftDeleteEntity {
}
