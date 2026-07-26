package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.task.entity.enums.RecurringTaskFrequency;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "recurring_tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecurringTask extends SoftDeleteEntity {

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency")
    private RecurringTaskFrequency frequency = RecurringTaskFrequency.DAILY;

    @Column(name = "interval_value")
    private Integer intervalValue = 1;

    @Column(name = "days_of_week", columnDefinition = "TEXT")
    private String daysOfWeek;

    @Column(name = "day_of_month")
    private Integer dayOfMonth;

    @Column(name = "month_of_year")
    private Integer monthOfYear;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "next_execution")
    private LocalDateTime nextExecution;

    @Column(name = "enabled")
    private Boolean enabled = true;
}

