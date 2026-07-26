package org.SprintForge.modules.workspace.search.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "search_indices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchIndex extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(name = "search_text", columnDefinition = "TEXT")
    private String searchText;

    @Column(name = "last_indexed_at")
    private LocalDateTime lastIndexedAt;
}

