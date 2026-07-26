package org.SprintForge.modules.workspace.sprint.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "sprint_goals")
@Getter
@Setter
public class SprintGoal extends SoftDeleteEntity {
}
