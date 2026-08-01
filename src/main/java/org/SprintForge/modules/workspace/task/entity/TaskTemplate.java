package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskTemplate extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "default_status_id")
    private Long defaultStatusId;

    @Column(name = "default_priority_id")
    private Long defaultPriorityId;

    @Column(name = "estimated_hours")
    private Double estimatedHours;

    @Column(name = "story_points")
    private Integer storyPoints;

    @Column(name = "template_data", columnDefinition = "TEXT")
    private String templateData;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    @Column(name = "is_public")
    private Boolean isPublic = false;

    @Column(name = "is_archived")
    private Boolean isArchived = false;

    @Column(name = "usage_count")
    private Integer usageCount = 0;

    @Column(name = "favorited_count")
    private Integer favoritedCount = 0;

    @OneToMany(mappedBy = "taskTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskTemplateChecklist> checklists = new ArrayList<>();

    @OneToMany(mappedBy = "taskTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskTemplateLabel> labels = new ArrayList<>();

    @OneToMany(mappedBy = "taskTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskTemplateAttachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "taskTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskTemplateCustomField> customFields = new ArrayList<>();

    @OneToMany(mappedBy = "taskTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskTemplateWatcher> watchers = new ArrayList<>();
}
