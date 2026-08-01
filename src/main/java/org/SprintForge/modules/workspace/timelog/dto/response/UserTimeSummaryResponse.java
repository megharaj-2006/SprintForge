package org.SprintForge.modules.workspace.timelog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTimeSummaryResponse {
    private Long userId;
    private Long totalDurationMinutes;
    private Long billableDurationMinutes;
    private List<TimeEntryResponse> timeEntries;
}
