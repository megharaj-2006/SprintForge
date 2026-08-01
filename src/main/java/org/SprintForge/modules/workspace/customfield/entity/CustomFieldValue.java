package org.SprintForge.modules.workspace.customfield.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.task.entity.Task;

@Entity
@Table(name = "custom_field_values")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomFieldValue extends SoftDeleteEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_field_id", nullable = false)
    private CustomField customField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "field_value", columnDefinition = "TEXT")
    private String value;
}

