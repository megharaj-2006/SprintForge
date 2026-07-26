package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDate;

@Entity
@Table(name = "task_worklogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskWorklog extends SoftDeleteEntity {

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "hours_spent")
    private Double hoursSpent;

    @Column(name = "work_date")
    private LocalDate workDate;
}

