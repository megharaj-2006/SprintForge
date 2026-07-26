package org.SprintForge.modules.workspace.customfield.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.customfield.entity.enums.CustomFieldType;

@Entity
@Table(name = "custom_fields")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomField extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CustomFieldType type = CustomFieldType.TEXT;

    @Column(name = "options", columnDefinition = "TEXT")
    private String options;

    @Column(name = "required")
    private Boolean required = false;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;
}

