package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskScheduleResponse {

    private Long taskId;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private LocalDateTime targetDate;
    private boolean isOverdue;
    private boolean isDueToday;
}
