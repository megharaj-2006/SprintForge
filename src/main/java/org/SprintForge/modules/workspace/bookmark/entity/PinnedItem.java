package org.SprintForge.modules.workspace.bookmark.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "pinned_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PinnedItem extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(name = "pinned_by")
    private Long pinnedBy;

    @Column(name = "position")
    private Integer position;
}

