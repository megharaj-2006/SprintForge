package org.SprintForge.modules.workspace.sprint.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintStartRequest {

    private LocalDate startDate;

    private LocalDate endDate;

    private String goal;
}
