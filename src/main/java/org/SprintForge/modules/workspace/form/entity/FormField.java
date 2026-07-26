package org.SprintForge.modules.workspace.form.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.form.entity.enums.FormFieldType;

@Entity
@Table(name = "form_fields")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormField extends SoftDeleteEntity {

    @Column(name = "form_id", nullable = false)
    private Long formId;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "placeholder")
    private String placeholder;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private FormFieldType type = FormFieldType.TEXT;

    @Column(name = "required")
    private Boolean required = false;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "options", columnDefinition = "TEXT")
    private String options;

    @Column(name = "validation_rules", columnDefinition = "TEXT")
    private String validationRules;

    @Column(name = "position")
    private Integer position;
}

