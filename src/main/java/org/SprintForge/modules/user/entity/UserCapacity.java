package org.SprintForge.modules.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "user_capacities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCapacity extends SoftDeleteEntity {

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "daily_hours_capacity")
    private Double dailyHoursCapacity = 8.0;

    @Column(name = "weekly_hours_capacity")
    private Double weeklyHoursCapacity = 40.0;

    @Column(name = "is_on_vacation")
    private Boolean isOnVacation = false;
}
