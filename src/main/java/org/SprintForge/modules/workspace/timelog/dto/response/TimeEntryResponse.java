package org.SprintForge.modules.workspace.timelog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeEntryResponse {

    private Long id;
    private Long taskId;
    private Long userId;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long durationMinutes;
    private Boolean billable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
