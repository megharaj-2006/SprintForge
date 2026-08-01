package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class PreviewOccurrencesRequest {

    private RecurringTaskFrequency frequency;

    private Integer intervalValue;

    private List<String> daysOfWeek;

    private Integer dayOfMonth;

    private Integer monthOfYear;

    private LocalDate startDate;

    private LocalDate endDate;

    @Min(1)
    @Max(50)
    @Builder.Default
    private Integer count = 10;
}
