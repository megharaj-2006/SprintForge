package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OccurrencePreviewResponse {

    private Long recurringTaskId;
    private Integer requestedCount;
    private List<LocalDateTime> previewDates;
}
