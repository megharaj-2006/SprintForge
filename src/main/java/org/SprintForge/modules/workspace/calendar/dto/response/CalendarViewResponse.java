package org.SprintForge.modules.workspace.calendar.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarViewResponse {

    private String viewMode; // DAY, WEEK, MONTH, AGENDA
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalEvents;
    private List<CalendarEventResponse> events;
}
