package org.SprintForge.modules.workspace.bookmark.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "saved_views")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavedView extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "view_type")
    private String viewType;

    @Column(name = "filters", columnDefinition = "TEXT")
    private String filters;

    @Column(name = "sorting", columnDefinition = "TEXT")
    private String sorting;

    @Column(name = "grouping", columnDefinition = "TEXT")
    private String grouping;

    @Column(name = "layout")
    private String layout;

    @Column(name = "is_default")
    private Boolean isDefault = false;
}

