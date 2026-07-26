package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.ValidTimezone;

import java.time.ZoneId;
import java.util.Set;

public class ValidTimezoneValidator implements ConstraintValidator<ValidTimezone, String> {

    private static final Set<String> AVAILABLE_ZONE_IDS = ZoneId.getAvailableZoneIds();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return AVAILABLE_ZONE_IDS.contains(value);
    }
}
