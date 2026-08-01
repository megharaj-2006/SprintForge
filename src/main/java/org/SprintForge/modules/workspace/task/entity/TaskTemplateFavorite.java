package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "task_template_favorites", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "task_template_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskTemplateFavorite extends SoftDeleteEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "task_template_id", nullable = false)
    private Long taskTemplateId;
}
