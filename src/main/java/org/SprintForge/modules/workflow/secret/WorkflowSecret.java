package org.SprintForge.modules.workflow.secret;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.AuditableEntity;

@Entity
@Table(name = "workflow_secrets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowSecret extends AuditableEntity {

    @Column(name = "workspace_id")
    private Long workspaceId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value_encrypted", nullable = false, columnDefinition = "TEXT")
    private String valueEncrypted;

    @Column(name = "secret_type", nullable = false)
    private String secretType = "STRING";

    @Column(name = "scope", nullable = false)
    private String scope = "WORKSPACE";
}
