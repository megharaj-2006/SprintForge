package org.SprintForge.modules.workspace.project.governance.document.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.governance.document.entity.enums.DocumentFormat;

@Entity(name = "GovernanceDocument")
@Table(name = "project_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GovernanceDocument extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "folder_id")
    private Long folderId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "slug")
    private String slug;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "format", nullable = false)
    private DocumentFormat format = DocumentFormat.MARKDOWN;

    @Column(name = "version_number")
    private Integer versionNumber = 1;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "last_editor_id")
    private Long lastEditorId;

    @Column(name = "is_pinned")
    private Boolean isPinned = false;

    @Column(name = "is_favorite")
    private Boolean isFavorite = false;

    @Column(name = "is_archived")
    private Boolean isArchived = false;
}
