package org.SprintForge.modules.workspace.workspace.team.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.workspace.team.entity.enums.TeamMemberRole;

import java.time.LocalDateTime;

@Entity
@Table(name = "team_members", uniqueConstraints = {
    @UniqueConstraint(name = "uk_team_member", columnNames = {"team_id", "member_id"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember extends SoftDeleteEntity {

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeamMemberRole role;

    @Column(name = "allocation_percentage")
    private Integer allocationPercentage;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;
}
