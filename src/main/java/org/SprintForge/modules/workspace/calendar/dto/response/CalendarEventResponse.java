package org.SprintForge.modules.workspace.calendar.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEventResponse {

    private String id;
    private String title;
    private String eventType; // TASK_DUE, SPRINT_END, MILESTONE_DUE
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String color;
    private String entityUrl;
}
