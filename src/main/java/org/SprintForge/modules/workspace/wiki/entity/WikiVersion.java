package org.SprintForge.modules.workspace.wiki.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "wiki_versions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WikiVersion extends SoftDeleteEntity {

    @Column(name = "wiki_page_id", nullable = false)
    private Long wikiPageId;

    @Column(name = "version_number", nullable = false)
    private Integer versionNumber;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "edited_by")
    private Long editedBy;

    @Column(name = "edited_at")
    private LocalDateTime editedAt;
}

