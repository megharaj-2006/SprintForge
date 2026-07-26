package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.Trimmed;

public class TrimmedValidator implements ConstraintValidator<Trimmed, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null is considered valid (use @NotNull for null checks)
        }
        return value.trim().equals(value);
    }
}
