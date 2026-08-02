package org.SprintForge.modules.workspace.project.governance.document.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity(name = "GovernanceDocumentFolder")
@Table(name = "document_folders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentFolder extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "parent_id")
    private Long parentId;
}
