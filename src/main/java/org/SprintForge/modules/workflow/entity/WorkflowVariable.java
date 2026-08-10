package org.SprintForge.modules.workflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;
import org.SprintForge.modules.workflow.entity.enums.VariableScope;

@Entity
@Table(name = "workflow_variables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowVariable extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_version_id", nullable = false)
    private WorkflowVersion workflowVersion;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type = "STRING";

    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = false)
    private VariableScope scope = VariableScope.GLOBAL;

    @Column(name = "default_value", columnDefinition = "TEXT")
    private String defaultValue;

    @Column(name = "required", nullable = false)
    private Boolean required = false;
}
