package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.MaxCollectionSize;

import java.util.Collection;

public class MaxCollectionSizeValidator implements ConstraintValidator<MaxCollectionSize, Collection<?>> {

    private int maxSize;

    @Override
    public void initialize(MaxCollectionSize constraintAnnotation) {
        this.maxSize = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Collection<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.size() <= maxSize;
    }
}
