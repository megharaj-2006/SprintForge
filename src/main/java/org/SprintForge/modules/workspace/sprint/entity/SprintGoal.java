package org.SprintForge.modules.workspace.sprint.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "sprint_goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SprintGoal extends SoftDeleteEntity {

    @Column(name = "sprint_id", nullable = false)
    private Long sprintId;

    @Column(name = "goal_text", nullable = false, columnDefinition = "TEXT")
    private String goalText;

    @Column(name = "is_achieved", nullable = false)
    private boolean achieved = false;
}
