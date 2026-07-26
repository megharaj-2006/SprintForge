package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.ValidEnum;

public class ValidEnumValidator implements ConstraintValidator<ValidEnum, CharSequence> {

    private Class<? extends Enum<?>> enumClass;
    private boolean ignoreCase;

    @Override
    public void initialize(ValidEnum annotation) {
        this.enumClass = annotation.enumClass();
        this.ignoreCase = annotation.ignoreCase();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String strValue = value.toString();
        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        if (enumConstants != null) {
            for (Enum<?> constant : enumConstants) {
                if (ignoreCase) {
                    if (constant.name().equalsIgnoreCase(strValue)) {
                        return true;
                    }
                } else {
                    if (constant.name().equals(strValue)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
