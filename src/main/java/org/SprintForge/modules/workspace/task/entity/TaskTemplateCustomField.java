package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "task_template_custom_fields")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskTemplateCustomField extends SoftDeleteEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_template_id", nullable = false)
    private TaskTemplate taskTemplate;

    @Column(name = "custom_field_id", nullable = false)
    private Long customFieldId;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field_value", columnDefinition = "TEXT")
    private String fieldValue;
}
