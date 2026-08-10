package org.SprintForge.modules.workspace.workspace.calendar.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.SprintForge.modules.workspace.workspace.calendar.entity.enums.HolidayType;

import java.time.LocalDate;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CreateHolidayRequest {
    @NotBlank
    private String name;
    @NotNull
    private LocalDate date;
    private Boolean isRecurring;
    private HolidayType type;
    private String description;
}
