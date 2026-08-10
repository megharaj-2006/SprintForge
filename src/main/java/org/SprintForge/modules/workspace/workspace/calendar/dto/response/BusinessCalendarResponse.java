package org.SprintForge.modules.workspace.workspace.calendar.dto.response;

import lombok.*;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BusinessCalendarResponse {
    private Long workspaceId;
    private String timezone;
    private List<String> workingDays;
    private String businessHoursStart;
    private String businessHoursEnd;
    private String weekStartsOn;
    private List<HolidayResponse> holidays;
    private int year;
}
