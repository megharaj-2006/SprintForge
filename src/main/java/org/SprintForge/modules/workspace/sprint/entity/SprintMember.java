package org.SprintForge.modules.workspace.sprint.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "sprint_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SprintMember extends SoftDeleteEntity {

    @Column(name = "sprint_id", nullable = false)
    private Long sprintId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;
}

