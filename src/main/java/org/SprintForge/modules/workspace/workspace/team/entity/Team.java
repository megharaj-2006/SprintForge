package org.SprintForge.modules.workspace.workspace.team.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "teams", indexes = {
    @Index(name = "idx_team_workspace_id", columnList = "workspace_id"),
    @Index(name = "idx_team_department_id", columnList = "department_id")
})
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String color;

    private String icon;

    @Column(name = "lead_member_id")
    private Long leadMemberId;

    @Column(name = "parent_team_id")
    private Long parentTeamId;

    @Column(name = "created_by_id")
    private Long createdById;

    @Builder.Default
    @Column(name = "is_archived", nullable = false)
    private boolean isArchived = false;
}
