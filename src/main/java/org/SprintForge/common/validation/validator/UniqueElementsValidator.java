package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.UniqueElements;

import java.util.Collection;
import java.util.HashSet;

public class UniqueElementsValidator implements ConstraintValidator<UniqueElements, Collection<?>> {

    @Override
    public boolean isValid(Collection<?> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return new HashSet<>(value).size() == value.size();
    }
}
