package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.RecurringTaskFrequency;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecurringTaskResponse {

    private Long id;
    private Long taskId;
    private Long workspaceId;
    private Long projectId;
    private RecurringTaskFrequency frequency;
    private Integer intervalValue;
    private List<String> daysOfWeek;
    private Integer dayOfMonth;
    private Integer monthOfYear;
    private String cronExpression;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxOccurrences;
    private Integer generatedOccurrences;
    private LocalDateTime nextExecution;
    private LocalDateTime lastExecution;
    private String timezone;
    private Boolean paused;
    private Boolean enabled;
    private Boolean skipWeekends;
    private Boolean skipHolidays;
    private Boolean autoAssign;
    private Boolean autoNotify;
    private Long createdByUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
