package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.RecurringTaskFrequency;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRecurringTaskRequest {

    @NotNull(message = "Frequency is required")
    private RecurringTaskFrequency frequency;

    private Integer intervalValue;

    private List<String> daysOfWeek;

    private Integer dayOfMonth;

    private Integer monthOfYear;

    private String cronExpression;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    private Integer maxOccurrences;

    private String timezone;

    private Boolean skipWeekends;

    private Boolean skipHolidays;

    private Boolean autoAssign;

    private Boolean autoNotify;
}
