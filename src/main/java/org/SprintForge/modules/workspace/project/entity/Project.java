package org.SprintForge.modules.workspace.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectStatusType;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectVisibility;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "project_key", nullable = false)
    private String projectKey;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "icon")
    private String icon;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "color")
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    private ProjectVisibility visibility = ProjectVisibility.WORKSPACE;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProjectStatusType status = ProjectStatusType.PLANNING;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "target_end_date")
    private LocalDate targetEndDate;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "progress_percentage")
    private Double progressPercentage = 0.0;

    @Column(name = "budget")
    private Double budget;

    @Column(name = "currency")
    private String currency = "USD";

    @Column(name = "estimated_hours")
    private Double estimatedHours;

    @Column(name = "logged_hours")
    private Double loggedHours = 0.0;

    @Column(name = "is_template")
    private Boolean isTemplate = false;

    @Column(name = "is_archived")
    private Boolean isArchived = false;
}
