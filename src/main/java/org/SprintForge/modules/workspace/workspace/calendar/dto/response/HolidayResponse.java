package org.SprintForge.modules.workspace.workspace.calendar.dto.response;

import lombok.*;
import org.SprintForge.modules.workspace.workspace.calendar.entity.enums.HolidayType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class HolidayResponse {
    private Long id;
    private Long workspaceId;
    private String name;
    private LocalDate date;
    private Boolean isRecurring;
    private HolidayType type;
    private String description;
    private LocalDateTime createdAt;
}
