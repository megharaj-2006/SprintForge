package org.SprintForge.modules.workspace.task.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTaskRequest {

    private LocalDateTime startDate;

    private LocalDateTime dueDate;

    private LocalDateTime targetDate;

    private String timezone;
}
