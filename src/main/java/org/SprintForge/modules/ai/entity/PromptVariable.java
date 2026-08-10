package org.SprintForge.modules.ai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;

@Entity
@Table(name = "prompt_variables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromptVariable extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private PromptTemplate template;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "variable_type", nullable = false)
    private String variableType = "STRING";

    @Column(name = "required", nullable = false)
    private Boolean required = true;

    @Column(name = "default_value", columnDefinition = "TEXT")
    private String defaultValue;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
