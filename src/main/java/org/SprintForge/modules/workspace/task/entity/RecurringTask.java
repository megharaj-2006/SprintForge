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

    @Column(name = "workspace_id")
    private Long workspaceId;

    @Column(name = "project_id")
    private Long projectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency", nullable = false)
    private RecurringTaskFrequency frequency = RecurringTaskFrequency.DAILY;

    @Column(name = "interval_value")
    private Integer intervalValue = 1;

    @Column(name = "days_of_week", columnDefinition = "TEXT")
    private String daysOfWeek; // e.g. "MONDAY,WEDNESDAY,FRIDAY"

    @Column(name = "day_of_month")
    private Integer dayOfMonth;

    @Column(name = "month_of_year")
    private Integer monthOfYear;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "max_occurrences")
    private Integer maxOccurrences;

    @Column(name = "generated_occurrences")
    private Integer generatedOccurrences = 0;

    @Column(name = "next_execution")
    private LocalDateTime nextExecution;

    @Column(name = "last_execution")
    private LocalDateTime lastExecution;

    @Column(name = "timezone")
    private String timezone = "UTC";

    @Column(name = "paused")
    private Boolean paused = false;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "skip_weekends")
    private Boolean skipWeekends = false;

    @Column(name = "skip_holidays")
    private Boolean skipHolidays = false;

    @Column(name = "auto_assign")
    private Boolean autoAssign = true;

    @Column(name = "auto_notify")
    private Boolean autoNotify = true;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;
}
