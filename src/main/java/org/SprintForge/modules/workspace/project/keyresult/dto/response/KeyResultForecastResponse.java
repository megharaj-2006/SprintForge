package org.SprintForge.modules.workspace.project.keyresult.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyResultForecastResponse {

    private Long keyResultId;
    private String title;
    private Double currentValue;
    private Double targetValue;
    private Double progressPercentage;
    private Double runRatePerDay;
    private LocalDate estimatedCompletionDate;
    private String forecastStatus; // ON_TRACK, AT_RISK, OFF_TRACK, COMPLETED
}
