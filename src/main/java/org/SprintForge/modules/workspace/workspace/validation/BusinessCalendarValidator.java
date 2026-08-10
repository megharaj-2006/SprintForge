package org.SprintForge.modules.workspace.workspace.validation;

import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.modules.workspace.workspace.calendar.repository.BusinessHolidayRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BusinessCalendarValidator {
    private final BusinessHolidayRepository businessHolidayRepository;
    
    public BusinessCalendarValidator(BusinessHolidayRepository businessHolidayRepository) {
        this.businessHolidayRepository = businessHolidayRepository;
    }
    
    public void validateNoConflict(Long workspaceId, LocalDate date) {
        if (businessHolidayRepository.existsByWorkspaceIdAndDateAndIsDeletedFalse(workspaceId, date)) {
            throw new BusinessRuleException("A holiday already exists on " + date + " in this workspace.");
        }
    }
    
    public void validateDate(LocalDate date) {
        if (date == null) throw new BusinessRuleException("Holiday date cannot be null.");
    }
}
