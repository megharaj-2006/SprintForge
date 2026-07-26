package org.SprintForge.modules.workspace.customfield.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "custom_field_values")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomFieldValue extends SoftDeleteEntity {

    @Column(name = "custom_field_id", nullable = false)
    private Long customFieldId;

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "field_value", columnDefinition = "TEXT")
    private String value;
}

