package org.SprintForge.modules.workspace.sprint.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "sprint_retrospectives")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SprintRetrospective extends SoftDeleteEntity {

    @Column(name = "sprint_id", nullable = false)
    private Long sprintId;

    @Column(name = "what_went_well", columnDefinition = "TEXT")
    private String whatWentWell;

    @Column(name = "what_can_improve", columnDefinition = "TEXT")
    private String whatCanImprove;

    @Column(name = "action_items", columnDefinition = "TEXT")
    private String actionItems;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;
}

