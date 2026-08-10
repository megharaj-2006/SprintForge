package org.SprintForge.modules.workspace.workspace.calendar.entity;

import jakarta.persistence.*;
import lombok.*;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.workspace.calendar.entity.enums.HolidayType;

import java.time.LocalDate;

@Entity
@Table(name = "business_holidays", indexes = {
    @Index(name = "idx_holiday_workspace_date", columnList = "workspace_id, date")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BusinessHoliday extends SoftDeleteEntity {
    @Column(name = "workspace_id", nullable = false) private Long workspaceId;
    @Column(name = "name", nullable = false) private String name;
    @Column(name = "date", nullable = false) private LocalDate date;
    
    @Builder.Default
    @Column(name = "is_recurring") private Boolean isRecurring = false; // repeats every year
    
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "type") private HolidayType type = HolidayType.CUSTOM;
    
    @Column(name = "description") private String description;
}
