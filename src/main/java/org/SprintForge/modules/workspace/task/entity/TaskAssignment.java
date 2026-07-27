package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.entity.ProjectMember;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssignment extends SoftDeleteEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_member_id", nullable = false)
    private ProjectMember projectMember;

    @Column(name = "assigned_by")
    private Long assignedBy;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;
}
