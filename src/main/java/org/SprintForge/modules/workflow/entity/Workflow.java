package org.SprintForge.modules.workflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.AuditableEntity;
import org.SprintForge.modules.workflow.entity.enums.WorkflowStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workflows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Workflow extends AuditableEntity {

    @Column(name = "workspace_id")
    private Long workspaceId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "category")
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WorkflowStatus status = WorkflowStatus.DRAFT;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "published", nullable = false)
    private Boolean published = false;

    @Column(name = "archived", nullable = false)
    private Boolean archived = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_version_id")
    private WorkflowVersion currentVersion;

    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WorkflowVersion> versions = new ArrayList<>();
}
