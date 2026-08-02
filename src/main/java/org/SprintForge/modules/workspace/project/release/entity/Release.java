package org.SprintForge.modules.workspace.project.release.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.release.entity.enums.ReleaseStatus;
import org.SprintForge.modules.workspace.project.release.entity.enums.ReleaseType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "StrategicRelease")
@Table(name = "releases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Release extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "release_version", nullable = false)
    private String releaseVersion;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "release_type", nullable = false)
    private ReleaseType releaseType = ReleaseType.MINOR;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReleaseStatus status = ReleaseStatus.PLANNING;

    @Column(name = "planned_start")
    private LocalDate plannedStart;

    @Column(name = "planned_release_date")
    private LocalDate plannedReleaseDate;

    @Column(name = "actual_release_date")
    private LocalDate actualReleaseDate;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "release_notes", columnDefinition = "TEXT")
    private String releaseNotes;

    @Column(name = "color")
    private String color = "#3B82F6";
}
