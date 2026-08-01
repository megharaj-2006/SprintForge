package org.SprintForge.modules.workspace.customfield.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.customfield.entity.enums.CustomFieldType;
import org.SprintForge.modules.workspace.project.entity.Project;

@Entity
@Table(name = "custom_fields")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomField extends SoftDeleteEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "field_type", nullable = false)
    private CustomFieldType fieldType = CustomFieldType.TEXT;

    @Column(name = "options", columnDefinition = "TEXT")
    private String options;

    @Column(name = "required")
    private Boolean required = false;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "position")
    private Integer position = 0;

    @Column(name = "archived")
    private Boolean archived = false;

    @Column(name = "validation_rules", columnDefinition = "TEXT")
    private String validationRules;
}

