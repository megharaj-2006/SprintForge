package org.SprintForge.modules.workspace.sprint.validation;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;
import org.SprintForge.modules.workspace.sprint.exception.SprintException;
import org.SprintForge.modules.workspace.sprint.repository.SprintRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class SprintValidator {

    private final SprintRepository sprintRepository;

    public void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new SprintException("Sprint end date must be after start date.");
        }
    }

    public void validateActiveSprintCheck(Long projectId, Long excludeSprintId) {
        boolean activeExists = sprintRepository.findByProjectIdAndStatusAndIsDeletedFalse(projectId, SprintStatus.ACTIVE)
                .stream()
                .anyMatch(sprint -> excludeSprintId == null || !sprint.getId().equals(excludeSprintId));
        if (activeExists) {
            throw new SprintException("Only one ACTIVE sprint is allowed per project.");
        }
    }
}
